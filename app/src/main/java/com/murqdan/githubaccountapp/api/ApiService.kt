package com.murqdan.githubaccountapp.api

import com.murqdan.githubaccountapp.response.DetailGithubAccountResponse
import com.murqdan.githubaccountapp.response.GithubAccountResponse
import com.murqdan.githubaccountapp.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("users")
    fun getAccounts(
    ): Call<List<ItemsItem>>

    @GET("search/users")
    fun getAccountsSearch(
        @Query("q") login: String
    ): Call<GithubAccountResponse>

    @GET("users/{login}")
    fun getDetailAccount(
        @Path("login") login: String
    ): Call<DetailGithubAccountResponse>

    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login") login: String
    ): Call<List<ItemsItem>>

    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login: String
    ): Call<List<ItemsItem>>
}
