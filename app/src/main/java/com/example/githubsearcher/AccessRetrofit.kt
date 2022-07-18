package com.example.githubsearcher

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class AccessRetrofit {
    companion object {
        val baseUrl= "https://api.github.com/"
        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttp())
                .build()
        }

        fun getInterface():IRetrofit{
            return getRetrofit().create(IRetrofit::class.java)
        }

        private fun okHttp(): OkHttpClient {
            val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            return client
        }
    }
}
