package com.emilygoose.assignment1.enum

import com.emilygoose.assignment1.R

enum class PizzaSize (val price: Int, val displayStringResourceId: Int) {
    SMALL(6, R.string.pizza_size_small),
    MEDIUM(9, R.string.pizza_size_medium),
    LARGE(12, R.string.pizza_size_large),
    XLARGE(15, R.string.pizza_size_xlarge)
}