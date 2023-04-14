package com.murqdan.githubaccountapp

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
