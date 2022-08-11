package com.example.timeforsum.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timeforsum.R
import com.example.timeforsum.databinding.GameOfEndFragmentBinding

class EndOfGameFragment : Fragment() {
    private var _binding: GameOfEndFragmentBinding? = null

    private val binding: GameOfEndFragmentBinding
        get() = _binding ?: throw RuntimeException("GameOfEndFragmentBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = GameOfEndFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}