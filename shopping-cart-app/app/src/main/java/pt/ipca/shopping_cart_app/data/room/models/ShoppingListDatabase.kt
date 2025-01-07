package pt.ipca.shopping_cart_app.data.room.models

import androidx.room.Database;
import androidx.room.Room
import pt.ipca.shopping_cart_app.data.room.models.Item;
import pt.ipca.shopping_cart_app.data.room.models.ShoppingList;
import pt.ipca.shopping_cart_app.data.room.models.Store;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters
import pt.ipca.shopping_cart_app.data.room.converter.DateConverter
import android.content.Context
import android.util.Log
import java.util.concurrent.Executors


@Database(entities = [SavedList::class, SavedListItem::class, ShoppingList::class, Store::class, Item::class], version = 13, exportSchema = false)
@TypeConverters(value = [DateConverter::class])
abstract class ShoppingListDatabase : RoomDatabase() {

    abstract fun listDao(): ListDao
    abstract fun itemDao(): ItemDao
    abstract fun storeDao(): StoreDao
    abstract fun savedlistsDao(): SavedListsDao

    companion object {
        @Volatile
        private var INSTANCE: ShoppingListDatabase? = null

        fun getDatabase(context: Context): ShoppingListDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingListDatabase::class.java,
                    "shopping_db"
                )
                    .fallbackToDestructiveMigration()
                    .setQueryCallback(RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
                        Log.d("RoomQuery", "Query: $sqlQuery, BindArgs: $bindArgs")
                    }, Executors.newSingleThreadExecutor())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
