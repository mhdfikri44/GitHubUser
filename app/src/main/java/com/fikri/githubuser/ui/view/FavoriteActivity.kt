package com.fikri.githubuser.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fikri.githubuser.data.database.DatabaseModule
import com.fikri.githubuser.data.response.ItemsItem
import com.fikri.githubuser.databinding.ActivityFavoriteBinding
import com.fikri.githubuser.ui.UserAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModel.Factory(DatabaseModule(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager

        favoriteViewModel.getUserFavorite().observe(this) {
            setUserData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getUserFavorite().observe(this) {
            setUserData(it)
        }
    }

    private fun setUserData(listUser: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(listUser)
        binding.rvFavorite.adapter = adapter
    }
}