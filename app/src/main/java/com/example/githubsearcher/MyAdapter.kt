package com.example.githubsearcher

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation

class MyAdapter(private val myContext: Context , private val myList: List<CommonModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    inner class UserViewHolder(view:View): RecyclerView.ViewHolder(view){
        var userImage:ImageView
        var userInfocard:TextView
        init {
            userImage = view.findViewById(R.id.itemimage)
            userInfocard = view.findViewById(R.id.iteminfocard)
        }
    }
    inner class RepoViewHolder(view: View): RecyclerView.ViewHolder(view){
        var repoImage: ImageView
        var repoInfocard: TextView
        init {
            repoImage = view.findViewById(R.id.itemimage)
            repoInfocard = view.findViewById(R.id.iteminfocard)
        }
    }
    override fun getItemViewType(position: Int): Int {
        if (myList[position] is UserModel) {
            return 0
        } else {
            return 1
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder{
        if (viewType == 0) {
            val my_activity_object =
                LayoutInflater.from(myContext).inflate(R.layout.user_design, parent, false)
            return UserViewHolder(my_activity_object)
        }
        else{
            val my_activity_object =
                LayoutInflater.from(myContext).inflate(R.layout.repo_design, parent, false)
            return RepoViewHolder(my_activity_object)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = myList[position]
        if(getItemViewType(position) == 0){
            item as UserModel
            holder as UserViewHolder
            holder.userInfocard.text = item.login
            holder.userImage.load(item.avatarUrl){
                crossfade(false)
                transformations(CircleCropTransformation())
            }
            holder.itemView.setOnClickListener {
                val intent = Intent(myContext,DetailActivity::class.java)
                intent.putExtra("UserObject",item)
                intent.putExtra("Class","User")
                myContext.startActivity(intent)
            }

        }
        else {
            item as RepoModel
            holder as RepoViewHolder
            holder.repoInfocard.text = item.name
            holder.repoImage.load(item.owner!!.avatarUrl){
                transformations(CircleCropTransformation())
            }
            holder.itemView.setOnClickListener {
                val intent = Intent(myContext,DetailActivity::class.java)
                intent.putExtra("RepoObject",item)
                intent.putExtra("Class","Repo")
                myContext.startActivity(intent)
            }
        }
        }

    override fun getItemCount(): Int {
        return myList.size
    }
    }