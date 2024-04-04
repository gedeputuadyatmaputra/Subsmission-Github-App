package com.gedeputu.subsmissionakhir.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.gedeputu.subsmissionakhir.Detail.DetailActivity
import com.gedeputu.subsmissionakhir.Detail.Favorite.FavoriteActivity
import com.gedeputu.subsmissionakhir.R
import com.gedeputu.subsmissionakhir.data.response.User
import com.gedeputu.subsmissionakhir.databinding.ActivityMainBinding
import com.gedeputu.subsmissionakhir.ui.Adapter.UserAdapter
import com.gedeputu.subsmissionakhir.ui.Setting.SettingthemeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        binding.apply {
            rvAdapter.layoutManager = LinearLayoutManager(this@MainActivity)
            rvAdapter.setHasFixedSize(true)
            rvAdapter.adapter = adapter

            SearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) {
                        searchUser(query)
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

        searchUser("mojo")

        viewModel.getSearchUsers().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
    }
    private fun searchUser(query: String) {
        showLoading(true)
        viewModel.setSearchUsers(query)
        viewModel.getSearchUsers().observe(this) { users ->
            if (users.isNullOrEmpty()) {
                Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setList(users)
            }
            lifecycleScope.launch {
                delay(1000)
                showLoading(false)
            }
        }
    }
    private fun showLoading(state: Boolean) {
        binding.apply {
            if (state) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.settings -> {
                val intent = Intent(this, SettingthemeActivity::class.java)
                startActivity(intent)
            }
            R.id.favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}