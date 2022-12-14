package com.example.timeforsum.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.timeforsum.R
import com.example.timeforsum.databinding.StartScreenFragmentBinding

class StartScreenFragment: Fragment() {
    private var _binding: StartScreenFragmentBinding? = null

    private val binding: StartScreenFragmentBinding
        get() = _binding ?: throw RuntimeException("StartScreenFragmentBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StartScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnStart.setOnClickListener{
            launchChooseLevelFragment()
        }
    }

    private fun launchChooseLevelFragment(){
        findNavController().navigate(R.id.action_startScreenFragment3_to_chooseLevelFragment2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}