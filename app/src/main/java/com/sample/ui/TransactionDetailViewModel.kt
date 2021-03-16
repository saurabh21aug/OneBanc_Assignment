package com.sample.ui


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.data.TransactionDetail
import com.sample.network.Retrofit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TransactionDetailViewModel : ViewModel() {


    private var _transactionSummary: MutableLiveData<TransactionDetail>? = null
    val transactionSummary: LiveData<TransactionDetail>
        get() = _transactionSummary!!


    fun getTransactionSummary(userId: String, transactionId:String) {
        if (_transactionSummary == null) {
            _transactionSummary = MutableLiveData()
            loadData(userId, transactionId)
        }
    }

//    fun getTransactionSummary() {
//        if (_transactionSummary == null) {
//            _transactionSummary = MutableLiveData()
//            loadData()
//        }
//    }
//
//    private fun loadData() {
//        val call = Retrofit.api.getTransactionSummary(1, 1957)
//        call.enqueue(object : Callback<TransactionDetail> {
//            override fun onFailure(call: Call<TransactionDetail>, t: Throwable) {
//                Log.e("onFailure ", t.toString())
//            }
//
//            override fun onResponse(
//                call: Call<TransactionDetail>,
//                response: Response<TransactionDetail>
//            ) {
//                val res = response.body()
//                if (res != null) {
//                    println(response)
//                    _transactionSummary!!.value = res
//
//                }
//            }
//        })
//    }

    private fun loadData(userId: String, transactionId: String) {
        val call = Retrofit.api.getTransactionSummary(userId, transactionId)
        call.enqueue(object : Callback<TransactionDetail> {
            override fun onFailure(call: Call<TransactionDetail>, t: Throwable) {
                Log.e("onFailure ", t.toString())
            }

            override fun onResponse(
                call: Call<TransactionDetail>,
                response: Response<TransactionDetail>
            ) {
                val res = response.body()
                if (res != null) {
                    println(response)
                    _transactionSummary!!.value = res

                }
            }
        })
    }
}


