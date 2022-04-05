package com.practicetask.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.practicetask.data.model.ATMData

@Dao
interface ATMDao {

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    fun insert(atmData: ATMData)

    @Query("SELECT * from atm_data WHERE type =1 order by id DESC")
    fun loadAllTransactions(): LiveData<List<ATMData>>

    @Query("SELECT * FROM atm_data WHERE type =0")
    fun getAvailableAmountLive(): LiveData<ATMData>

    @Query("SELECT * FROM atm_data WHERE type =0")
    fun getAvailableAmount(): ATMData?

    @Query("UPDATE atm_data SET amount =:amount,fiveHundred =:fiveHundred,oneHundred =:oneHundred,twoHundred =:twoHundred,twoThousand =:twoThousand where type=0")
    fun updateAvailableAmount(
        amount: Int,
        oneHundred: Int,
        twoHundred: Int,
        fiveHundred: Int,
        twoThousand: Int
    )
}
