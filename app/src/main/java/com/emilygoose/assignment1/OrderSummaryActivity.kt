package com.emilygoose.assignment1

import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderSummaryActivity : AppCompatActivity() {

    // Declare view variables
    private lateinit var pageTitle: TextView
    private lateinit var sizeLabel: TextView
    private lateinit var toppingsListView: ListView
    private lateinit var instructionsLabel: TextView
    private lateinit var priceLabel: TextView
    private lateinit var deliveryLabel: TextView
    private lateinit var addressLabel: TextView
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)
        supportActionBar?.hide()

        // Get bundle extras from the intent
        // Assert not null since we require them to display the info
        val orderInfo = intent.getBundleExtra("orderInfo")!!
        val customerInfo = intent.getBundleExtra("customerInfo")!!

        // Instantiate view variables
        sizeLabel = findViewById(R.id.label_size)
        toppingsListView = findViewById(R.id.list_toppings)
        instructionsLabel = findViewById(R.id.label_instructions)
        priceLabel = findViewById(R.id.label_price)
        pageTitle = findViewById(R.id.form_title)
        deliveryLabel = findViewById(R.id.title_delivery)
        addressLabel = findViewById(R.id.label_address)
        backButton = findViewById(R.id.button_back)

        // If user entered a name, populate the page title
        val name = customerInfo.getString("name")
        if (name?.isNotBlank() == true) {
            pageTitle.text = getString(R.string.title_summary_personal, name)
        }

        // Populate the price label
        val price = orderInfo.getInt("price")
        priceLabel.text = NumberFormat.getCurrencyInstance().format(price)

        // Populate the size label
        val selectedSize = orderInfo.getInt("size")
        sizeLabel.text = getString(R.string.label_size, getString(selectedSize))

        // Check whether user has cheese
        val hasCheese = orderInfo.getBoolean("cheese")

        // Get toppings from the bundle
        var toppingArray = orderInfo.getStringArray("toppings")!!
        if (hasCheese) {
            // Add "Extra Cheese" to topping array so I don't need an extra TextView
            toppingArray = arrayOf(*toppingArray, "Extra Cheese")
        }
        // Create default value to display if toppings are empty
        if (toppingArray.isEmpty()) {
            toppingArray = arrayOf("None")
        }
        // Create ArrayAdapter to populate the ListView with toppings
        ArrayAdapter(
            this,
            android.R.layout.test_list_item,
            toppingArray as Array<out String>
        ).also { adapter ->
            toppingsListView.adapter = adapter
        }

        // Populate the special instructions label
        instructionsLabel.text = orderInfo.getString("instructions")

        // Check if customer selected delivery
        val hasDelivery = orderInfo.getBoolean("delivery")
        if (hasDelivery) {
            deliveryLabel.text = getString(R.string.label_delivery)
            addressLabel.text = customerInfo.getString("address")
        }

        // Add click listener to back button
        backButton.setOnClickListener {
            // Send user back to MainActivity
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}