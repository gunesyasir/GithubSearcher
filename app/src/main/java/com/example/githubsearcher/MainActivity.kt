package com.example.githubsearcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearcher.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.root.clearFocus()
        //binding.searchView.clearFocus()
        binding.recyclerView.setHasFixedSize(true)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    listUsers(query)
                }
                else{
                    Log.e("nullquery","a")
                    Toast.makeText(
                    this@MainActivity,
                    "You need to type some words!",
                    Toast.LENGTH_LONG
                ).show()
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })
    }

    private fun listUsers(searcheditem: String) {
        val obj = AccessRetrofit.getInterface()
        var userResult: List<CommonModel>
        obj.getUsersByName(searcheditem).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.body() != null) {
                    userResult = response.body()!!.items as List<CommonModel>
                    listRepos(searcheditem , userResult)
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("User error", t.toString())
            }
        })
    }
    private fun listRepos(searcheditem: String , userList: List<CommonModel>) {
        val obj = AccessRetrofit.getInterface()
        var commonResult: List<CommonModel>
        var repoList: List<CommonModel>
        obj.getReposByName(searcheditem).enqueue(object : Callback<RepoResponse> {
            override fun onResponse(call: Call<RepoResponse>, response: Response<RepoResponse>) {
                if (response.body() != null) {
                    if(response.isSuccessful) {
                        repoList = response.body()!!.items as List<CommonModel>
                        commonResult = userList + repoList
                        binding.recyclerView.adapter =
                            MyAdapter(this@MainActivity, commonResult)
                    }
                    else Log.e("Repo error", "problem!!")
                }
            }
            override fun onFailure(call: Call<RepoResponse>, t: Throwable) {
                    Log.e("failure", t.toString())
            }
        })
    }
}












