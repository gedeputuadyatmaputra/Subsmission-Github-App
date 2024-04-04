package com.gedeputu.subsmissionakhir.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gedeputu.subsmissionakhir.data.response.User
import com.gedeputu.subsmissionakhir.data.response.UserResponse
import com.gedeputu.subsmissionakhir.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val Userslist = MutableLiveData<ArrayList<User>>()

    val errorMessage = MutableLiveData<String>()

    fun setSearchUsers(query: String) {
        ApiConfig.apiService
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {

                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful) {
                            Userslist.postValue(response.body()?.items)
                        }
                    }

                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Log.d("Failure", t.message.toString())
                        errorMessage.postValue(t.message.toString())
                    }
                }
            )
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return Userslist
    }
}