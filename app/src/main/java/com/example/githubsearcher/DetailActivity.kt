package com.example.githubsearcher

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.githubsearcher.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var imageView: ImageView
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var textView4: TextView
    private lateinit var textView5: TextView
    private lateinit var textView6: TextView
    private lateinit var textView7: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.clearFocus()
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val className = intent.getStringExtra("Class")
        imageView = binding.imageview
        textView1 = binding.textview1
        textView2 = binding.textview2
        textView3 = binding.textview3
        textView4 = binding.textview4
        textView5 = binding.textview5
        textView6 = binding.textview6
        textView7 = binding.textview7
        if (className.equals("User")) {
            val userModel = intent.getSerializableExtra("UserObject") as UserModel
            imageView.load(userModel.avatarUrl)
            textView1.text = userModel.login.toString()
            textView2.text = userModel.eventsUrl.toString()
            textView3.text = userModel.followersUrl.toString()
            textView4.text = userModel.followingUrl.toString()
            textView5.text = userModel.gistsUrl.toString()
            textView6.text = userModel.gravatarId.toString()
        } else {
            val repoModel = intent.getSerializableExtra("RepoObject") as RepoModel
            imageView.load(repoModel.owner!!.avatarUrl)
            textView1.text = repoModel.name.toString()
            textView2.text = repoModel.description.toString()
            textView3.text = repoModel.archiveUrl.toString()
            textView4.text = repoModel.blobsUrl.toString()
            textView5.text = repoModel.branchesUrl.toString()
            textView6.text = repoModel.commentsUrl.toString()
            textView7.text = repoModel.createdAt.toString()
        }
    }
}