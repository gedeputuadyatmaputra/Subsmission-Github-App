package com.gedeputu.subsmissionakhir.data.response

import com.google.gson.annotations.SerializedName

data class GithubResponse (

    @SerializedName("login")
    val username: String,
    val id: Int,
    val name: String,
    val bio: String,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String,
    @SerializedName("public_repos")
    var publicRepos: Int,
    val following: Int,
    val followers: Int
)