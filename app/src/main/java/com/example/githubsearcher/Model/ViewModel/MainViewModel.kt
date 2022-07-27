package com.example.githubsearcher.Model.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubsearcher.AccessRetrofit
import com.example.githubsearcher.Model.CommonModel
import com.example.githubsearcher.Model.RepoResponse
import com.example.githubsearcher.Model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(): ViewModel(){
     var liveDataResult = MutableLiveData<List<CommonModel>>()

    fun listItems(searchedItem: String){
        listUsers(searchedItem)
    }
     private fun listUsers(searchedItem: String) {
         var userResult: List<CommonModel>
        val retrofitObject = AccessRetrofit.getInterface()
        retrofitObject.getUsersByName(searchedItem).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.body() != null) {
                    userResult = (response.body()!!.items as List<CommonModel>)
                    listRepos(searchedItem, userResult)
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("RetrofitRequestError", t.toString())
            }
        })
    }
    private fun listRepos(searchedItem: String, userResult: List<CommonModel>) {
        var repoResult: List<CommonModel>
        var commonResult: List<CommonModel>
        val obj = AccessRetrofit.getInterface()
        obj.getReposByName(searchedItem).enqueue(object : Callback<RepoResponse> {
            override fun onResponse(call: Call<RepoResponse>, response: Response<RepoResponse>) {
                if (response.body() != null) {
                    if (response.isSuccessful) {
                        repoResult = response.body()!!.items as List<CommonModel>
                        commonResult = userResult + repoResult
                        liveDataResult.value = commonResult

                    } else Log.e("ResponseError", "Response is not successfull!!")
                }
            }
            override fun onFailure(call: Call<RepoResponse>, t: Throwable) {
                Log.e("ResponseFailure", t.toString())
            }
        })
    }
}