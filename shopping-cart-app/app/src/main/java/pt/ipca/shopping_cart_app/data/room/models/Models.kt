package pt.ipca.shopping_cart_app.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import pt.ipca.shopping_cart_app.data.room.converter.DateConverter
import java.util.*

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
    tableName = "items"
)




data class Item(

    @ColumnInfo(name = "item_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val itemName: String,

    val qty: String,

    @ColumnInfo(name = "listId")   //      <---    alterar aqui
    val listId: Int,

    @ColumnInfo(name = "storeIdFk")
    val storeIdFk: Int,

    val date: Date, // Apenas mantenha o tipo Date

    val isChecked: Boolean
)


data class SelectedList(
    val id: String, // ID único para a lista
    val items: List<Item> // Itens dentro da lista salva
)







@Entity(
    tableName = "stores"
)
data class Store(

    @ColumnInfo(name = "store_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "listIdFk")
    val listIdFk: Int,

    val storeName: String
)

//--------------------------------------------------------------------

@Entity(tableName = "saved_lists")
data class SavedList(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val date: Date = Date()
)

@Entity(tableName = "saved_list_items")
data class SavedListItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val listId: Int, // Relacionamento com a SavedList
    val itemName: String,
    val qty: String
)

data class SavedListWithItems(
    @Embedded val list: SavedList,
    @Relation(
        parentColumn = "id",       // Relacionando com o id de SavedList
        entityColumn = "listId")
     val items: List<SavedListItem>
)


//--------------------------------------------------------------------------------------------