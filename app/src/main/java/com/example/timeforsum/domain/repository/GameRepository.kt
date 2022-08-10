package com.example.timeforsum.domain.repository

import com.example.timeforsum.domain.entity.GameSettings
import com.example.timeforsum.domain.entity.Level
import com.example.timeforsum.domain.entity.Question

interface GameRepository {
    fun generateQuestion(sumValue: Int, countOfQuestion: Int): Question

    fun getGameSettings(level: Level): GameSettings
}