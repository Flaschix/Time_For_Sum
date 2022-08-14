package com.example.timeforsum.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.timeforsum.R
import com.example.timeforsum.domain.entity.GameResult

interface OnAnswerClickListener{
    fun onAnswerClick(answer: Int)
}

@BindingAdapter("tvMinCountRightAnswers")
fun bindRequiredMinCountRightAnswers(textView: TextView, count: Int){
    textView.text = String.format(textView.context.getString(R.string.need_answer_count), count)
}

@BindingAdapter("tvMinPercent")
fun bindRequiredMinPercent(textView: TextView, percent: Int){
    textView.text = String.format(textView.context.getString(R.string.need_answer_progress), percent)

}

@BindingAdapter("tvPlayerCountRightAnswers")
fun bindRequiredPlayerCountRightAnswers(textView: TextView, count: Int){
    textView.text = String.format(textView.context.getString(R.string.your_answer_count), count)

}

@BindingAdapter("tvPlayerPercent")
fun bindRequiredPlayerPercent(textView: TextView, gameResult: GameResult){
    textView.text = String.format(textView.context.getString(R.string.your_answer_percent), getPercentRightAnswers(gameResult))
}

@BindingAdapter("imgResult")
fun bindRequiredImgResult(imageView: ImageView, win: Boolean){
    if(win) {
            imageView.setImageResource(R.drawable.ic_baseline_done_24)
        }
        else {
            imageView.setImageResource(R.drawable.ic_baseline_close_24)
        }
}

private fun getPercentRightAnswers(gameResult: GameResult): Int{
    with(gameResult){
        if(countOfQuestion == 0) return 0
        return ((countOfRightAnswers.toDouble() / countOfQuestion) * 100).toInt()
    }
}


@BindingAdapter("playerProgressBar")
fun bindRequiredPercentProgressBar(progressBar: ProgressBar, percent: Int){
    progressBar.secondaryProgress = percent
}

@BindingAdapter("percentOfRightAnswers")
fun bindRequiredPercentOfRightAnswers(progressBar: ProgressBar, percent: Int){
    progressBar.setProgress(percent, true)
}

@BindingAdapter("enoughOfRightAnswers")
fun bindRequiredEnoughOfRightAnswers(textView: TextView, enough: Boolean){
    textView.setTextColor(setColor(textView.context, enough))

}

@BindingAdapter("enoughOfPercentAnswers")
fun bindRequiredEnoughOfPercentAnswers(progressBar: ProgressBar, enough: Boolean){
    val color = setColor(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}


private fun setColor(context: Context, state: Boolean): Int{
    val colorId = if(state) R.color.green
    else R.color.red

    return ContextCompat.getColor(context, colorId)
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int){
    textView.text = number.toString()
}

@BindingAdapter("answersOnClickListeners")
fun bindOnAnswersClickListener(textView: TextView, clickListener: OnAnswerClickListener){
    textView.setOnClickListener {
        clickListener.onAnswerClick(textView.text.toString().toInt())
    }
}
