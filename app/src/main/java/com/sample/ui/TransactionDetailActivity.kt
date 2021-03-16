package com.sample.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sample.*
import com.sample.adapter.TransactionAdapter.Companion.TRANSACTION_ID
import com.sample.adapter.TransactionAdapter.Companion.USER_ID
import com.sample.util.*
import kotlinx.android.synthetic.main.activity_transaction_details.*
import kotlinx.android.synthetic.main.activity_transaction_details.progressBar
import kotlinx.android.synthetic.main.activity_transaction_details.txtLetter

import java.util.*

class TransactionDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_details)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

//        if (supportActionBar != null)
//            supportActionBar?.hide()
        val model = ViewModelProvider(this).get(TransactionDetailViewModel::class.java)

        progressBar.show()
        if (intent.hasExtra(USER_ID)) {
            val userId = intent.getStringExtra(USER_ID)
            val transactionID = intent.getStringExtra(TRANSACTION_ID)
            model.getTransactionSummary(userId!!, transactionID!!)
        }


        if (isNetworkAvailable(this)) {
            progressBar.show()

            model.transactionSummary.observe(this, Observer {
                if (it != null) {
                    progressBar.hide()
                    println(it)
                    txtLetter.text = it.transaction.partner.vPay.substring(0, 1)
                        .toUpperCase(Locale.getDefault())
                    txtName.text = it.transaction.partner.vPay

                    txtMobile.text = getString(R.string.mobile_tag_not_found)
                    txtAmount.text =
                        String.format(getString(R.string.amount), it.transaction.amount.toString())

                    txtStatus.text = convertDateFormat(it.transaction.startDate)

                    txtBankName.text = getString(R.string.bank_tag_not_found)
                    val accountNumber = it.transaction.partner.account.accountNumber.trim()
                    txtBanlAccount.text =
                        maskString(accountNumber, 0, accountNumber.length - 4, 'x')

                    txtTransactionId.text = it.transaction.id.toString()

                    txtReceiverName.text = "To: " + getString(R.string.tag_not_found)
                    txtReceiverEmail.text = it.transaction.partner.vPay

                    txtSenderName.text = "From: " + getString(R.string.tag_not_found)
                    txtSenderEmail.text = it.transaction.customer.vPay
                }
            })
        } else {
            progressBar.hide()
            container.snackbar("Check internet connection")
            finish()
        }
    }
}