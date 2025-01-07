package pt.ipca.shopping_cart_app.ui.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import pt.ipca.shopping_cart_app.data.room.models.Item
import pt.ipca.shopping_cart_app.data.room.models.ItemDao
import pt.ipca.shopping_cart_app.data.room.models.ItemsWithStoreAndList
import pt.ipca.shopping_cart_app.data.room.models.ListDao
import pt.ipca.shopping_cart_app.data.room.models.SavedListsDao
import pt.ipca.shopping_cart_app.data.room.models.ShoppingList
import pt.ipca.shopping_cart_app.data.room.models.Store
import pt.ipca.shopping_cart_app.data.room.models.StoreDao
import javax.inject.Inject

/*

class Repository( private val listDao: ListDao,
                  private val storeDao: StoreDao, private val itemDao: ItemDao) {


    val store = storeDao.getAllStores()
    val getItemsWithStoreAndList = listDao.getItemsWithStoreList()

    fun getItemsFilteredByCategoryId(categoryId: Int): Flow<List<Item>> {
        return itemDao.getItemsFilteredByCategoryId(categoryId)
    }

    fun getItemWithStoreAndList(id:Int) = listDao.getItemsWithStoreListFilteredById(id)

    fun getItemWithStoreAndListFilteredById(id: Int) = listDao.getItemsWithStoreListFilteredById(id)

    suspend fun insertList(shoppingList: ShoppingList) {listDao.insertShoppingList(shoppingList)}

    suspend fun insertStore(store: Store) {storeDao.insert(store)}

    suspend fun insertItem(item: Item) { itemDao.insert(item)}

    suspend fun deleteItem(item: Item) {itemDao.delete(item)}

    suspend fun updateItem(item: Item) {itemDao.update(item)}
}

 */

class Repository @Inject constructor(
    private val listDao: ListDao,
    private val storeDao: StoreDao,
    private val itemDao: ItemDao,
    private val savedlistsDao: SavedListsDao
) {

    val store = storeDao.getAllStores()
    val getItemsWithListAndStore = listDao.getItemsWithStoreAndList()


    fun getItemWithStoreAndListFilteredById(id: Int) =
        listDao.getItemsWithStoreAndListFilteredById(id)





    fun getItemWithStoreAndList(id: Int) = listDao.getItemWithStoreAndListFilteredById(id)

    suspend fun insertList(shoppingList: ShoppingList) {
        withContext(Dispatchers.IO) {
            listDao.insertShoppingList(shoppingList)
        }
    }

    suspend fun insertStore(store: Store) {
        withContext(Dispatchers.IO) {
            storeDao.insert(store)
        }
    }

    suspend fun insertItem(item: Item) {
        try {
            withContext(Dispatchers.IO) {
                Log.d("ItemRepository", "Inserindo item no banco de dados: $item")
                itemDao.insert(item)
                Log.d("ItemRepository", "Item inserido com sucesso")
            }
        } catch (e: Exception) {
            Log.e("ItemRepository", "Erro ao inserir item", e)
        }
    }



    suspend fun deleteItem(item: Item) {
        withContext(Dispatchers.IO) {
            itemDao.delete(item)
        }
    }



    suspend fun updateItem(item: Item) {
        withContext(Dispatchers.IO) {
            itemDao.update(item)
        }
    }
}

