package com.example.timeforsum.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.timeforsum.R
import com.example.timeforsum.databinding.ChooseLevelFragmentBinding
import com.example.timeforsum.domain.entity.Level

class ChooseLevelFragment : Fragment() {
    private var _binding: ChooseLevelFragmentBinding? = null

    private val binding: ChooseLevelFragmentBinding
        get() = _binding ?: throw RuntimeException("ChooseLevelFragmentBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChooseLevelFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnEasy.setOnClickListener{
                lounchGameFragment(Level.EASY)
            }
            btnMedium.setOnClickListener {
                lounchGameFragment(Level.MEDIUM)
            }
            btnHard.setOnClickListener {
                lounchGameFragment(Level.HARD)
            }
            btnTest.setOnClickListener {
                lounchGameFragment(Level.TEST)
            }
        }
    }

    private fun lounchGameFragment(level: Level){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, GameFragment.newInstance(level))
            .addToBackStack(GameFragment.NAME)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val NAME = "ChooseLevelFragment"

        fun newInstance(): ChooseLevelFragment{
            return ChooseLevelFragment()
        }
    }
}