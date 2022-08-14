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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModelObserver()
    }

    private fun viewModelObserver(){

        viewModel.gameResult.observe(viewLifecycleOwner){
            launchEndOfGameFragment(it)
        }

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