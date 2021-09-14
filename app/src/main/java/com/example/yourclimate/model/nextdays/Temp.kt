package com.example.yourclimate.model.nextdays

import javax.inject.Inject

data class Temp @Inject constructor(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)