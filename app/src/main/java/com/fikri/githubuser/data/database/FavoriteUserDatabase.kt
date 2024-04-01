package com.fikri.githubuser.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fikri.githubuser.data.response.ItemsItem

@Database(entities = [ItemsItem::class], version = 1, exportSchema = false)
abstract class FavoriteUserDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao
}