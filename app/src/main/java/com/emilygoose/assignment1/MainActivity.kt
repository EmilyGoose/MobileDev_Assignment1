package com.emilygoose.assignment1

import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.emilygoose.assignment1.models.CustomerViewModel
import com.emilygoose.assignment1.models.PizzaViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    // Declare view variables
    private lateinit var priceLabel: TextView
    private lateinit var checkoutButton: Button

    // Initialize ViewModels to share data with fragments
    private val pizzaViewModel: PizzaViewModel by viewModels()
    private val customerViewModel: CustomerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // Initialize view variables
        priceLabel = findViewById(R.id.form_total)
        checkoutButton = findViewById(R.id.button_checkout)

        // Tracks what stage of the form we're in
        var pizzaStage = true

        // Observe the ViewModel price for changes
        pizzaViewModel.price.observe(this) { price ->
            // Update view to display price
            priceLabel.text = NumberFormat.getCurrencyInstance().format(price)
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

                // Set checkout button prompt to next stage
                checkoutButton.text = getString(R.string.prompt_checkout)
            } else {
                // Create bundle of order info
                val orderInfo = bundleOf(
                    "price" to pizzaViewModel.price.value,
                    // Bundle the size string resource because I don't wanna implement Parcelable
                    "size" to pizzaViewModel.selectedSize.displayStringResourceId,
                    // Convert MutableList<String> to Array<String> before bundling
                    "toppings" to Collections.unmodifiableList(pizzaViewModel.selectedToppings)
                        .toTypedArray(),
                    "cheese" to pizzaViewModel.hasCheese,
                    "delivery" to pizzaViewModel.hasDelivery,
                    "instructions" to pizzaViewModel.instructions
                )
                // Create bundle of customer info
                val customerInfo = bundleOf(
                    "name" to customerViewModel.name,
                    "phone" to customerViewModel.phoneNumber,
                    "address" to customerViewModel.address
                )
                // Move to next activity
                val intent = Intent(this, OrderSummaryActivity::class.java)
                // Add bundles to intent and start activity
                intent.putExtra("orderInfo", orderInfo)
                intent.putExtra("customerInfo", customerInfo)
                startActivity(intent)
            }
        }
    }
}