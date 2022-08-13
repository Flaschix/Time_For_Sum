package com.example.timeforsum.domain.entity


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val time: Int,
    val sumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int
    ) : Parcelable