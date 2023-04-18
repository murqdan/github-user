package com.murqdan.githubaccountapp.ui.insert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.murqdan.githubaccountapp.R
import com.murqdan.githubaccountapp.adapter.AccountAdapter
import com.murqdan.githubaccountapp.databinding.ActivityAccountFavoriteBinding
import com.murqdan.githubaccountapp.response.ItemsItem
import com.murqdan.githubaccountapp.ui.DetailAccountActivity

class AccountFavoriteActivity : AppCompatActivity() {
    private var _activityAccountFavoriteBinding: ActivityAccountFavoriteBinding? = null
    private val binding get() = _activityAccountFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityAccountFavoriteBinding = ActivityAccountFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = resources.getString(R.string.titleFavActivity)

        binding?.rvFavoriteAccount?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavoriteAccount?.setHasFixedSize(true)

        val accountFavoriteViewModel = obtainViewModel(this)
        accountFavoriteViewModel.getAllAccounts().observe(this) { accountList ->
            if (accountList.isNotEmpty()) {
                setAccountData(accountList)
            } else {
                binding?.rvFavoriteAccount?.visibility = View.GONE
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): AccountFavoriteViewModel {
        val factory = AccountFavoriteViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[AccountFavoriteViewModel::class.java]
    }

    private fun setAccountData(items: List<ItemsItem>) {
        val adapter = AccountAdapter(items)
        binding?.rvFavoriteAccount?.adapter = adapter
        adapter.setOnItemClickCallback(object : AccountAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                sendSelectedAccount(data)
            }
        })
    }

    private fun sendSelectedAccount(data: ItemsItem) {
        val intent = Intent(this, DetailAccountActivity::class.java)
        intent.putExtra(DetailAccountActivity.EXTRA_ACCOUNT, data)
        startActivity(intent)
    }
}