package com.example.timeforsum.domain.entity

data class Question(
    val sumOfQuestion: Int,
    val visibleNumber: Int,
    val answers: List<Int>
){
    val rightAnswer: Int
        get() = sumOfQuestion - visibleNumber
}
