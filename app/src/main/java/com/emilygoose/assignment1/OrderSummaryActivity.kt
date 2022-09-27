package com.emilygoose.assignment1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.text.NumberFormat
import kotlin.properties.Delegates

class OrderSummaryActivity : AppCompatActivity() {

    // Declare view variables
    private lateinit var priceDisplay: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)

        // Get the intent extras
        val price = this.intent.getIntExtra("price", 0)

        // Initialize view variables
        priceDisplay = findViewById(R.id.display_price)

        priceDisplay.text = NumberFormat.getCurrencyInstance().format(price)

    }
}