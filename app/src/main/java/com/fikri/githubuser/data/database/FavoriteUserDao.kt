package com.fikri.githubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.fikri.githubuser.data.response.ItemsItem

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: ItemsItem)

    @Query("SELECT * FROM favorite")
    fun loadAll(): LiveData<MutableList<ItemsItem>>

    @Query("SELECT * FROM favorite WHERE id LIKE :id LIMIT 1")
    fun findById(id: Int): ItemsItem

    @Delete
    fun delete(user: ItemsItem)
}