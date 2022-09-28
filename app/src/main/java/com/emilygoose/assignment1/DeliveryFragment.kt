package com.emilygoose.assignment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult

class DeliveryFragment : Fragment() {

    // Declare view variables
    private lateinit var nameField: EditText
    private lateinit var phoneField: EditText
    private lateinit var addressField: EditText

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
            // Sending the result every time the field changes is a bit jank but whatever
            setFragmentResult("DeliveryFragment", bundleOf("name" to it.toString()))
        }
        phoneField.addTextChangedListener {
            // Sending the result every time the field changes is a bit jank but whatever
            setFragmentResult("DeliveryFragment", bundleOf("phone" to it.toString()))
        }
        addressField.addTextChangedListener {
            // Sending the result every time the field changes is a bit jank but whatever
            setFragmentResult("DeliveryFragment", bundleOf("address" to it.toString()))
        }
    }

    companion object {
        fun newInstance() = DeliveryFragment()
    }
}