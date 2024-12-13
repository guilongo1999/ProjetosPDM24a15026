package pt.ipca.shopping_cart_app.ui

import androidx.annotation.DrawableRes
import pt.ipca.shopping_cart_app.R

object Utils {
    val category = listOf(
        Category(title = "Drinks", resId = R.drawable.drink, id = 0),
        Category(title = "Vegetable", resId = R.drawable.vegetable, id = 1),
        Category(title = "Fruits", resId = R.drawable.fruit, id = 2),
        Category(title = "Cleaning", resId = R.drawable.cleaning, id = 3),
        Category(title = "Electronic", resId = R.drawable.technology, id = 4),
        Category(title = "None", resId =R.drawable.no_category ,id = 10001)
    )
}

data class Category(
    @DrawableRes val resId: Int = -1,
    val title: String = "",
    val id: Int = -1,
)

