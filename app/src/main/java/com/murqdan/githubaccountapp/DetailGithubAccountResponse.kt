package com.murqdan.githubaccountapp

import com.google.gson.annotations.SerializedName

data class DetailGithubAccountResponse(

	@field:SerializedName("login")
	var login: String? = null,

	@field:SerializedName("followers")
	var followers: Int? = null,

	@field:SerializedName("avatar_url")
	var avatarUrl: String? = null,

	@SerializedName("html_url")
	var htmlUrl: String?,

	@field:SerializedName("following")
	var following: Int? = null,

	@field:SerializedName("name")
	var name: String? = null,
)