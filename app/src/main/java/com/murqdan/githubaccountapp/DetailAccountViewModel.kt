package com.murqdan.githubaccountapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response

class DetailAccountViewModel : ViewModel(){

    companion object {
        const val TAG = "DetailAccountViewModel"
    }

    private val _detailaccountgithub = MutableLiveData<DetailGithubAccountResponse>()
    val detailaccountgithub: LiveData<DetailGithubAccountResponse> = _detailaccountgithub

    private val _followersaccountgithub = MutableLiveData<List<ItemsItem>>()
    val followersaccountgithub: LiveData<List<ItemsItem>> = _followersaccountgithub

    private val _followingaccountgithub = MutableLiveData<List<ItemsItem>>()
    val followingaccountgithub: LiveData<List<ItemsItem>> = _followingaccountgithub

    private val _isLoadingDetailAcc = MutableLiveData<Boolean>()
    val isLoadingDetailAcc: LiveData<Boolean> = _isLoadingDetailAcc

    private val _isLoadingFollower = MutableLiveData<Boolean>()
    val isLoadingFollower: LiveData<Boolean> = _isLoadingFollower

    private val _isLoadingFollowing = MutableLiveData<Boolean>()
    val isLoadingFollowing: LiveData<Boolean> = _isLoadingFollowing

    fun getDetailAccount(login: String) {
        _isLoadingDetailAcc.value = true
        val api = ApiConfig.getApiService().getDetailAccount(login)
        api.enqueue(object : retrofit2.Callback<DetailGithubAccountResponse> {
            override fun onResponse(call: Call<DetailGithubAccountResponse>, response: Response<DetailGithubAccountResponse>) {
                _isLoadingDetailAcc.value = false
                if (response.isSuccessful) {
                    _detailaccountgithub.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailGithubAccountResponse>, t: Throwable) {
                _isLoadingDetailAcc.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getFollowersAccountGithub(login: String) {
        _isLoadingFollower.value = true
        val api = ApiConfig.getApiService().getFollowers(login)
        api.enqueue(object : retrofit2.Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollower.value = false
                if (response.isSuccessful) {
                    _followersaccountgithub.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollower.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getFollowingAccountGithub(login: String) {
        _isLoadingFollowing.value = true
        val api = ApiConfig.getApiService().getFollowing(login)
        api.enqueue(object : retrofit2.Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoadingFollowing.value = false
                if (response.isSuccessful) {
                    _followingaccountgithub.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoadingFollowing.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}