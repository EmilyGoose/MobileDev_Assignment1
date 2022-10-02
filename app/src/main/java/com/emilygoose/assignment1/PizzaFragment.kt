package com.emilygoose.assignment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.emilygoose.assignment1.enum.PizzaSize
import com.emilygoose.assignment1.models.PizzaViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class PizzaFragment : Fragment(), AdapterView.OnItemSelectedListener {

    // Declare view variables
    private lateinit var sizeSpinner: Spinner
    private lateinit var ingredientChips: ChipGroup
    private lateinit var cheeseBox: CheckBox
    private lateinit var deliveryBox: CheckBox
    private lateinit var instructionField: EditText

    // Initialize ViewModel to share data with MainActivity
    private val pizzaViewModel: PizzaViewModel by activityViewModels()

    // Declare imported resources
    private lateinit var toppingArray: Array<String>

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

        // Add listeners for checkboxes to update ViewModel
        cheeseBox.setOnClickListener {
            pizzaViewModel.setCheese(cheeseBox.isChecked)
        }
        deliveryBox.setOnClickListener {
            pizzaViewModel.setDelivery(deliveryBox.isChecked)
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
                    // Update the ViewModel
                    pizzaViewModel.addTopping(topping)
                } else {
                    // Update the ViewModel
                    pizzaViewModel.removeTopping(topping)
                }
            }

            // Parent the new chip to the ChipGroup
            ingredientChips.addView(newChip)
        }

        // Set listener for special instructions field
        instructionField.addTextChangedListener {
            // Update the ViewModel
            pizzaViewModel.setInstructions(it.toString())
        }
    }

    override fun onItemSelected(
        parent: AdapterView<*>, view: View, pos: Int, id: Long
    ) {
        // Get enum value for selected size and update ViewModel
        val selectedSize = PizzaSize.values()[pos]
        pizzaViewModel.setSize(selectedSize)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Update the ViewModel with the default size, small
        pizzaViewModel.setSize(PizzaSize.SMALL)
    }

}