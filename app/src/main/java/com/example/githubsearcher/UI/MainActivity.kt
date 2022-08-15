package com.example.githubsearcher.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearcher.Model.CommonModel
import com.example.githubsearcher.UI.ViewModel.MainViewModel
import com.example.githubsearcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainAdapter.RecyclerViewItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViews()
        observeData()
    }

    private fun setViews(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.root.clearFocus()
        binding.recyclerView.setHasFixedSize(true)
        binding.editText.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH){
                performSearch()
                return@setOnEditorActionListener true
            }
            false
        }
        binding.searchButton.setOnClickListener { performSearch() }
        activateProgressBar()
    }

    private fun performSearch(){
        val query = binding.editText.text.toString()
        if (TextUtils.getTrimmedLength(query)!=0) {
            mainViewModel.listItems(query)
        } else{
            Log.e("QueryTextError", "Query text is null!")
            Toast.makeText(
                this@MainActivity,
                "You need to type some words!",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    private fun activateProgressBar() {
        mainViewModel.isRefreshed.observe(this@MainActivity){
            if (it == true) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
    }
    private fun observeData(){
        mainViewModel.liveDataResult.observe(this@MainActivity) {
            binding.recyclerView.adapter = MainAdapter(mainViewModel.liveDataResult.value!!, this@MainActivity)
        }
        mainViewModel.isCompleted.observe(this@MainActivity) {
            if (it == true && mainViewModel.liveDataResult.value?.size == 0){
                Toast.makeText(
                    this@MainActivity,
                    "No result found!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onItemClick(item: CommonModel) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("CommonObject", item)
        this@MainActivity.startActivity(intent)
    }
}













