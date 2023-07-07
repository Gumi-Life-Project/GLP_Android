package com.ssafy.gumi_life_project.data.model

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("온도") val temperature: String,
    @SerializedName("강수 형태") val precipitationType: String,
    @SerializedName("1시간 강수량") val hourlyPrecipitation: String
)

data class WeatherResponse(
    @SerializedName("data") val data: Weather,
    @SerializedName("message") val message: String
)
