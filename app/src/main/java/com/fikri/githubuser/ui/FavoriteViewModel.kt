package com.fikri.githubuser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fikri.githubuser.data.database.DatabaseModule

class FavoriteViewModel(private val databaseModule: DatabaseModule) : ViewModel() {
    fun getUserFavorite() = databaseModule.favoriteUserDao.loadAll()

    class Factory(private val db: DatabaseModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(db) as T
    }
}