package com.gedeputu.subsmissionakhir.Detail.Favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gedeputu.subsmissionakhir.Detail.DetailActivity
import com.gedeputu.subsmissionakhir.R
import com.gedeputu.subsmissionakhir.data.Database.UserFavorite
import com.gedeputu.subsmissionakhir.data.response.User
import com.gedeputu.subsmissionakhir.databinding.ActivityFavoriteBinding
import com.gedeputu.subsmissionakhir.ui.Adapter.UserAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        supportActionBar?.title = resources.getString(R.string.Favoriteinfo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = adapter
        }
        viewModel.getFavoriteUser()?.observe(this) { user ->
            if (user != null) {
                val list = mapList(user)
                adapter.setList(list)
            }
        }
    }
    private fun mapList(user: List<UserFavorite>) : ArrayList<User> {
        val listUser = ArrayList<User>()
        for (users in user) {
            val userMapp = User (
                users.login,
                users.id,
                users.avatar_url
            )
            listUser.add(userMapp)
        }
        return listUser
    }
}