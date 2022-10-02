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
        ) { _, bundle ->
            // Get the price - We use the FragmentResultListener since this updates in real-time
            val priceResult = bundle.getString("price")
            priceLabel.text = priceResult
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

                checkoutButton.text = getString(R.string.prompt_checkout)
            } else {
                // Move to next activity
                val intent = Intent(this, OrderSummaryActivity::class.java)
                intent.putExtra("price", price)
                startActivity(intent)
            }
        }

    }
}