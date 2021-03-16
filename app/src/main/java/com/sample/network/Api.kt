package com.sample.network


import com.sample.data.TransactionDetail
import com.rxandroid_retrofit_recycleview_kotlin.data.TransactionHistory

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    companion object {

        const val BASE_URL = "https://a0d93751-a785-4947-a709-02aa9887c5e1.mock.pstmn.io/"
    }

    @GET("getTransactionHistory")
    fun getTransactionHistory(
        @Query("userId") userId: String,
        @Query("recipeintId") recipientId: String
    ): Call<TransactionHistory>

    @GET("getTransactionSummary")
    fun getTransactionSummary(
        @Query("userId") userId: String,
        @Query("transactionId") transactionId: String
    ): Call<TransactionDetail>


}
