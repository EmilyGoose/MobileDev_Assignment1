package com.emilygoose.assignment1

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {

    // Declare view variables
    private lateinit var priceLabel: TextView
    private lateinit var checkoutButton: Button

    // Activity-level variables
    private var price = ""

    // Declare required fragments
    private lateinit var pizzaFragment: PizzaFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // Initialize view variables
        priceLabel = findViewById(R.id.form_total)
        checkoutButton = findViewById(R.id.button_checkout)

        // Create a fragment result listener for the price
        supportFragmentManager.setFragmentResultListener(
            "PizzaFragment",
            this
        ) { _, bundle -> // We use a String here, but any type that can be put in a Bundle is supported
            val result = bundle.getString("price")
            if (result != null) {
                price = result
                priceLabel.text = result
            }
        }

        // Create the fragment for the first page of the order form
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<PizzaFragment>(R.id.fragment_container_view)
        }
        checkoutButton.text = getString(R.string.prompt_continue)

        // Create click listener on continue button
        checkoutButton.setOnClickListener {

        }

    }
}