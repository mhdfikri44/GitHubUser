package com.fikri.githubuser.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fikri.githubuser.data.response.FollowResponseItem
import com.fikri.githubuser.databinding.FragmentFollowBinding
import kotlin.properties.Delegates

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private var position by Delegates.notNull<Int>()
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        // set recyclerview
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollow.layoutManager = layoutManager

        // initial FollowViewModel
        val followViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowViewModel::class.java]

        followViewModel.findFollow(position, username)

        followViewModel.listFollow.observe(viewLifecycleOwner) {
            setFollowData(it)
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun setFollowData(listFollow: List<FollowResponseItem>) {
        val adapter = FollowAdapter()
        adapter.submitList(listFollow)
        binding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}