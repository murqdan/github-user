package com.murqdan.githubaccountapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.murqdan.githubaccountapp.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private lateinit var detailAccountViewModel: DetailAccountViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)

        val layoutManager = LinearLayoutManager(context)
        binding.rvFragmentFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        binding.rvFragmentFollow.addItemDecoration(itemDecoration)

        detailAccountViewModel = ViewModelProvider(requireActivity())[DetailAccountViewModel::class.java]

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_POSITION)
        val username = arguments?.getString(ARG_USERNAME)
        if (username != null) {
            detailAccountViewModel.getFollowersAccountGithub(username)
            detailAccountViewModel.getFollowingAccountGithub(username)
        }

        if (position == 1){
            detailAccountViewModel.followersaccountgithub.observe(viewLifecycleOwner) {
                setFollowAccountGithub(it, position)
            }

            detailAccountViewModel.isLoadingFollower.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        } else {
            detailAccountViewModel.followingaccountgithub.observe(viewLifecycleOwner) {
                if (position != null) {
                    setFollowAccountGithub(it, position)
                }
            }

            detailAccountViewModel.isLoadingFollowing.observe(viewLifecycleOwner) {
                showLoading(it)
            }
        }
    }

    private fun setFollowAccountGithub(items: List<ItemsItem>, position: Int) {
        if(items.isEmpty() && position == 1) {
            binding.tvFragmentFollow.setText(R.string.nofollowers)
            binding.tvFragmentFollow.visibility = View.VISIBLE
        } else if (items.isEmpty() && position == 2){
            binding.tvFragmentFollow.setText(R.string.nofollowing)
            binding.tvFragmentFollow.visibility = View.VISIBLE
        } else {
            binding.tvFragmentFollow.visibility = View.GONE
        }
        val adapter = AccountAdapter(items)
        binding.rvFragmentFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.tvFragmentFollow.visibility = View.GONE
            binding.pbFragmentProgressFollow.visibility = View.VISIBLE
            binding.rvFragmentFollow.alpha = 0.0F
        } else {
            binding.pbFragmentProgressFollow.visibility = View.GONE
            binding.rvFragmentFollow.alpha = 1F
        }
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}