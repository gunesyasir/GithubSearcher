package com.example.githubsearcher.Model.ViewModel.View

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.AbsoluteSizeSpan
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.githubsearcher.Model.RepoModel
import com.example.githubsearcher.Model.UserModel
import com.example.githubsearcher.databinding.ActivityDetailBinding
import java.io.Serializable

class DetailActivity : AppCompatActivity(), Serializable {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setViews()
    }

    private fun setViews() {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.clearFocus()
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setInfos()
    }

    private fun setInfos() {
        if (intent.getSerializableExtra("CommonObject") is UserModel) {
            val userModel = intent.getSerializableExtra("CommonObject") as UserModel
            binding.apply {
                itemImageView.load(userModel.avatarUrl)
                itemName.text = userModel.login.toString()
                itemID.text = userModel.id.toString()
                itemScore.text = userModel.score.toString()
                itemHtmlUrl.text = userModel.htmlUrl.toString()
            }
        } else {
            val repoModel = intent.getSerializableExtra("CommonObject") as RepoModel
            binding.apply {
                itemImageView.load(repoModel.owner!!.avatarUrl)
                itemName.text = repoModel.name.toString()
                itemID.text = repoModel.id.toString()
                itemScore.text = repoModel.score.toString()
                itemHtmlUrl.text = repoModel.htmlUrl.toString()
                    val staticValue = "Owner: "
                    val spannable = SpannableString(staticValue  + repoModel.owner!!.login.toString())
                    val styleSpan = StyleSpan(Typeface.BOLD_ITALIC)
                    val sizeSpan = AbsoluteSizeSpan(100)
                    val clickableSpan = object: ClickableSpan(){
                        override fun onClick(textView: View) {
                            val intent = Intent(this@DetailActivity, DetailActivity::class.java)
                            intent.putExtra("CommonObject", repoModel.owner )
                            startActivity(intent)
                        }
                    }
                    spannable.setSpan(sizeSpan, 0, staticValue.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    spannable.setSpan(styleSpan, 0, staticValue.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    spannable.setSpan(clickableSpan, staticValue.length, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    repoOwner.movementMethod = LinkMovementMethod.getInstance()
                    repoOwner.text = spannable
            }
        }
    }
}