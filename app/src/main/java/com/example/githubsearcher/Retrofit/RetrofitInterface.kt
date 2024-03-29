package com.example.githubsearcher.Retrofit

import com.example.githubsearcher.Model.RepoResponse
import com.example.githubsearcher.Model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("search/users")
    fun getUsersByName(@Query("q") username: String): Call<UserResponse>

    @GET("search/repositories")
    fun getReposByName(@Query("q") reponame: String): Call<RepoResponse>

}