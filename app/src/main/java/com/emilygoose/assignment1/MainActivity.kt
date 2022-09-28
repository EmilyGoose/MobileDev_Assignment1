package com.emilygoose.assignment1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace

class MainActivity : AppCompatActivity() {

    // Declare view variables
    private lateinit var priceLabel: TextView
    private lateinit var checkoutButton: Button

    // Activity-level variables
    private var price = ""
    private var specialInstructions = ""
    private var name = ""
    private var phoneNumber = ""
    private var address = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // Initialize view variables
        priceLabel = findViewById(R.id.form_total)
        checkoutButton = findViewById(R.id.button_checkout)

        // Tracks what stage of the form we're in
        var pizzaStage = true

        // Create a fragment result listener for the price
        supportFragmentManager.setFragmentResultListener(
            "PizzaFragment", this
        ) { _, bundle -> // We use a String here, but any type that can be put in a Bundle is supported
            val priceResult = bundle.getString("price")
            val instructionResult = bundle.getString("instructions")
            if (priceResult != null) {
                price = priceResult
                priceLabel.text = priceResult
            }
            if (instructionResult != null) {
                specialInstructions = instructionResult
            }
        }

        // Create the fragment for the first page of the order form
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<PizzaFragment>(R.id.fragment_container_view)
        }
        checkoutButton.text = getString(R.string.prompt_continue)

        // Create click listener on continue button
        checkoutButton.setOnClickListener {
            if (pizzaStage) {
                pizzaStage = false

                // Swap out PizzaFragment for DeliveryFragment
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<DeliveryFragment>(R.id.fragment_container_view)
                }

                // Create a fragment result listener for the price
                supportFragmentManager.setFragmentResultListener(
                    "DeliveryFragment", this
                ) { _, bundle -> // We use a String here, but any type that can be put in a Bundle is supported
                    // There's probably a better way to do this I'll look into it later
                    val nameResult = bundle.getString("name")
                    val phoneResult = bundle.getString("phone")
                    val addressResult = bundle.getString("address")
                    if (nameResult != null) {
                        name = nameResult
                    }
                    if (phoneResult != null) {
                        phoneNumber = phoneResult
                    }
                    if (addressResult != null) {
                        address = addressResult
                    }
                }
                checkoutButton.text = getString(R.string.prompt_checkout)
            } else {
                // Move to next activity
                val intent = Intent(this, OrderSummaryActivity::class.java)
                intent.putExtra("price", price)
                intent.putExtra("name", price)
                intent.putExtra("phone", phoneNumber)
                intent.putExtra("address", address)
                startActivity(intent)
            }
        }

    }
}