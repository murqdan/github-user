package com.murqdan.githubaccountapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _githubacc = MutableLiveData<List<ItemsItem>>()
    val githubacc: LiveData<List<ItemsItem>> = _githubacc

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        const val TAG = "MainViewModel"
    }

    init{
        getGithubAcc()
    }

    private fun getGithubAcc() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAccounts()
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _githubacc.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getAccountSearch(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAccountsSearch(query)
        client.enqueue(object : Callback<GithubAccountResponse> {
            override fun onResponse(
                call: Call<GithubAccountResponse>,
                response: Response<GithubAccountResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _githubacc.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubAccountResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}