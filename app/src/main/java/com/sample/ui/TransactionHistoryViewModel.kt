package com.sample.ui


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.network.Retrofit
import com.rxandroid_retrofit_recycleview_kotlin.data.TransactionHistory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TransactionHistoryViewModel : ViewModel() {


    private var _transactions: MutableLiveData<TransactionHistory>? = null
    val transaction: LiveData<TransactionHistory>
        get() = _transactions!!

    fun getTransaction(userId: String, recipientId: String) {
        if (_transactions == null) {
            _transactions = MutableLiveData()
            loadData(userId, recipientId)
        }
    }

    private fun loadData(userId: String, recipientId: String) {
        val call = Retrofit.api.getTransactionHistory(userId, recipientId)
        call.enqueue(object : Callback<TransactionHistory> {
            override fun onFailure(call: Call<TransactionHistory>, t: Throwable) {
                Log.e("onFailure ", t.toString())
            }

            override fun onResponse(
                call: Call<TransactionHistory>,
                response: Response<TransactionHistory>
            ) {
                val res = response.body()
                if (res != null) {
                    println(response)
                    _transactions!!.value = res

                }
            }
        })
    }
}


