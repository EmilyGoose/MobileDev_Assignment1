package com.emilygoose.assignment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.emilygoose.assignment1.models.CustomerViewModel

class DeliveryFragment : Fragment() {

    // Declare view variables
    private lateinit var nameField: EditText
    private lateinit var phoneField: EditText
    private lateinit var addressField: EditText

    // Initialize ViewModel to share data with MainActivity
    private val customerViewModel: CustomerViewModel by activityViewModels()

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

        // Add listeners to each of the fields - Need to see if there's a better way
        nameField.addTextChangedListener {
            // Update the ViewModel
            customerViewModel.setName(it.toString())
        }
        phoneField.addTextChangedListener {
            // Update the ViewModel
            customerViewModel.setPhone(it.toString())
        }
        addressField.addTextChangedListener {
            // Update the ViewModel
            customerViewModel.setAddress(it.toString())
        }
    }
}