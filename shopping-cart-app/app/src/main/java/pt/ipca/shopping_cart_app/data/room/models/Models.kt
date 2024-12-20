package pt.ipca.shopping_cart_app.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(
    tableName = "shopping_list"
)
data class ShoppingList(

    @ColumnInfo(name = "list_id")
    @PrimaryKey
    val id: Int,

    val name: String
)

@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = ShoppingList::class,
            parentColumns = ["list_id"],
            childColumns = ["listId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Store::class,
            parentColumns = ["store_id"],
            childColumns = ["storeIdFK"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Item(

    @ColumnInfo(name = "item_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val itemName: String,

    val qty: String,

    @ColumnInfo(name = "listId")
    val listId: Int,

    @ColumnInfo(name = "storeIdFK")
    val storeIdFK: Int,

    val date: Date,

    val isChecked: Boolean
)

@Entity(
    tableName = "stores"
)
data class Store(

    @ColumnInfo(name = "store_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "listIdFK")
    val listIdFK: Int,

    val storeName: String
)
