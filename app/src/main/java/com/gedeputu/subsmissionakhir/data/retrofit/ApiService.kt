package com.gedeputu.subsmissionakhir.data.retrofit

import com.gedeputu.subsmissionakhir.BuildConfig
import com.gedeputu.subsmissionakhir.data.response.GithubResponse
import com.gedeputu.subsmissionakhir.data.response.UserResponse
import com.gedeputu.subsmissionakhir.data.response.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val apiKey = BuildConfig.API_KEY

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token $apiKey")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $apiKey")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<GithubResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $apiKey")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $apiKey")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}