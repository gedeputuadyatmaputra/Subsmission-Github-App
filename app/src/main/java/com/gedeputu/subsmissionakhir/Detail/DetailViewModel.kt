package com.gedeputu.subsmissionakhir.Detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gedeputu.subsmissionakhir.data.Database.UserDatabase
import com.gedeputu.subsmissionakhir.data.Database.UserFavorite
import com.gedeputu.subsmissionakhir.data.Database.UserFavoriteDao
import com.gedeputu.subsmissionakhir.data.response.GithubResponse
import com.gedeputu.subsmissionakhir.data.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (application: Application) : AndroidViewModel(application){

    val user = MutableLiveData<GithubResponse>()
    private var userDao: UserFavoriteDao? = null
    private var userDatabase: UserDatabase? = null

    val errorMessage = MutableLiveData<String>()

    init {
        userDatabase = UserDatabase.getDatabase(application)
        userDao = userDatabase?.userFavoriteDao()
    }

    fun setUserDetail(username: String) {
        ApiConfig.apiService.getUserDetail(username)
            .enqueue(object : Callback<GithubResponse> {
                override fun onResponse(
                    call: Call<GithubResponse>,
                    response: Response<GithubResponse>
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                    errorMessage.postValue(t.message.toString())
                }

            })

    }

    fun getUserDetail(): LiveData<GithubResponse> {
        return user
    }

    fun insertFavoriteUser(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = UserFavorite(
                username,
                id,
                avatarUrl
            )
            userDao?.insertFavoriteUser(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.isFavorite(id)

    fun removeFavoriteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFavoriteUser(id)
        }
    }
}