package com.practicetask.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.practicetask.R
import com.practicetask.callbacks.WithdrawListener
import com.practicetask.data.model.ATMData
import com.practicetask.databinding.ActivityMainBinding
import com.practicetask.ui.adapter.TransactionAdapter
import com.practicetask.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), WithdrawListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        val viewModel =
            ViewModelProvider(
                this,
                MainViewModel.MainViewModelFactory(this)
            )[MainViewModel::class.java]
        viewModel.withdrawListener = this
        binding.mainViewModel = viewModel

        viewModel.insertAmountInATM(
            ATMData(
                amount = 50000,
                oneHundred = 100,
                twoHundred = 50,
                fiveHundred = 20,
                twoThousand = 10,
                type = 0
            )
        )


        viewModel.getAllTransactions()?.observe(this) { transList ->
            transactionAdapter = TransactionAdapter(this, transList as ArrayList<ATMData>)
            if (transList.isNotEmpty()) {
                binding.lastTransactions = transList[0]
            }
            binding.transactionAdapter = transactionAdapter
        }

        viewModel.getAvailableAmount()

    }

    override fun onStarted() {
        Toast.makeText(this, resources.getString(R.string.transactions_started), Toast.LENGTH_SHORT)
            .show()
    }

    override fun onSuccess(isSuccess: Boolean) {
        if (isSuccess) {
            Toast.makeText(
                this,
                resources.getString(R.string.transactions_success),
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            Toast.makeText(
                this,
                resources.getString(R.string.transactions_failed),
                Toast.LENGTH_SHORT
            )
                .show()
        }

    }

    override fun onFailure(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}