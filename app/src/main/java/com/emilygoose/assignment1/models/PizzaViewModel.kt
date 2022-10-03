package com.emilygoose.assignment1.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emilygoose.assignment1.enum.PizzaSize
import kotlinx.coroutines.launch

// ViewModel to handle pizza ingredients
class PizzaViewModel : ViewModel() {

    // Initialize MutableLiveData fields for the ViewModel
    // Array of selected toppings with default empty
    private val mutableToppingArray = MutableLiveData<MutableList<String>>()
    val selectedToppings: MutableList<String> get() = mutableToppingArray.value ?: mutableListOf()

    // Currently selected size with default small
    private val mutableSize = MutableLiveData<PizzaSize>()
    val selectedSize: PizzaSize get() = mutableSize.value ?: PizzaSize.SMALL

    // Special instructions from EditText field with default empty
    private val mutableInstructions = MutableLiveData<String>()
    val instructions: String get() = mutableInstructions.value ?: ""

    // Cheese checkbox with default false
    private val mutableCheese = MutableLiveData<Boolean>()
    val hasCheese: Boolean get() = mutableCheese.value ?: false

    // Delivery checkbox with default false
    private val mutableDelivery = MutableLiveData<Boolean>()
    val hasDelivery: Boolean get() = mutableDelivery.value ?: false

    // Price - Calculated in this ViewModel using updatePrice()
    private val mutablePrice = MutableLiveData<Int>()
    // Make this return LiveData since MainActivity observes it
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

    // Sets special instructions
    fun setInstructions(newText: String) {
        mutableInstructions.value = newText
    }

    // Sets whether cheese exists
    fun setCheese(cheese: Boolean) {
        mutableCheese.value = cheese
        updatePrice()
    }

    // Sets whether customer wants delivery
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
        if (selectedToppings.contains(topping)) {
            // Remove the specified topping
            mutableToppingArray.value?.remove(topping)
        }
        updatePrice()
    }

    private fun updatePrice() {
        // Launch as coroutine so it dies when ViewModel is cleared
        viewModelScope.launch {
            var totalPrice = 0
            // Add the size price
            totalPrice += selectedSize.price
            // Add topping price (Length of topping array, since each is $1)
            totalPrice += selectedToppings.size
            // Check for cheese and delivery (Use ==true for null safety)
            totalPrice += if (hasCheese) 2 else 0
            totalPrice += if (hasDelivery) 10 else 0

            // Update MutableLiveData
            mutablePrice.value = totalPrice
        }
    }
}