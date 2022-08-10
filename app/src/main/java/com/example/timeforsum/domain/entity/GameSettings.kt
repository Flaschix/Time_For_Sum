package com.example.timeforsum.domain.entity

data class GameSettings(
    val time: Int,
    val sumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int
    )