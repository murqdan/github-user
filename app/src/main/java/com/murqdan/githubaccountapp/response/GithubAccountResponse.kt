package com.murqdan.githubaccountapp.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GithubAccountResponse(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

@Entity
@Parcelize
data class ItemsItem(

	@PrimaryKey
	@ColumnInfo(name = "login")
	val login: String,

	@ColumnInfo(name = "avatarUrl")
	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@ColumnInfo(name = "htmlUrl")
	@field:SerializedName("html_url")
	val htmlUrl: String
) : Parcelable