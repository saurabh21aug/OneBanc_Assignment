package com.sample.ui


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.sample.*
import com.sample.adapter.TransactionAdapter
import com.sample.util.hide
import com.sample.util.isNetworkAvailable
import com.sample.util.show
import com.sample.util.snackbar

import kotlinx.android.synthetic.main.activity_transaction_history.*
import kotlinx.android.synthetic.main.activity_transaction_history.progressBar
import java.util.*


class TransactionHistoryActivity : AppCompatActivity() {

    var adapter: TransactionAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true);

        val model = ViewModelProvider(this).get(TransactionHistoryViewModel::class.java)

        adapter = TransactionAdapter(this@TransactionHistoryActivity, null)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.visibility = View.INVISIBLE

//        val helper: SnapHelper = LinearSnapHelper()
//        helper.attachToRecyclerView(recyclerView)

        if (intent.hasExtra(HomeActivity.USER_ID)) {
            val userId = intent.getStringExtra(HomeActivity.USER_ID)
            val recipientIdId = intent.getStringExtra(HomeActivity.RECIPIENT_ID)
            model.getTransaction(userId!!, recipientIdId!!)
        }

        if (isNetworkAvailable(this)) {
            progressBar.show()

            model.transaction.observe(this, Observer {
                if (it != null) {
                    txtLetter.text = it.transactions[0].partner.vPay.substring(0, 1)
                        .toUpperCase(Locale.getDefault())
                    txtNameToolbar.text = it.transactions[0].partner.vPay
                    txtMobileToolbar.text = getString(R.string.mobile_tag_not_found)


                    adapter?.setData(it)
                    recyclerView.visibility = View.VISIBLE
                    progressBar.hide()
                }
            })
        } else {
            progressBar.hide()
            container.snackbar("Check internet connection")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }
}

