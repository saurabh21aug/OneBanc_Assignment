package com.rxandroid_retrofit_recycleview_kotlin.data

import com.sample.data.Transaction

data class TransactionHistory(
    val userId: String,
    val receipientId: String,
    val transactions: List<Transaction>
)



