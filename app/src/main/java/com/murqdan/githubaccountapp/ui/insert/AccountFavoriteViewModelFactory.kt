package com.murqdan.githubaccountapp.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.murqdan.githubaccountapp.ui.DetailAccountViewModel

class AccountFavoriteViewModelFactory(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: AccountFavoriteViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): AccountFavoriteViewModelFactory {
            if (INSTANCE == null) {
                synchronized(AccountFavoriteViewModelFactory::class.java) {
                    INSTANCE = AccountFavoriteViewModelFactory(application)
                }
            }
            return INSTANCE as AccountFavoriteViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountFavoriteViewModel::class.java)) {
            return AccountFavoriteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(DetailAccountViewModel::class.java)) {
            return DetailAccountViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}