package com.example.githubsearcher

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation

class MainAdapter(
    private val adapterContext: Context,
    private val resultList: List<CommonModel>,
    private val listener: RecyclerViewClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    interface RecyclerViewClickListener {
        fun onClick(item : CommonModel)
    }


    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var userImage: ImageView
        var userInfocard: TextView

        init {
            userImage = view.findViewById(R.id.itemImage)
            userInfocard = view.findViewById(R.id.itemInfoCard)
        }

    }

    inner class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var repoImage: ImageView
        var repoInfocard: TextView

        init {
            repoImage = view.findViewById(R.id.itemImage)
            repoInfocard = view.findViewById(R.id.itemInfoCard)
        }
    }

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
            val view =
                LayoutInflater.from(adapterContext).inflate(R.layout.user_design, parent, false)
            return UserViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(adapterContext).inflate(R.layout.repo_design, parent, false)
            return RepoViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = resultList[position]
        if (getItemViewType(position) == TYPE_USER) {
            item as UserModel
            holder as UserViewHolder
            holder.userInfocard.text = item.login
            holder.userImage.load(item.avatarUrl) {
                crossfade(false)
                transformations(CircleCropTransformation())
            }
            holder.itemView.setOnClickListener {
                listener.onClick(item)
            }
        }
        else {
            item as RepoModel
            holder as RepoViewHolder
            holder.repoInfocard.text = item.name
            holder.repoImage.load(item.owner!!.avatarUrl) {
                transformations(CircleCropTransformation())
            }
            holder.itemView.setOnClickListener {
                listener.onClick(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return resultList.size
    }
}