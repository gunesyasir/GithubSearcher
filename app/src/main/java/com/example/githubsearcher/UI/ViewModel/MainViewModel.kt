package com.example.githubsearcher.UI.ViewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.githubsearcher.Retrofit.AccessRetrofit
import com.example.githubsearcher.Model.CommonModel
import com.example.githubsearcher.Model.RepoResponse
import com.example.githubsearcher.Model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(): ViewModel(){

    private var _liveDataResult = MutableLiveData<List<CommonModel>>()
    val liveDataResult: LiveData<List<CommonModel>> = _liveDataResult

    // Object to manage ProgressBar status
    private var _isRefreshed = MutableLiveData(false)
    val isRefreshed: LiveData<Boolean> = _isRefreshed

    // Object to manage empty response status
    private var _isCompleted = MutableLiveData<Boolean>(false)
    val isCompleted: LiveData<Boolean> = _isCompleted

    fun listItems(searchedItem: String){
        _isRefreshed.value = true
        _liveDataResult.value = emptyList()
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
                        _liveDataResult.value = commonResult
                        _isRefreshed.value = false
                        _isCompleted.value = true

                    } else Log.e("ResponseError", "Response is not successfull!!")
                }
            }
            override fun onFailure(call: Call<RepoResponse>, t: Throwable) {
                Log.e("ResponseFailure", t.toString())
                _isRefreshed.value = false
                _isCompleted.value = true
            }
        })
    }
}