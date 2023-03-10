package com.weather.core.database.entities.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.weather.core.database.entities.onecall.DailyEntity
import com.weather.core.database.entities.onecall.CurrentEntity
import com.weather.core.database.entities.onecall.OneCallEntity
import com.weather.core.database.entities.onecall.OneCallHourlyEntity

class OneCallWithCurrentAndDailyAndHourly(
    @Embedded
    val oneCall: OneCallEntity,
    @Relation(
        entity = CurrentEntity::class,
        parentColumn = "cityName",
        entityColumn = "cityName"
    )
    val current: CurrentWithWeather,
    @Relation(
        entity = DailyEntity::class,
        parentColumn = "cityName",
        entityColumn = "cityName"
    )
    val daily: List<DailyEntity>,
    @Relation(
        entity = OneCallHourlyEntity::class,
        parentColumn = "cityName",
        entityColumn = "cityName"
    )
    val hourly: List<OneCallHourlyEntity>,
)
