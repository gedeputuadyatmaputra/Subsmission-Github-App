package com.gedeputu.subsmissionakhir.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gedeputu.subsmissionakhir.data.response.User
import com.gedeputu.subsmissionakhir.databinding.ItemUsersBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val list = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null


    fun setList(users: List<User>) {
        val diffResult = DiffUtil.calculateDiff(UserCallback(list, users))
        list.clear()
        list.addAll(users)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class UserViewHolder(private val binding: ItemUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun ImageView.loadImage(url: String?) {
            Glide.with(this.context).load(url).apply(
                RequestOptions().override(500, 500)
            ).centerCrop().into(this)
        }

        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }

            binding.apply {
                avatarImageView.loadImage(user.avatar_url)
                usernameTextView.text = user.username

            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val view = ItemUsersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size


    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}