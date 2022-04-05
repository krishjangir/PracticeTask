package com.practicetask.callbacks


interface WithdrawListener {
    fun onStarted()
    fun onSuccess(isSuccess: Boolean)
    fun onFailure(message: String?)
}