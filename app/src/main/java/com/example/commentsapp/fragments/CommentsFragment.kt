package com.example.commentsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commentsapp.R
import com.example.commentsapp.adapter.CommentAdapter
import com.example.commentsapp.databinding.FragmentCommentsBinding
import com.example.commentsapp.viewmodel.CommentViewModel
import kotlinx.coroutines.launch
import logcat.logcat
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentsFragment : Fragment() {

  private var _binding: FragmentCommentsBinding? = null
  private val binding get() = _binding!!

  private lateinit var layoutManager: LinearLayoutManager
  private lateinit var commentAdapter: CommentAdapter
  private val commentsViewModel: CommentViewModel by viewModel()


  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentCommentsBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    layoutManager = LinearLayoutManager(requireContext())
    binding.commentsRecyclerView.layoutManager = layoutManager
    setAddCommentsClicked()
    getComments()
    observeComments()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun setAddCommentsClicked(){
    binding.addCommentsBtn .setOnClickListener {
      gotToEnterComments()
    }
  }

  private fun gotToEnterComments(){
    val directions = CommentsFragmentDirections.actionFirstFragmentToSecondFragment()
    findNavController().navigate(directions)
  }

  private fun getComments(){
    commentsViewModel.getComments()
  }

  private fun observeComments(){
      viewLifecycleOwner.lifecycleScope.launch{
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
          commentsViewModel.comments.collect{ comments ->
            logcat("CommentsFragment - getComments") { "comments => ${comments.size}" }
            if (comments.isEmpty()){
              binding.noCommentsTextView.visibility = View.VISIBLE
            }else{
              binding.noCommentsTextView.visibility = View.GONE
              commentAdapter = CommentAdapter(comments)

              binding.commentsRecyclerView.adapter = commentAdapter
            }
          }
        }
      }
  }
}