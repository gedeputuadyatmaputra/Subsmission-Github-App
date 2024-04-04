package com.gedeputu.subsmissionakhir.Detail.Following

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gedeputu.subsmissionakhir.Detail.DetailActivity
import com.gedeputu.subsmissionakhir.R
import com.gedeputu.subsmissionakhir.databinding.FollowuserFragmentBinding
import com.gedeputu.subsmissionakhir.ui.Adapter.UserAdapter

class FollowingFragment : Fragment(R.layout.followuser_fragment) {

    private var _binding: FollowuserFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FollowuserFragmentBinding.bind(view)

        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            followuserRv.setHasFixedSize(true)
            followuserRv.layoutManager = LinearLayoutManager(activity)
            followuserRv.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowingViewModel::class.java]

        viewModel.setListFollowing(username)

        viewModel.getListFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (message != null) {
                showLoading(false)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressfollow.visibility = View.VISIBLE
        } else {
            binding.progressfollow.visibility = View.GONE
        }
    }
}