package com.example.githubsearcher.UI

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.githubsearcher.Model.CommonModel
import com.example.githubsearcher.Model.RepoModel
import com.example.githubsearcher.Model.UserModel
import com.example.githubsearcher.databinding.RepoDesignBinding
import com.example.githubsearcher.databinding.UserDesignBinding

class MainAdapter(
    private val resultList: List<CommonModel>,
    private val listener: RecyclerViewItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface RecyclerViewItemClickListener {
        fun onItemClick(item: CommonModel)
    }

    inner class UserViewHolder(val userBinding: UserDesignBinding) :
        RecyclerView.ViewHolder(userBinding.root)

    inner class RepoViewHolder(val repoBinding: RepoDesignBinding) :
        RecyclerView.ViewHolder(repoBinding.root)

    private val TYPE_USER = 0
    private val TYPE_REPO = 1
    override fun getItemViewType(position: Int): Int {
        if (resultList[position] is UserModel) {
            return TYPE_USER
        } else {
            return TYPE_REPO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_USER) {
            val userBinding =
                UserDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return UserViewHolder(userBinding)
        } else {
            val repoBinding =
                RepoDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return RepoViewHolder(repoBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = resultList[position]
        if (getItemViewType(position) == TYPE_USER) {
            with(holder as UserViewHolder) {
                with(item as UserModel) {
                    userBinding.itemInfoCard.text = this.login
                    userBinding.itemImage.load(this.avatarUrl) {
                        crossfade(false)
                        transformations(CircleCropTransformation())
                    }
                    userBinding.divider.setOnClickListener {
                        listener.onItemClick(this)
                    }
                }
            }
        }
        else {
            with(holder as RepoViewHolder) {
                with(item as RepoModel) {
                    repoBinding.itemInfoCard.text = this.name
                    if(this.owner?.avatarUrl != null) {
                        repoBinding.itemImage.load(this.owner!!.avatarUrl) {
                            crossfade(false)
                            transformations(CircleCropTransformation())
                        }
                    }
                    repoBinding.divider.setOnClickListener {
                        listener.onItemClick(this)
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return resultList.size
    }
}