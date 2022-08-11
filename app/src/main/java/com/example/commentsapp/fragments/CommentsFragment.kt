package com.example.commentsapp.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.commentsapp.R
import com.example.commentsapp.RemoteConfigUtils
import com.example.commentsapp.adapter.CommentAdapter
import com.example.commentsapp.databinding.FragmentCommentsBinding
import com.example.commentsapp.viewmodel.CommentViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.launch
import logcat.logcat
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommentsFragment : Fragment() {

  private var _binding: FragmentCommentsBinding? = null
  private val binding get() = _binding!!

  private lateinit var layoutManager: LinearLayoutManager
  private lateinit var commentAdapter: CommentAdapter
  private val commentsViewModel: CommentViewModel by viewModel()
  private lateinit var remoteConfig: FirebaseRemoteConfig

  private val TAG = "RemoteConfig"


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
    setUpFirebaseRemoteConfig()
    fetchRemoteConfigs()
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


  private fun setUpFirebaseRemoteConfig(){
    // Get remote config instance
    remoteConfig = Firebase.remoteConfig

    // Set minimum fetch interval to allow frequent refreshes
    val configSettings = remoteConfigSettings {
      minimumFetchIntervalInSeconds = 3600
    }
    remoteConfig.setConfigSettingsAsync(configSettings)

    // Set default remote config values
    remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
  }

  /**
   *
   */
  private fun fetchRemoteConfigs(){
    binding.fetchConfigButton.setOnClickListener {
      remoteConfig.fetchAndActivate()
        .addOnCompleteListener(requireActivity()) { task ->
          if (task.isSuccessful) {
            val updated = task.result
            logcat(TAG) { "Config params updated: $updated" }
            Toast.makeText(requireContext(), "Fetch and activate succeeded",
              Toast.LENGTH_SHORT).show()
          } else {
            logcat(TAG) { "Config params failed" }
            Toast.makeText(requireContext(), "Fetch failed",
              Toast.LENGTH_SHORT).show()
          }
          updateViews()
        }
    }
  }

  private fun updateViews(){
    with(binding){
      val title = remoteConfig.getString("title_text")
      val btnText = remoteConfig.getString("add_button_text")
      val btnColor = remoteConfig.getString("button_color")

      titleTextView.text = title
      addCommentsBtn.text = btnText
      addCommentsBtn.setBackgroundColor(Color.parseColor(btnColor))
      fetchConfigButton.setBackgroundColor(Color.parseColor(btnColor))
    }
  }
}