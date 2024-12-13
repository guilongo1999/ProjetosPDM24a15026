package pt.ipca.shopping_cart_app

import android.content.Context
import pt.ipca.shopping_cart_app.data.room.models.ShoppingListDatabase
import pt.ipca.shopping_cart_app.ui.repository.Repository

object Graph {

    lateinit var db:ShoppingListDatabase
        private set

    val repository by lazy {

        Repository(

            listDao = db.listDao(),
            storeDao = db.storeDao(),
            itemDao = db.itemDao()
        )
    }

    fun provide(context: Context) {

        db = ShoppingListDatabase.getDatabase(context)
    }
}