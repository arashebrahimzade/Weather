package com.weather.core.network.model.geosearch

import com.weather.model.geocode.GeoSearchItem
import kotlinx.serialization.Serializable

@Serializable
data class GeoSearchItemDto(
    val country: String? = null,
    val lat: Double? = null,
    val local_names: LocalNamesDto? = null,
    val lon: Double? = null,
    val name: String? = null,
    val state: String? = null,
) {
    fun toGeoSearchItem(): GeoSearchItem {
        return GeoSearchItem(
            country = country,
            lat = lat,
            local_names = local_names?.toLocalNames(),
            lon,
            name,
            state
        )
    }
}