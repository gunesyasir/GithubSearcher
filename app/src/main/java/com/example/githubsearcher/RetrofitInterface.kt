package com.example.githubsearcher

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("search/users")
    fun getUsersByName(@Query("q") username: String): Call<UserResponse>

    @GET("search/repositories")
    fun getReposByName(@Query("q") reponame: String): Call<RepoResponse>

}