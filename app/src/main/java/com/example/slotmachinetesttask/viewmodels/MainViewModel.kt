package com.example.slotmachinetesttask.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _balance = MutableLiveData(500)
    val balance: LiveData<Int>
        get() = _balance

    private var _rate = MutableLiveData(50)
    val rate: LiveData<Int>
        get() = _rate

    fun plusBalance(value: Int) {
        _balance.value = _balance.value?.plus(value)
    }

    fun minusBalance(value: Int) {
        _balance.value = _balance.value?.minus(value)
    }

    fun plusRate(value: Int) {
        _rate.value = _rate.value?.plus(value)
    }

    fun minusRate(value: Int) {
        _rate.value = _rate.value?.minus(value)
    }
}