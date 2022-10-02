package com.emilygoose.assignment1.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CustomerViewModel : ViewModel() {

    // Initialize MutableLiveData fields for the ViewModel
    // All String fields default to empty

    // Name
    private val mutableName = MutableLiveData<String>()
    val name: String get() = mutableName.value ?: ""

    // Phone number
    private val mutablePhoneNumber = MutableLiveData<String>()
    val phoneNumber: String get() = mutablePhoneNumber.value ?: ""

    // Address
    private val mutableAddress = MutableLiveData<String>()
    val address: String get() = mutableAddress.value ?: ""

    // Sets name
    fun setName(name: String) {
        mutableName.value = name
    }

    // Sets phone number
    fun setPhone(phone: String) {
        mutablePhoneNumber.value = phone
    }

    // Sets address
    fun setAddress(address: String) {
        mutableAddress.value = address
    }
}