package com.sample.data


data class Transaction(
    val id: Long,
    val startDate: String,
    val endDate: String,
    val amount: Long,
    val direction: Int,
    val type: Int,
    val status: Int,
    val description: String,
    val customer: Customer,
    val partner: Customer
)