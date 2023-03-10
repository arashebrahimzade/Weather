package com.weather.feature.search

import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.weather.core.repository.WeatherRepository
import com.weather.model.Coordinate
import com.weather.model.Resource
import com.weather.model.WeatherData
import com.weather.model.geocode.GeoSearchItem
import com.weather.sync.work.FetchRemoteWeatherWorker
import com.weather.sync.work.WEATHER_COORDINATE
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val workManager = WorkManager.getInstance(context)

    private val _weatherPreview = MutableSharedFlow<WeatherData>()
    val weatherPreview: SharedFlow<WeatherData> = _weatherPreview.asSharedFlow()

    private val _searchQuery = MutableStateFlow("")
    private val searchQuery = _searchQuery.asStateFlow()
    @ExperimentalCoroutinesApi
    @FlowPreview
    val searchUIState = searchQuery
        .debounce(500L)
        .filterNot {
            it.isBlank()
        }
        .flatMapLatest { cityName ->
            weatherRepository.searchLocation(cityName = cityName)
                .flowOn(Dispatchers.IO)
                .map { search ->
                    when (search) {
                        is Resource.Success -> {
                            SearchUIState.Success(search.data!!)
                        }
                        is Resource.Loading -> {
                            SearchUIState.Loading
                        }
                        is Resource.Error -> {
                            SearchUIState.Error
                        }
                    }
                }
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(1000L),
            initialValue = SearchUIState.Loading
        )

    fun setSearchQuery(cityName: String) {
        _searchQuery.value = cityName
    }
    fun syncWeather(searchItem: GeoSearchItem){
        //work
        val coordinates = Coordinate(
            searchItem.name,
            searchItem.lat.toString(),
            searchItem.lon.toString()
        )
        val coordinateString = Json.encodeToString(coordinates)
        val inputData = Data.Builder()
            .putString(WEATHER_COORDINATE, coordinateString)
            .build()
        val fetchWork = OneTimeWorkRequestBuilder<FetchRemoteWeatherWorker>()
            .setInputData(inputData)
            .build()
        workManager.beginUniqueWork("weatherSyncWorkName",ExistingWorkPolicy.KEEP,fetchWork).enqueue()
    }

    fun saveSearchWeatherItem(searchItem: GeoSearchItem) {
        val coordinates = Coordinate(
            searchItem.name,
            searchItem.lat.toString(),
            searchItem.lon.toString()
        )
        viewModelScope.launch {
            weatherRepository.syncWeather(
                coordinates
            )
        }
    }
}

sealed interface SearchUIState {
    data class Success(val data: List<GeoSearchItem>) : SearchUIState
    object Error : SearchUIState
    object Loading : SearchUIState
}