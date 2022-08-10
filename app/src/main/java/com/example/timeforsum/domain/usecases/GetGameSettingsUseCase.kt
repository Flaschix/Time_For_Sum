package com.example.timeforsum.domain.usecases

import com.example.timeforsum.domain.entity.GameSettings
import com.example.timeforsum.domain.entity.Level
import com.example.timeforsum.domain.repository.GameRepository

class GetGameSettingsUseCase(private val gameRepository: GameRepository) {
    operator fun invoke(level: Level): GameSettings{
        return gameRepository.getGameSettings(level)
    }
}