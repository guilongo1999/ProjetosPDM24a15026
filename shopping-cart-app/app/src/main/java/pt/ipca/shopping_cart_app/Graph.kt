package pt.ipca.shopping_cart_app

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ipca.shopping_cart_app.data.room.models.ShoppingListDatabase
import pt.ipca.shopping_cart_app.ui.repository.Repository

object Graph {




}




//lateinit var db:ShoppingListDatabase
//    private set

// val repository by lazy {

//   Repository(

//     listDao = db.listDao(),
//    storeDao = storeDao,
//    itemDao = itemDao,
//    savedlistsDao = savedlistsDao

//  )
// }

/*

fun provide(context: Context) {
    // Initialize the database on a background thread
    CoroutineScope(Dispatchers.IO).launch {
        db = ShoppingListDatabase.getDatabase(context)
        Log.d("Database", "Database instance initialized: $db")
    }
}
 */