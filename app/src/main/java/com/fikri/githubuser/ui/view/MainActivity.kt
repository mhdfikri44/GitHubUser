package com.fikri.githubuser.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fikri.githubuser.data.response.ItemsItem
import com.fikri.githubuser.databinding.ActivityMainBinding
import com.fikri.githubuser.ui.MainViewModel
import com.fikri.githubuser.ui.UserAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchView.hide()
                    val query = searchView.text.toString()
                    mainViewModel.findUser(query)
                    false
                }
        }

        mainViewModel.listUser.observe(this) {
            setUserData(it)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setUserData(listUser: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(listUser)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}