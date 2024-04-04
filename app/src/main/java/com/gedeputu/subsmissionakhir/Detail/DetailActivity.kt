package com.gedeputu.subsmissionakhir.Detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.gedeputu.subsmissionakhir.Detail.Favorite.FavoriteActivity
import com.gedeputu.subsmissionakhir.R
import com.gedeputu.subsmissionakhir.databinding.ActivityDetailBinding
import com.gedeputu.subsmissionakhir.ui.Setting.SettingthemeActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID,0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)


        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, user)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        binding.progressBar2.visibility = View.VISIBLE

        if (user != null) {
            viewModel.setUserDetail(user)
            viewModel.getUserDetail().observe(this) {
                if (it != null) {
                    binding.apply {
                        nameView.text = it.name
                        usernameView.text = it.username
                        countRepoView.text = String.format(resources.getString(R.string.repo_count), it.publicRepos)
                        countFollowersView.text = String.format(resources.getString(R.string.followers_count), it.followers)
                        countFollowingView.text = String.format(resources.getString(R.string.following_count), it.following)

                        Glide.with(this@DetailActivity)
                            .load(it.avatar_url)
                            .into(imgAvatar)
                        bioTv.text = it.bio

                        progressBar2.visibility = View.GONE
                    }
                }
            }
        }
        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.toggleFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFavorite.isChecked = false
                        _isChecked = false
                    }
                } else {
                    Toast.makeText(this@DetailActivity, "Error", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        binding.toggleFavorite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                viewModel.insertFavoriteUser(user.toString(), id, avatarUrl.toString())
                Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.removeFavoriteUser(id)
                Toast.makeText(this, "Removed from Favorite", Toast.LENGTH_SHORT).show()
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)

        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabsLayout.setupWithViewPager(viewPager)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
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

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
    }
}