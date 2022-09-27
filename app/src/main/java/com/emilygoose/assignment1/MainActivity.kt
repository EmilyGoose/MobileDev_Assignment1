package com.emilygoose.assignment1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.text.NumberFormat

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    // Declare view variables
    private lateinit var sizeSpinner: Spinner
    private lateinit var ingredientChips: ChipGroup
    private lateinit var checkoutButton: Button
    private lateinit var cheeseBox: CheckBox
    private lateinit var deliveryBox: CheckBox
    private lateinit var priceLabel: TextView

    // Declare imported resources
    private lateinit var toppingArray: Array<String>
    private lateinit var sizePriceArray: IntArray

    // Activity-level variables
    private var toppingsSelected = 0
    private var sizePrice = 0
    private var totalPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize view variables
        sizeSpinner = findViewById(R.id.spinner_size)
        ingredientChips = findViewById(R.id.chip_group_toppings)
        checkoutButton = findViewById(R.id.button_checkout)
        cheeseBox = findViewById(R.id.checkbox_cheese)
        deliveryBox = findViewById(R.id.checkbox_delivery)
        priceLabel = findViewById(R.id.form_total)

        // Import resources
        toppingArray = resources.getStringArray(R.array.array_toppings)
        sizePriceArray = resources.getIntArray(R.array.array_size_prices)

        // Set select listener
        sizeSpinner.onItemSelectedListener = this

        // Add listeners for checkboxes to update price
        cheeseBox.setOnClickListener {
            updatePrice()
        }
        deliveryBox.setOnClickListener {
            updatePrice()
        }

        // Create an array adapter for the spinner
        ArrayAdapter.createFromResource(
            this, R.array.array_sizes, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sizeSpinner.adapter = adapter
        }

        // Create click listener for checkout button
        checkoutButton.setOnClickListener {
            updatePrice()
            val intent = Intent(
                this, OrderSummaryActivity::class.java
            ).apply { // Add all the extras for the intent
                putExtra("price", totalPrice)
            }

            startActivity(intent)
        }

        // Initialize topping chips and add listeners
        for (topping in toppingArray) { // Create a new chip
            val newChip = Chip(this)
            newChip.text = topping
            newChip.isCheckable = true

            // Add click listener to the new chip
            newChip.setOnClickListener {
                if ((it as Chip).isChecked) {
                    toppingsSelected++
                } else {
                    toppingsSelected--
                }

                // Update price now that toppings have changed
                updatePrice()
            }

            // Parent the new chip to the ChipGroup
            ingredientChips.addView(newChip)
        }
    }

    override fun onResume() {
        super.onResume() // Update price display on start
        updatePrice()
    }

    // Function to update the price displayed to the user
    private fun updatePrice() { // Reset total price for calculation
        totalPrice = 0 // Add the size price
        totalPrice += sizePrice // Add the topping price
        totalPrice += toppingsSelected * 1 // Add cheese price if checked
        if (cheeseBox.isChecked) {
            totalPrice += 2
        } // Add delivery price if checked
        if (deliveryBox.isChecked) {
            totalPrice += 10
        }

        // Update view to display price
        val priceString = NumberFormat.getCurrencyInstance().format(totalPrice)
        priceLabel.text = priceString
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // Grab price corresponding to selected size
        sizePrice = sizePriceArray[pos]
        updatePrice()
    }

    override fun onNothingSelected(parent: AdapterView<*>) { // Default size price for small so nothing breaks
        sizePrice = sizePriceArray[0]
        updatePrice()
    }
}