package com.example.timeforsum.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val win: Boolean,
    val countOfRightAnswers: Int,
    val countOfQuestion: Int,
    val gameSettings: GameSettings
) : Parcelable