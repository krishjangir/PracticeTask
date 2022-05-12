package com.practicetask.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practicetask.R
import com.practicetask.callbacks.WithdrawListener
import com.practicetask.data.model.ATMData
import com.practicetask.data.repository.ATMRepository
import com.practicetask.ui.MainActivity


class MainViewModel(var context: Context) : ViewModel() {
    var amount = ""
    var availableAmountData: LiveData<ATMData>? = null
    var withdrawListener: WithdrawListener? = null

    //withdraw button click handle
    fun onWithdrawButtonClick(view: View) {
        withdrawListener!!.onStarted()
        if (amount.isEmpty()
        ) {
            withdrawListener!!.onFailure(context.resources.getString(R.string.please_enter_amount))
            return
        }
        if (amount.isNotEmpty() && amount.toInt() % 100 != 0
        ) {
            withdrawListener!!.onFailure(context.resources.getString(R.string.please_enter_amount_valid_amount))
            return
        }
        if (amount.isNotEmpty() && amount.toInt() > 10000
        ) {
            withdrawListener!!.onFailure(context.resources.getString(R.string.you_can_withdraw_only_10000_at_a_time))
            return
        }

        val isSuccess: Boolean =
            ATMRepository.withdrawAmount(context, ATMData(amount = amount.toInt()))
        withdrawListener!!.onSuccess(isSuccess)
    }

    //withdraw button click handle
    fun insertAmountInATM(amount: ATMData) {
        ATMRepository.insertAmountInATM(context, amount)
    }

    //getAllTransactions
    fun getAllTransactions(): LiveData<List<ATMData>>? {
        return ATMRepository.getAllTransactions(context)
    }

    //getAvailableAmount
    fun getAvailableAmount() {
        availableAmountData = ATMRepository.getAvailableAmount(context)
    }


    //Create factory
    class MainViewModelFactory(var context: Context) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(context) as T
        }
    }

}