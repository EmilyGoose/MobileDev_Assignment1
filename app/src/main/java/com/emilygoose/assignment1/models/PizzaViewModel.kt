package com.emilygoose.assignment1.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emilygoose.assignment1.enum.PizzaSize

// ViewModel to handle pizza ingredients
class PizzaViewModel : ViewModel() {

    // Initialize MutableLiveData fields for the ViewModel
    private val mutableToppingArray = MutableLiveData<MutableList<String>>()
    val selectedToppings: LiveData<MutableList<String>> get() = mutableToppingArray

    private val mutableSize = MutableLiveData<PizzaSize>()
    val selectedSize: LiveData<PizzaSize> get() = mutableSize

    private val mutableInstructions = MutableLiveData<String>()
    val instructions: LiveData<String> get() = mutableInstructions

    private val mutableCheese = MutableLiveData<Boolean>()
    val hasCheese: LiveData<Boolean> get() = mutableCheese

    private val mutableDelivery = MutableLiveData<Boolean>()
    val hasDelivery: LiveData<Boolean> get() = mutableDelivery

    private val mutablePrice = MutableLiveData<Int>()
    val price: LiveData<Int> get() = mutablePrice

    init {
        // Initialize topping MutableList
        mutableToppingArray.value = mutableListOf()
        updatePrice()
    }

    // Sets size for the pizza
    fun setSize(size: PizzaSize) {
        mutableSize.value = size
        updatePrice()
    }

    // Set special instructions
    fun setInstructions(newText: String) {
        mutableInstructions.value = newText
    }

    // Set whether cheese exists
    fun setCheese(cheese: Boolean) {
        mutableCheese.value = cheese
        updatePrice()
    }

    // Set whether customer wants delivery
    fun setDelivery(delivery: Boolean) {
        mutableDelivery.value = delivery
        updatePrice()
    }

    // Adds specified topping to MutableList
    fun addTopping(topping: String) {
        mutableToppingArray.value?.add(topping)
        updatePrice()
    }

    // Removes topping from MutableList
    fun removeTopping(topping: String) {
        // Check that the value exists - We use == true for null safety
        if (selectedToppings.value?.contains(topping) == true) {
            // Remove the specified topping
            mutableToppingArray.value?.remove(topping)
        }
        updatePrice()
    }

    private fun updatePrice() {
        var totalPrice = 0
        // Add the size price
        totalPrice += selectedSize.value?.price ?: 0
        // Add topping price (Length of topping array, since each is $1)
        totalPrice += selectedToppings.value?.size ?: 0
        // Check for cheese and delivery (Use ==true for null safety)
        totalPrice += if (hasCheese.value == true) 2 else 0
        totalPrice += if (hasDelivery.value == true) 10 else 0

        // Update MutableLiveData
        mutablePrice.value = totalPrice
    }
}