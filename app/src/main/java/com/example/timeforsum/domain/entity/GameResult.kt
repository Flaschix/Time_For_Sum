package com.example.timeforsum.domain.entity

data class GameResult(
    val win: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestion: Int,
    val gameSettings: GameSettings
)