package com.example.commentsapp.presentation.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commentsapp.R
import com.example.commentsapp.RemoteConfigUtils
import com.example.commentsapp.databinding.FragmentCommentsBinding
import com.example.commentsapp.presentation.intent.CommentIntent
import com.example.commentsapp.presentation.model.CommentUiState
import com.example.commentsapp.presentation.view.adapter.CommentAdapter
import com.example.commentsapp.presentation.viewmodel.CommentViewModel
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
        getComments()
        observeComments()
        setAddCommentsClicked()
//        fetchRemoteConfigs()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setAddCommentsClicked() {
        binding.addCommentsBtn.setOnClickListener {
            gotToEnterComments()
//            lifecycleScope.launch{ TODO: Fix.. When?
//                commentsViewModel.commentIntent.send(CommentIntent.NavigateToAddComment)
//            }
        }
    }

    private fun gotToEnterComments() {
        val directions = CommentsFragmentDirections.actionFirstFragmentToSecondFragment()
        findNavController().navigate(directions)
    }

    private fun getComments() {
        lifecycleScope.launch{
            commentsViewModel.commentIntent.send(CommentIntent.FetchComments)
        }
    }

    private fun observeComments() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                commentsViewModel.uiState.collect { state ->
                    when(state){
                        is CommentUiState.Loading ->{
                        }
                        is CommentUiState.NoComments ->{
                            binding.noCommentsTextView.visibility = View.VISIBLE
                        }
                        is CommentUiState.Error ->{
                            val errorMsg = state.error
                            binding.noCommentsTextView.visibility = View.VISIBLE
                            binding.noCommentsTextView.text = errorMsg
                        }
                        is CommentUiState.Success ->{
                            val comments = state.data
                            binding.noCommentsTextView.visibility = View.GONE
                            commentAdapter = CommentAdapter(comments)

                            binding.commentsRecyclerView.adapter = commentAdapter
                        }
                    }
                }
            }
        }
    }


    private fun setUpFirebaseRemoteConfig() {
        RemoteConfigUtils.setUpRemoteConfig()
    }

    private fun fetchRemoteConfigs() {
        RemoteConfigUtils.fetchRemoteConfigs(requireActivity()).run {
            updateViews()
        }
    }

    private fun updateViews() {
        with(binding) {
            val title = RemoteConfigUtils.getTitleText()
            val btnText = RemoteConfigUtils.getAddButtonText()
            val btnColor = RemoteConfigUtils.getButtonColor()
            val isNotes = RemoteConfigUtils.getIsNotes()

//            titleTextView.text = title
            addCommentsBtn.text = btnText
            addCommentsBtn.setBackgroundColor(Color.parseColor(btnColor))
            setTitleIcon(isNotes)
        }
    }

    private fun setTitleIcon(isNotes: Boolean) {
        val notesDrawable = requireContext().getDrawable(R.drawable.ic_baseline_note_24)
        val commentsDrawable = requireContext().getDrawable(R.drawable.ic_baseline_comment_24)
//        binding.titleTextView.apply {
//            if (isNotes) {
//                setCompoundDrawablesRelativeWithIntrinsicBounds(notesDrawable, null, null, null)
//            } else {
//                setCompoundDrawablesRelativeWithIntrinsicBounds(commentsDrawable, null, null, null)
//            }
//        }
    }
}