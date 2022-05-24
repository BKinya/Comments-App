package com.example.commentsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.commentsapp.R
import com.example.commentsapp.databinding.FragmentCommentsBinding
import logcat.logcat

class CommentsFragment : Fragment() {

  private var _binding: FragmentCommentsBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentCommentsBinding.inflate(inflater, container, false)
    return binding.root

  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.addCommentsBtn .setOnClickListener {
      gotToEnterComments()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun gotToEnterComments(){
    val directions = CommentsFragmentDirections.actionFirstFragmentToSecondFragment()
    findNavController().navigate(directions)
  }
}