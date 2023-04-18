package com.murqdan.githubaccountapp.ui.insert

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.murqdan.githubaccountapp.repository.AccountRepository
import com.murqdan.githubaccountapp.response.ItemsItem

class AccountFavoriteViewModel(application: Application) : ViewModel() {
    private val mAccountRepository: AccountRepository = AccountRepository(application)

    fun getAllAccounts(): LiveData<List<ItemsItem>> = mAccountRepository.getAllAccounts()
}