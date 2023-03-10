package com.experiment.weather.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import com.experiment.weather.presentation.ui.WeatherApp
import com.weather.core.design.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//        private val viewModel: MainPageViewModel by lazy {
//        ViewModelProvider(this).get(MainPageViewModel::class.java)
//    }
//    private lateinit var binding: ActivityMainBinding
//    val vm: TestViewModel by viewModels()

    @OptIn(ExperimentalMaterialApi::class, ExperimentalCoroutinesApi::class)
    @FlowPreview
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//
//            }
//        }
        setContent {
            WeatherApp()
        }
    }
}
