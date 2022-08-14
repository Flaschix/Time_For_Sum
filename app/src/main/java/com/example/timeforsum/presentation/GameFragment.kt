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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.timeforsum.R
import com.example.timeforsum.databinding.GameFragmentBinding
import com.example.timeforsum.domain.entity.GameResult
import com.example.timeforsum.domain.entity.Level

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    private var _binding: GameFragmentBinding? = null

    private val binding: GameFragmentBinding
        get() = _binding ?: throw RuntimeException("GameFragmentBinding == null")

    private val viewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
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

        findNavController().navigate(GameFragmentDirections.actionGameFragmentToEndOfGameFragment23(gameResult))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}