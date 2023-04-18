package com.murqdan.githubaccountapp.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.murqdan.githubaccountapp.*
import com.murqdan.githubaccountapp.adapter.SectionsPagerAdapter
import com.murqdan.githubaccountapp.databinding.ActivityDetailAccountBinding
import com.murqdan.githubaccountapp.response.DetailGithubAccountResponse
import com.murqdan.githubaccountapp.response.ItemsItem
import com.murqdan.githubaccountapp.ui.insert.AccountFavoriteViewModelFactory

class DetailAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAccountBinding
    private lateinit var detailAccountViewModel: DetailAccountViewModel
    private var isFavorite: Boolean = false

    companion object {
        const val EXTRA_ACCOUNT = "extra_account"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.vpFollow)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabsFollow)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        detailAccountViewModel = obtainViewModel(this)

        val accountGithub = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_ACCOUNT, ItemsItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_ACCOUNT)
        }

        if (accountGithub != null) {
            detailAccountViewModel.getDetailAccount(accountGithub.login)
            sectionsPagerAdapter.username = accountGithub.login
        }

        detailAccountViewModel.detailaccountgithub.observe(this) {
            setDetailAccount(it)
        }

        detailAccountViewModel.isLoadingDetailAcc.observe(this) {
            showLoading(it)
        }

        detailAccountViewModel.getAllAccounts().observe(this) {
            isFavorite = it.contains(accountGithub)
            if (isFavorite) {
                binding.fabDetailAccount.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.favorite_home))
            } else {
                binding.fabDetailAccount.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.favorite_border))
            }
        }

        binding.fabDetailAccount.setOnClickListener {
            if (isFavorite) {
                detailAccountViewModel.delete(accountGithub!!)
            } else {
                detailAccountViewModel.insert(accountGithub!!)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailAccountViewModel {
        val factory = AccountFavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailAccountViewModel::class.java]
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.pbDetail.visibility = View.VISIBLE
        } else {
            binding.pbDetail.visibility = View.GONE
        }
    }

    private fun setDetailAccount(detailgithubaccount: DetailGithubAccountResponse) {
        binding.tvUsernameToolBar.text = detailgithubaccount.login
        binding.tvDetailFullname.text = detailgithubaccount.name
        binding.tvDetailCountFollowers.text = detailgithubaccount.followers.toString()
        binding.tvDetailCountFollowing.text = detailgithubaccount.following.toString()
        Glide.with(this)
            .load(detailgithubaccount.avatarUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.ivDetailAvatar)
    }
}