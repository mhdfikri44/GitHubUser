package com.fikri.githubuser.data.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GithubResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<ItemsItem>,
)

@Parcelize
@Entity(tableName = "favorite")
data class ItemsItem(

    @field:SerializedName("login")
    @ColumnInfo(name = "login")
    val login: String,

    @field:SerializedName("avatar_url")
    @ColumnInfo(name = "avatarUrl")
    val avatarUrl: String,

    @field:SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
) : Parcelable
