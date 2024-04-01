package com.fikri.githubuser.ui.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fikri.githubuser.data.database.DatabaseModule
import com.fikri.githubuser.data.response.DetailResponse
import com.fikri.githubuser.data.response.ItemsItem
import com.fikri.githubuser.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val db: DatabaseModule) : ViewModel() {
    private val _detailUser = MutableLiveData<DetailResponse>()
    val detailUser: LiveData<DetailResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun setFavorite(item: ItemsItem?) {
        viewModelScope.launch {
            item?.let {
                if (_isFavorite.value == false) {
                    db.favoriteUserDao.insert(item)
                    _isFavorite.value = true
                } else {
                    db.favoriteUserDao.delete(item)
                    _isFavorite.value = false
                }
            }
        }
    }

    fun findFavorite(id: Int, listenFavorite: () -> Unit) {
        viewModelScope.launch {
            val user = db.favoriteUserDao.findById(id)
            if (user != null) {
                listenFavorite()
                _isFavorite.value = true
            }
        }
    }

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)

        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>,
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.postValue(response.body())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    class Factory(private val db: DatabaseModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(db) as T
    }
}