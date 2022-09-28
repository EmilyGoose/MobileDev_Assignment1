package com.emilygoose.assignment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.text.NumberFormat

class PizzaFragment : Fragment(), AdapterView.OnItemSelectedListener {

    // Declare view variables
    private lateinit var sizeSpinner: Spinner
    private lateinit var ingredientChips: ChipGroup
    private lateinit var cheeseBox: CheckBox
    private lateinit var deliveryBox: CheckBox

    // Declare imported resources
    private lateinit var toppingArray: Array<String>
    private lateinit var sizePriceArray: IntArray

    // Fragment-level variables
    private var toppingsSelected = 0
    private var sizePrice = 0
    private var totalPrice = 0

    companion object {
        fun newInstance() = PizzaFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pizza, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize view variables
        sizeSpinner = view.findViewById(R.id.spinner_size)
        ingredientChips = view.findViewById(R.id.chip_group_toppings)
        cheeseBox = view.findViewById(R.id.checkbox_cheese)
        deliveryBox = view.findViewById(R.id.checkbox_delivery)

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
    ) { // Grab price corresponding to selected size
        sizePrice = sizePriceArray[pos]
        updatePrice()
    }

    override fun onNothingSelected(parent: AdapterView<*>) { // Default size price for small so nothing breaks
        sizePrice = sizePriceArray[0]
        updatePrice()
    }

}