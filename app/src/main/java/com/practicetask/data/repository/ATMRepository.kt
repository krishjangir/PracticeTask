package com.practicetask.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.practicetask.data.local.database.AppDatabase
import com.practicetask.data.model.ATMData

object ATMRepository {

    //we will get All Transactions from this method
    fun getAllTransactions(context: Context): LiveData<List<ATMData>>? {
        return AppDatabase.getDataBase(context).atmDao()
            ?.loadAllTransactions()
    }

    //we will implement insert Amount in ATM logic here
    fun insertAmountInATM(context: Context, transactions: ATMData) {
        AppDatabase.getDataBase(context).atmDao()
            ?.insert(transactions)
    }

    //we will implement withdrawAmount logic here
    fun getAvailableAmount(context: Context): LiveData<ATMData>? {
        return AppDatabase.getDataBase(context).atmDao()
            ?.getAvailableAmountLive()
    }

    //we will implement withdrawAmount logic here
    fun withdrawAmount(context: Context, transactions: ATMData): Boolean {
        val availableAmount = AppDatabase.getDataBase(context).atmDao()
            ?.getAvailableAmount()
        return if (transactions.amount!! <= availableAmount?.amount!!) {
            var netAmount = transactions.amount!!
            var twoThousandNotes = 0
            var fiveHundredNotes = 0
            var twoHundredNotes = 0
            var oneHundredNotes = 0

            val maxTwoThousandNotes: Int = netAmount / 2000
            when {
                netAmount != 0 && maxTwoThousandNotes <= availableAmount.twoThousand!! -> {
                    netAmount -= maxTwoThousandNotes * 2000
                    twoThousandNotes = maxTwoThousandNotes
                }
                netAmount != 0 && availableAmount.twoThousand!! != 0 -> {
                    netAmount -= availableAmount.twoThousand!! * 2000
                    twoThousandNotes = availableAmount.twoThousand!!
                }
            }

            val maxFiveHundredNotes: Int = netAmount / 500
            when {
                netAmount != 0 && maxFiveHundredNotes <= availableAmount.fiveHundred!! -> {
                    netAmount -= maxFiveHundredNotes * 500
                    fiveHundredNotes = maxFiveHundredNotes
                }
                netAmount != 0 && availableAmount.fiveHundred!! != 0 -> {
                    netAmount -= availableAmount.fiveHundred!! * 500
                    fiveHundredNotes = availableAmount.fiveHundred!!
                }
            }

            val maxTwoHundredNotes: Int = netAmount / 200
            when {
                netAmount != 0 && maxTwoHundredNotes <= availableAmount.twoHundred!! -> {
                    netAmount -= maxTwoHundredNotes * 200
                    twoHundredNotes = maxTwoHundredNotes
                }
                netAmount != 0 && availableAmount.twoHundred!! != 0 -> {
                    netAmount -= availableAmount.twoHundred!! * 200
                    twoHundredNotes = availableAmount.twoHundred!!
                }
            }

            val maxOneHundredNotes: Int = netAmount / 100
            when {
                netAmount != 0 && maxOneHundredNotes <= availableAmount.oneHundred!! -> {
                    netAmount -= maxOneHundredNotes * 100
                    oneHundredNotes = maxOneHundredNotes
                }
                netAmount != 0 && availableAmount.oneHundred!! != 0 -> {
                    netAmount -= availableAmount.oneHundred!! * 100
                    oneHundredNotes = availableAmount.oneHundred!!
                }
            }

            transactions.oneHundred = oneHundredNotes
            transactions.twoHundred = twoHundredNotes
            transactions.fiveHundred = fiveHundredNotes
            transactions.twoThousand = twoThousandNotes
            transactions.type = 1

            //perform transaction
            AppDatabase.getDataBase(context).atmDao()
                ?.insert(transactions)

            //Update availableAmount
            AppDatabase.getDataBase(context).atmDao()
                ?.updateAvailableAmount(
                    amount = availableAmount.amount!! - transactions.amount!!,
                    oneHundred = availableAmount.oneHundred!! - transactions.oneHundred!!,
                    twoHundred = availableAmount.twoHundred!! - transactions.twoHundred!!,
                    fiveHundred = availableAmount.fiveHundred!! - transactions.fiveHundred!!,
                    twoThousand = availableAmount.twoThousand!! - transactions.twoThousand!!
                )
            true
        } else {
            false
        }
    }
}