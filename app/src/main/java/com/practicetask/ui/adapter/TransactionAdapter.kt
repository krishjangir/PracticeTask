package com.practicetask.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.practicetask.R
import com.practicetask.data.model.ATMData
import com.practicetask.databinding.TransactionRowBinding

class TransactionAdapter(
    private val context: Context,
    private val transactionList: ArrayList<ATMData>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var transactionRowBinding: TransactionRowBinding


    @NonNull
    override fun onCreateViewHolder(
        @NonNull parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        transactionRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.transaction_row,
            parent,
            false
        )

        return TransactionViewHolder(
            transactionRowBinding
        )
    }

    override fun onBindViewHolder(@NonNull holder: RecyclerView.ViewHolder, position: Int) {
        val transactionData = transactionList[position]
        when (holder) {
            is TransactionViewHolder -> {
                holder.binding.transactionData = transactionData
            }
        }
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    /*--------------Transaction ViewHolder---------------*/
    inner class TransactionViewHolder(internal val binding: TransactionRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}