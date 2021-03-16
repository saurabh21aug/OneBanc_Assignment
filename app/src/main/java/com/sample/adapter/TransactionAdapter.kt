package com.sample.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sample.R
import com.sample.util.convertDateFormat
import com.sample.data.Transaction
import com.sample.ui.TransactionDetailActivity
import com.rxandroid_retrofit_recycleview_kotlin.data.TransactionHistory
import com.sample.util.isNetworkAvailable
import com.sample.util.snackbar
import kotlinx.android.synthetic.main.activity_transaction_details.*
import kotlinx.android.synthetic.main.item_layout_left.view.*
import kotlinx.android.synthetic.main.item_layout_right.view.*


class TransactionAdapter(
    private var context: Context,
    private var transactionHistory: TransactionHistory?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val DIRECTION_SEND_R: Int = 1
        private const val DIRECTION_RECEIVE_L: Int = 2
        const val USER_ID = "userId"
        const val TRANSACTION_ID = "transactionId"
    }

    inner class ViewHolderRight(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(context: Context, transaction: Transaction, userId: String) {

            itemView.apply {
                amountR.text =
                    String.format(context.getString(R.string.amount), transaction.amount.toString())
                txtDateTimeR.text = convertDateFormat(transaction.startDate)

                if (transaction.type == 1) {

                    txtStatusR.text = context.getString(R.string.you_paid)
                    txtStatusR.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_check,
                        0,
                        0,
                        0
                    )
                    layoutTransactionR.visibility = View.VISIBLE
                    layoutCancelR.visibility = View.GONE
                    txtTransactionIDR.text = transaction.id.toString()
                } else {
                    txtStatusR.text = context.getString(R.string.you_requested)
                    txtStatusR.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_attachment,
                        0,
                        0,
                        0
                    )
                    layoutTransactionR.visibility = View.GONE
                    layoutCancelR.visibility = View.VISIBLE
                }
                layoutParentR.setOnClickListener {
                    if (isNetworkAvailable(context)) {
                        val intent = Intent(context, TransactionDetailActivity::class.java)
                        intent.putExtra(USER_ID, userId)
                        intent.putExtra(TRANSACTION_ID, transaction.id.toString())
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    } else {
                        itemView.snackbar("Check internet connection")
                    }
                }
            }

        }
    }

    inner class ViewHolderLeft(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(context: Context, transaction: Transaction, userId: String) {
            itemView.amountL.text =
                String.format(context.getString(R.string.amount), transaction.amount.toString())
            itemView.txtDateTimeL.text = convertDateFormat(transaction.startDate)

            if (transaction.type == 1) {
                itemView.txtStatusL.text = context.getString(R.string.you_received)
                itemView.txtStatusL.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_check,
                    0,
                    0,
                    0
                )
                itemView.layoutTransactionL.visibility = View.VISIBLE
                itemView.layoutCancelL.visibility = View.GONE
                itemView.txtTransactionIDL.text = transaction.id.toString()
            } else {
                itemView.txtStatusL.text = context.getString(R.string.request_received)
                itemView.txtStatusL.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_attachment,
                    0,
                    0,
                    0
                )
                itemView.layoutTransactionL.visibility = View.GONE
                itemView.layoutCancelL.visibility = View.VISIBLE
            }

            itemView.layoutParentL.setOnClickListener {
                if (isNetworkAvailable(context)) {
                    val intent = Intent(context, TransactionDetailActivity::class.java)
                    intent.putExtra(USER_ID, userId)
                    intent.putExtra(TRANSACTION_ID, transaction.id.toString())
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                } else {
                    itemView.snackbar("Check internet connection")
                }
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (transactionHistory?.transactions!![position].direction == 1) {
            DIRECTION_SEND_R
        } else {
            DIRECTION_RECEIVE_L
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DIRECTION_RECEIVE_L) {
            (ViewHolderLeft(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_layout_left, parent, false)
            ))
        } else {
            (ViewHolderRight(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_layout_right, parent, false)
            ))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DIRECTION_SEND_R) {
            transactionHistory?.transactions?.get(position)
                ?.let { (holder as ViewHolderRight).bind(context, it, transactionHistory!!.userId) }
        } else
            transactionHistory?.transactions?.get(position)
                ?.let { (holder as ViewHolderLeft).bind(context, it, transactionHistory!!.userId) }
    }

    override fun getItemCount() = transactionHistory?.transactions?.size ?: 0


    fun setData(transactionHistory: TransactionHistory?) {
        this.transactionHistory = transactionHistory
        notifyDataSetChanged()
    }
}



