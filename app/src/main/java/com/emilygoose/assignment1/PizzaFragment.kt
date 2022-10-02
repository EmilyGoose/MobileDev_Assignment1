package com.emilygoose.assignment1

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.emilygoose.assignment1.enum.PizzaSize
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.text.NumberFormat

class PizzaFragment : Fragment(), AdapterView.OnItemSelectedListener {

    // Declare view variables
    private lateinit var sizeSpinner: Spinner
    private lateinit var ingredientChips: ChipGroup
    private lateinit var cheeseBox: CheckBox
    private lateinit var deliveryBox: CheckBox
    private lateinit var instructionField: EditText

    // Declare imported resources
    private lateinit var toppingArray: Array<String>

    // Fragment-level variables
    private var toppingsSelected = 0
    private var sizePrice = 0
    private var totalPrice = 0
    private val toppingSelectedArray = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pizza, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize view variables
        sizeSpinner = view.findViewById(R.id.spinner_size)
        ingredientChips = view.findViewById(R.id.chip_group_toppings)
        cheeseBox = view.findViewById(R.id.checkbox_cheese)
        deliveryBox = view.findViewById(R.id.checkbox_delivery)
        instructionField = view.findViewById(R.id.field_instructions)

        // Import resources
        toppingArray = resources.getStringArray(R.array.array_toppings)

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
            requireContext(), R.array.array_sizes, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sizeSpinner.adapter = adapter
        }

        // Initialize topping chips and add listeners
        for (topping in toppingArray) { // Create a new chip
            val newChip = Chip(requireContext())
            newChip.text = topping
            newChip.isCheckable = true

            // Add click listener to the new chip
            newChip.setOnClickListener {
                if ((it as Chip).isChecked) {
                    toppingsSelected++
                    toppingSelectedArray.add(topping)
                } else {
                    toppingsSelected--
                    toppingSelectedArray.remove(topping)
                }

                // Update price now that toppings have changed
                updatePrice()

                TODO("Needs to update ViewModel")
            }

            // Parent the new chip to the ChipGroup
            ingredientChips.addView(newChip)
        }

        // Set listener for special instructions field
        instructionField.addTextChangedListener { // Sending the result every time the field changes is a bit jank but whatever
            TODO("Needs to update ViewModel")
        }
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
        setFragmentResult("PizzaFragment", bundleOf("price" to priceString))
    }

    override fun onItemSelected(
        parent: AdapterView<*>, view: View, pos: Int, id: Long
    ) {
        // Grab price corresponding to selected size
        val sizeString = parent.getItemAtPosition(pos).toString()
        // This loop has to look up resources every time might need to eval for performance
        for (pizzaSize in PizzaSize.values()) {
            Log.println(Log.DEBUG, "PizzaFragment", "Size String: $sizeString")
            Log.println(Log.DEBUG, "PizzaFragment", "Checking: " + resources.getString(pizzaSize.displayStringResourceId))
            // Find the pizza size corresponding to the price
            if (resources.getString(pizzaSize.displayStringResourceId) == sizeString) {
                sizePrice = pizzaSize.price
                break
            }
        }
        updatePrice()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Default to small pizza
        sizePrice = PizzaSize.SMALL.price
        updatePrice()
    }

}