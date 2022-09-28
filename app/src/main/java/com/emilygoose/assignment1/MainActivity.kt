package com.emilygoose.assignment1

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

                // Create a fragment result listener for the delivery
                supportFragmentManager.setFragmentResultListener(
                    "PizzaFragment", this
                ) { _, bundle -> // We use a String here, but any type that can be put in a Bundle is supported
                    val result = bundle.getString("price")
                    if (result != null) {
                        price = result
                        priceLabel.text = result
                    }
                }

                // Swap out PizzaFragment for DeliveryFragment
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<DeliveryFragment>(R.id.fragment_container_view)
                }
                checkoutButton.text = getString(R.string.prompt_checkout)
            } else { // Move to next activity
                TODO("Needs to open OrderSummaryActivity")
            }
        }

    }
}