package com.example.commentsapp.presentation.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.commentsapp.presentation.RemoteConfigUtils
import com.example.commentsapp.databinding.FragmentEnterCommentsBinding
import com.example.commentsapp.presentation.viewmodel.CommentViewModel
import logcat.logcat
import org.koin.androidx.viewmodel.ext.android.viewModel


class EnterCommentsFragment : Fragment() {

    private var _binding: FragmentEnterCommentsBinding? = null
    private val binding get() = _binding!!
    private val commentViewModel: CommentViewModel by viewModel()
    private var message: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterCommentsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTextChangedListener()
        saveBtnClicked()
        updateViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setTextChangedListener() {
        binding.commentsInputField.addTextChangedListener { newText ->
            newText?.let {
                message = it.toString()
            }
        }
    }

    private fun saveBtnClicked() {
        binding.saveCommentsBtn.setOnClickListener {
            logcat("EnterComments - Save comments") { "Save comments clicked" }
            hideKeyboard(it)
            if (message != null) {
                commentViewModel.saveComment(message!!)
                Toast.makeText(requireContext(), "Saving a comment", Toast.LENGTH_LONG).show()
                goToCommentsScreen()
            } else {
                Toast.makeText(requireContext(), "Comment cannot be empty", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun goToCommentsScreen() {
        val directions = EnterCommentsFragmentDirections.actionSecondFragmentToFirstFragment()
        findNavController().navigate(directions)
    }

    private fun updateViews() {
        with(binding) {
            val inputLabel = RemoteConfigUtils.getInputLabel()
            val hintText = RemoteConfigUtils.getHintText()
            val btnColor = RemoteConfigUtils.getButtonColor()
            val btnText = RemoteConfigUtils.getSaveButtonText()

            labelTextView.text = inputLabel
            commentTil.hint = hintText
            saveCommentsBtn.text = btnText
            saveCommentsBtn.setBackgroundColor(Color.parseColor(btnColor))
        }
    }
}