package com.example.timeforsum.domain.usecases

import com.example.timeforsum.domain.entity.Question
import com.example.timeforsum.domain.repository.GameRepository

class GenerateQuestionUseCase(private val gameRepository: GameRepository) {
    operator fun invoke(sumValue: Int): Question{
        return gameRepository.generateQuestion(sumValue, COUNT_OF_QUESTIONS)
    }

    private companion object{
        private const val COUNT_OF_QUESTIONS = 6
    }
}