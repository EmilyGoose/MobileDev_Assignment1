package com.emilygoose.assignment1.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// ViewModel to handle pizza ingredients
class PizzaViewModel() : ViewModel() {

    private val mutableToppingArray = MutableLiveData<MutableList<String>>()
    val selectedToppings: LiveData<MutableList<String>> get() = mutableToppingArray

    init {
        // Initialize topping MutableList
        mutableToppingArray.value = mutableListOf()
    }

    // Adds specified topping to MutableList
    fun addTopping(topping: String) {
        mutableToppingArray.value?.add(topping)
    }

    fun removeTopping(topping: String) {
        // Check that the value exists - We use == true for null safety
        if(mutableToppingArray.value?.contains(topping) == true) {
            // Remove the specified topping
            mutableToppingArray.value?.remove(topping)
        }
    }
}