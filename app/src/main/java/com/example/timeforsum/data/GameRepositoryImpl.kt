package com.example.timeforsum.data

import com.example.timeforsum.domain.entity.GameSettings
import com.example.timeforsum.domain.entity.Level
import com.example.timeforsum.domain.entity.Question
import com.example.timeforsum.domain.repository.GameRepository
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {
    private const val MIN_SUM_VALUE = 1
    private const val MIN_VISIBLE_VALUE = 0

    override fun generateQuestion(sumValue: Int, countOfQuestion: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, sumValue + 1)
        val visibleNumber = Random.nextInt(MIN_VISIBLE_VALUE, sum)
        val answers = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        answers.add(rightAnswer)
        while (answers.size < countOfQuestion) answers.add(Random.nextInt(MIN_VISIBLE_VALUE, sum*2))
        return Question(sum, visibleNumber, answers.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level){
            Level.EASY -> GameSettings(120, 20, 10, 50)
            Level.MEDIUM -> GameSettings(90, 100, 15, 65)
            Level.HARD -> GameSettings(60, 200, 20, 80)
            Level.TEST -> GameSettings(15, 10, 2, 10)
        }
    }
}