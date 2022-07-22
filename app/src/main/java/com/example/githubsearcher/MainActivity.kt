package com.example.githubsearcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearcher.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),MainAdapter.RecyclerViewClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        binding.root.clearFocus()
        binding.recyclerView.setHasFixedSize(true)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    listUsers(query)
                } else {
                    Log.e("nullquery", "a")
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


    private fun listUsers(searchedItem: String) {
        val retrofitObject = AccessRetrofit.getInterface()
        var userResult: List<CommonModel>
        retrofitObject.getUsersByName(searchedItem).enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.body() != null) {
                    userResult = response.body()!!.items as List<CommonModel>
                    listRepos(searchedItem, userResult)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("User error", t.toString())
            }
        })
    }

    private fun listRepos(searchedItem: String, userList: List<CommonModel>) {
        val obj = AccessRetrofit.getInterface()
        var commonResult: List<CommonModel> = listOf()
        var repoList: List<CommonModel>
        obj.getReposByName(searchedItem).enqueue(object : Callback<RepoResponse> {
            override fun onResponse(call: Call<RepoResponse>, response: Response<RepoResponse>) {
                if (response.body() != null) {
                    if (response.isSuccessful) {
                        repoList = response.body()!!.items as List<CommonModel>
                        commonResult = userList + repoList
                        val adapter = MainAdapter(this@MainActivity, commonResult, this@MainActivity)
                        binding.recyclerView.adapter = adapter
                    } else Log.e("Repo error", "problem!!")
                }
            }

            override fun onFailure(call: Call<RepoResponse>, t: Throwable) {
                Log.e("failure", t.toString())
            }
        })
    }

    override fun onClick(item: CommonModel) {
        val intent = Intent(this@MainActivity,DetailActivity::class.java)
        intent.putExtra("CommonObject", item)
        this@MainActivity.startActivity(intent)
    }

}













