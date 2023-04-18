package com.murqdan.githubaccountapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.murqdan.githubaccountapp.response.ItemsItem

@Database(entities = [ItemsItem::class], version = 1)
abstract class AccountRoomDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    companion object {
        @Volatile
        private var INSTANCE: AccountRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): AccountRoomDatabase {
            if (INSTANCE == null) {
                synchronized(AccountRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AccountRoomDatabase::class.java, "account_database")
                        .build()
                }
            }
            return INSTANCE as AccountRoomDatabase
        }
    }
}