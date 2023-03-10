package com.weather.core.database.entities.onecall

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.NO_ACTION
import androidx.room.PrimaryKey
import com.weather.model.Weather

@Entity(tableName = "current_weather", foreignKeys = [ForeignKey(
    entity = OneCallEntity::class,
    parentColumns = ["cityName"],
    childColumns = ["cityName"],
    onDelete = CASCADE,
    onUpdate = NO_ACTION
)])
data class CurrentWeatherEntity(
    @PrimaryKey(autoGenerate = false)
    val cityName: String,
    val description: String,
    val icon: String,
    val id: Int,
    val main: String,
){
    fun asDomainModel(): Weather {
        return Weather(
            description = description,
            icon = icon,
            id = id,
            main = main
        )
    }
}