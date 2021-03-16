package com.sample.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.sample.databinding.ActivityHomeBinding
import com.sample.util.hideKeyboard
import com.sample.util.isNetworkAvailable
import com.sample.util.snackbar
import kotlinx.android.synthetic.main.activity_transaction_history.*

class HomeActivity : AppCompatActivity() {


    companion object {
        const val USER_ID = "userId"
        const val RECIPIENT_ID = "recipientId"
    }

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding.btnSubmit.setOnClickListener {
            callHistoryScreen()
        }

    }

    private fun callHistoryScreen() {
        hideKeyboard(binding.btnSubmit)
        if (binding.edtUserId.text.isEmpty()) {
            binding.container.snackbar("Enter user Id")
            return
        } else if (binding.edtRecipientId.text.isEmpty()) {
            binding.container.snackbar("Enter recipient Id")
            return
        }

        if (isNetworkAvailable(this)) {
            val intent = Intent(this, TransactionHistoryActivity::class.java)
            intent.putExtra(USER_ID, binding.edtUserId.text.toString())
            intent.putExtra(RECIPIENT_ID, binding.edtRecipientId.text.toString())

            startActivity(intent)
        } else {
            container.snackbar("Check internet connection")
        }
    }
}