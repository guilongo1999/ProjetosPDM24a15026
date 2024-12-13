package pt.ipca.shopping_cart_app.data.room.models

import android.content.Context
import androidx.room.Database;
import androidx.room.Room
import pt.ipca.shopping_cart_app.data.room.models.Item;
import pt.ipca.shopping_cart_app.data.room.models.ShoppingList;
import pt.ipca.shopping_cart_app.data.room.models.Store;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters
import pt.ipca.shopping_cart_app.data.room.converter.DateConverter


@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [ShoppingList::class, Store::class, Item::class],
    version = 1,
    exportSchema = false



)

abstract class ShoppingListDatabase:RoomDatabase() {


    abstract fun listDao(): ListDao
    abstract fun itemDao(): ItemDao
    abstract fun storeDao(): StoreDao


    companion object {

        @Volatile
        var INSTANCE: ShoppingListDatabase? = null
        fun getDatabase(context: Context): ShoppingListDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(

                    context,
                    ShoppingListDatabase::class.java, "shopping_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
