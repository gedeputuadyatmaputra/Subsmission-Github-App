package com.gedeputu.subsmissionakhir.Detail.Following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gedeputu.subsmissionakhir.data.response.User
import com.gedeputu.subsmissionakhir.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    val listFollowing = MutableLiveData<ArrayList<User>>()

    val errorMessage = MutableLiveData<String>()

    fun setListFollowing(user: String) {
        ApiConfig.apiService
            .getUserFollowing(user)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message.toString())
                    errorMessage.postValue(t.message.toString())
                }

            })
    }

    fun getListFollowing(): LiveData<ArrayList<User>> {
        return listFollowing
    }
}