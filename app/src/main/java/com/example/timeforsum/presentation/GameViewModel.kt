package com.example.timeforsum.presentation

import android.app.Application
import android.content.Context
import android.os.CountDownTimer

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.timeforsum.R
import com.example.timeforsum.data.GameRepositoryImpl
import com.example.timeforsum.domain.entity.GameResult
import com.example.timeforsum.domain.entity.GameSettings
import com.example.timeforsum.domain.entity.Level
import com.example.timeforsum.domain.entity.Question
import com.example.timeforsum.domain.usecases.GenerateQuestionUseCase
import com.example.timeforsum.domain.usecases.GetGameSettingsUseCase

class GameViewModel(private val application: Application, private val level: Level) : ViewModel() {
    private var timer: CountDownTimer? = null
    private lateinit var gameSettings: GameSettings
    private var correctAnswers = 0
    private var countQuestions = 0

    private var _timeLD = MutableLiveData<String>()
    val timeLD: LiveData<String>
        get() = _timeLD

    private var _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private var _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers

    private var _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private var _enoughOfRightAnswers = MutableLiveData<Boolean>()
    val enoughOfRightAnswers: LiveData<Boolean>
        get() = _enoughOfRightAnswers

    private var _enoughOfPercentAnswers = MutableLiveData<Boolean>()
    val enoughOfPercentAnswers: LiveData<Boolean>
        get() = _enoughOfPercentAnswers

    private var _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private var _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private val repository = GameRepositoryImpl
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    init {
        startGame()
    }

    private fun startGame(){
        getGameSetting()
        startTimer()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(answer: Int){
        val rightAnswer = question.value?.rightAnswer
        if(answer == rightAnswer) {
            correctAnswers++
        }
        countQuestions++
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress(){
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(application.resources.getString(R.string.answer_progress), correctAnswers, gameSettings.minCountOfRightAnswers)
        _enoughOfRightAnswers.value = correctAnswers >= gameSettings.minCountOfRightAnswers
        _enoughOfPercentAnswers.value = percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswers(): Int{
        if (countQuestions == 0) return 0
        return ((correctAnswers.toDouble() / countQuestions.toDouble()) * 100).toInt()
    }

    private fun getGameSetting(){
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun generateQuestion(){
        _question.value = generateQuestionUseCase(gameSettings.sumValue)
    }

    private fun startTimer(){
        timer = object : CountDownTimer(gameSettings.time * SECONDS_OT_OF_MILLISECONDS, SECONDS_OT_OF_MILLISECONDS){
            override fun onTick(p0: Long) {
                _timeLD.value = formattingTime(p0)
            }
            override fun onFinish() {
                finishGame()
            }

        }
        timer?.start()
    }

    private fun finishGame(){
        _gameResult.value = GameResult(
            win = enoughOfPercentAnswers.value == true && enoughOfRightAnswers.value == true,
            countOfRightAnswers = correctAnswers,
            countOfQuestion = countQuestions,
            gameSettings = gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()

    }

    private fun formattingTime(time: Long): String{
        val seconds = time / SECONDS_OT_OF_MILLISECONDS
        val minutes = seconds / MINUTES_OT_OF_SECONDS
        val leftSeconds = seconds - (minutes * MINUTES_OT_OF_SECONDS)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    companion object{
        private const val SECONDS_OT_OF_MILLISECONDS = 1000L
        private const val MINUTES_OT_OF_SECONDS = 60
    }
}