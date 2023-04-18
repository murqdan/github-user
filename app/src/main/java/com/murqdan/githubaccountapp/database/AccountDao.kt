package com.murqdan.githubaccountapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.murqdan.githubaccountapp.response.ItemsItem

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(account: ItemsItem)

    @Delete
    fun delete(account: ItemsItem)

    @Query("SELECT * FROM ItemsItem ORDER BY login DESC")
    fun getAllAccounts(): LiveData<List<ItemsItem>>
}