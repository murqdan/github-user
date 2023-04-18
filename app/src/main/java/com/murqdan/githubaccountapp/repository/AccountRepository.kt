package com.murqdan.githubaccountapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.murqdan.githubaccountapp.database.AccountDao
import com.murqdan.githubaccountapp.database.AccountRoomDatabase
import com.murqdan.githubaccountapp.response.ItemsItem
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AccountRepository(application: Application) {
    private val mAccountsDao: AccountDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = AccountRoomDatabase.getDatabase(application)
        mAccountsDao = db.accountDao()
    }

    fun getAllAccounts(): LiveData<List<ItemsItem>> = mAccountsDao.getAllAccounts()

    fun insert(account: ItemsItem) {
        executorService.execute { mAccountsDao.insert(account) }
    }

    fun delete(account: ItemsItem) {
        executorService.execute { mAccountsDao.delete(account) }
    }
}