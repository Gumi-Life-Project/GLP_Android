package com.ssafy.gumi_life_project.data.model

data class ShuttleBusLine(
    val lineName: String,
    val stopList: List<ShuttleBusStop>
)

data class ShuttleBusStop(
    val stopName: String,
    val latitude: Double,
    val longitude: Double,
    val arrivalTime: String,
    var isMarked: Boolean
)