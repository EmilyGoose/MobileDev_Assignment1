package com.emilygoose.assignment1

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrderSummaryActivity : AppCompatActivity() {

    // Declare view variables
    private lateinit var sizeLabel: TextView
    private lateinit var toppingsListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)
        supportActionBar?.hide()

        // Get bundle extras from the intent
        val orderInfo = intent.getBundleExtra("orderInfo")
        val customerInfo = intent.getBundleExtra("customerInfo")

        // Instantiate view variables
        sizeLabel = findViewById(R.id.label_size)
        toppingsListView = findViewById(R.id.list_toppings)

        // Populate the size label
        val selectedSize = orderInfo!!.getInt("size")
        sizeLabel.text = getString(R.string.label_size, getString(selectedSize))

        // Get toppings from the bundle
        val toppingArray = orderInfo.getStringArray("toppings")
        // Create ArrayAdapter to populate the ListView with toppings
        ArrayAdapter(
            this,
            android.R.layout.test_list_item,
            toppingArray as Array<out String>
        ).also { adapter ->
            toppingsListView.adapter = adapter
        }
    }
}