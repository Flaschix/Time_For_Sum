package com.example.timeforsum.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.timeforsum.R
import com.example.timeforsum.databinding.GameFragmentBinding
import com.example.timeforsum.domain.entity.GameResult
import com.example.timeforsum.domain.entity.Level

class GameFragment : Fragment() {
    private lateinit var level: Level
    private var _binding: GameFragmentBinding? = null

    private val binding: GameFragmentBinding
        get() = _binding ?: throw RuntimeException("GameFragmentBinding == null")

    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application))[GameViewModel::class.java]
    }

    private val tvAnswers by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvAnswer1)
            add(binding.tvAnswer2)
            add(binding.tvAnswer3)
            add(binding.tvAnswer4)
            add(binding.tvAnswer5)
            add(binding.tvAnswer6)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserver()
        setClickListenersToAnswers()
        viewModel.startGame(level)
    }

    private fun viewModelObserver() = with(binding){

        viewModel.question.observe(viewLifecycleOwner){
            tvSum.text = it.sumOfQuestion.toString()
            tvVisibleNumber.text = it.visibleNumber.toString()
            for (i in 0 until tvAnswers.size){
                tvAnswers[i].text  = it.answers[i].toString()
            }
        }

        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner){
            progressBar.setProgress(it, true)
        }

        viewModel.minPercent.observe(viewLifecycleOwner){
            progressBar.secondaryProgress = it
        }

        viewModel.gameResult.observe(viewLifecycleOwner){
            launchEndOfGameFragment(it)
        }

        viewModel.enoughOfRightAnswers.observe(viewLifecycleOwner){
            tvAnswerProgress.setTextColor(setColor(it))
        }

        viewModel.enoughOfPercentAnswers.observe(viewLifecycleOwner){
            val color = setColor(it)
            progressBar.progressTintList = ColorStateList.valueOf(color)
        }

        viewModel.timeLD.observe(viewLifecycleOwner){
            tvTimer.text = it
        }

        viewModel.progressAnswers.observe(viewLifecycleOwner){
            tvAnswerProgress.text = it
        }
    }

    private fun setClickListenersToAnswers(){
        for(item in tvAnswers) item.setOnClickListener {
            viewModel.chooseAnswer(item.text.toString().toInt())
        }
    }

    private fun setColor(state: Boolean): Int{
        val colorId = if(state) R.color.green
        else R.color.red

        return ContextCompat.getColor(requireContext(), colorId)
    }

    private fun launchEndOfGameFragment(gameResult: GameResult){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, EndOfGameFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs(){
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    companion object{
        private const val KEY_LEVEL = "level"
        const val NAME = "game_fragment"

        fun newInstance(level: Level): GameFragment{
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }
    }
}