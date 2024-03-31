package com.fikri.githubuser.ui.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.fikri.githubuser.R
import com.fikri.githubuser.data.response.DetailResponse
import com.fikri.githubuser.databinding.ActivityDetailBinding
import com.fikri.githubuser.ui.DetailViewModel
import com.fikri.githubuser.ui.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object {
        private const val USERNAME = "username"
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        val username = intent.getStringExtra(USERNAME).toString()
        detailViewModel.getDetailUser(username)

        detailViewModel.detailUser.observe(this) { detailUser ->
            setDetailUser(detailUser)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionsPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setDetailUser(detailUser: DetailResponse) {
        with(binding){
            userName.text = detailUser.name
            userLogin.text = detailUser.login
            "${detailUser.followers} Follower".also { userFollower.text = it }
            "${detailUser.following} Following".also { userFollowing.text = it }
            Glide.with(root)
                .load(detailUser.avatarUrl)
                .into(userImg)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}