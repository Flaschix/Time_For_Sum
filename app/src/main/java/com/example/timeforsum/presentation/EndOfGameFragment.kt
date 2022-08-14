package com.example.timeforsum.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.timeforsum.R
import com.example.timeforsum.databinding.GameOfEndFragmentBinding
import com.example.timeforsum.domain.entity.GameResult

class EndOfGameFragment : Fragment() {
    private var _binding: GameOfEndFragmentBinding? = null

    private val binding: GameOfEndFragmentBinding
        get() = _binding ?: throw RuntimeException("GameOfEndFragmentBinding == null")

    private val args by navArgs<EndOfGameFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameOfEndFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnClose.setOnClickListener {
            closeGame()
        }
        binding.gameResult = args.gameResult
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun closeGame(){
        findNavController().popBackStack()
    }
}