package com.fikri.githubuser.data.database

import android.content.Context
import androidx.room.Room

class DatabaseModule(private val context: Context)  {
    private val db = Room.databaseBuilder(context, FavoriteUserDatabase::class.java, "favorite.db")
        .allowMainThreadQueries()
        .build()

    val favoriteUserDao = db.favoriteUserDao()
}