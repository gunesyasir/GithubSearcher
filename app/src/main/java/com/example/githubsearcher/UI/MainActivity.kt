package com.example.githubsearcher.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubsearcher.Model.CommonModel
import com.example.githubsearcher.UI.ViewModel.MainViewModel
import com.example.githubsearcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainAdapter.RecyclerViewItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViews()
        mainViewModel.liveDataResult.observe(this@MainActivity, Observer {
            binding.recyclerView.adapter = MainAdapter(mainViewModel.liveDataResult.value!!, this@MainActivity)
        })
    }

    private fun setViews(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
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

    override fun onItemClick(item: CommonModel) {
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra("CommonObject", item)
        this@MainActivity.startActivity(intent)
    }
}













