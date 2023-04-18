package com.murqdan.githubaccountapp.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.murqdan.githubaccountapp.R
import com.murqdan.githubaccountapp.adapter.AccountAdapter
import com.murqdan.githubaccountapp.databinding.ActivityMainBinding
import com.murqdan.githubaccountapp.response.ItemsItem
import com.murqdan.githubaccountapp.ui.insert.AccountFavoriteActivity

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: AccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingPreferencesViewModel = ViewModelProvider(this, SettingPreferencesViewModelFactory(pref))[SettingPreferencesViewModel::class.java]
        settingPreferencesViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        mainViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvListAccountHome.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvListAccountHome.addItemDecoration(itemDecoration)

        mainViewModel.githubacc.observe(this) { githubacc ->
            setAccountData(githubacc)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setAccountData(items: List<ItemsItem>) {
        adapter = AccountAdapter(items)
        binding.rvListAccountHome.adapter = adapter
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

    private fun showLoading(isLoading: Boolean) {
        binding.pbHome.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.searchAccount)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.searchHint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    mainViewModel.getAccountSearch(query)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favoritesHome -> {
                val intent = Intent(this, AccountFavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.darkmodeHome -> {
                val intent = Intent(this, ThemeModeActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
