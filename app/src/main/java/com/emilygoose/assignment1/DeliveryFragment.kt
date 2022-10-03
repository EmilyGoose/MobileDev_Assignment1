package com.emilygoose.assignment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.emilygoose.assignment1.models.CustomerViewModel
import com.emilygoose.assignment1.models.PizzaViewModel

class DeliveryFragment : Fragment() {

    // Declare view variables
    private lateinit var nameField: EditText
    private lateinit var phoneField: EditText
    private lateinit var addressField: EditText
    private lateinit var addressLayout: LinearLayout

    // Initialize ViewModels to share data with MainActivity
    private val customerViewModel: CustomerViewModel by activityViewModels()
    private val pizzaViewModel: PizzaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delivery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize view variables
        nameField = view.findViewById(R.id.field_name)
        phoneField = view.findViewById(R.id.field_phone)
        addressField = view.findViewById(R.id.field_address)
        addressLayout = view.findViewById(R.id.form_address)

        // Add listeners to each of the fields
        nameField.addTextChangedListener {
            // Update the ViewModel
            customerViewModel.setName(it.toString())
        }
        phoneField.addTextChangedListener {
            // Update the ViewModel
            customerViewModel.setPhone(it.toString())
        }

        // Show/hide address section based on whether delivery is selected
        if (pizzaViewModel.hasDelivery) {
            // Add listener to addressField only if user has delivery
            addressField.addTextChangedListener {
                // Update the ViewModel
                customerViewModel.setAddress(it.toString())
            }
        } else {
            addressLayout.isVisible = false
        }
    }
}