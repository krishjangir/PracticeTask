package com.practicetask.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "atm_data")
data class ATMData(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var type: Int? = null,// 0 for available amount , 1 for transaction
    var amount: Int? = 0,
    var oneHundred: Int? = 0,
    var twoHundred: Int? = 0,
    var fiveHundred: Int? = 0,
    var twoThousand: Int? = 0
)
