package com.example.timeforsum.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level : Parcelable{
    EASY, MEDIUM, HARD, TEST
}