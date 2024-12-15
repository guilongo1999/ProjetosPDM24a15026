package pt.ipca.shopping_cart_app.ui.repository

import pt.ipca.shopping_cart_app.data.room.models.Item
import pt.ipca.shopping_cart_app.data.room.models.ItemDao
import pt.ipca.shopping_cart_app.data.room.models.ListDao
import pt.ipca.shopping_cart_app.data.room.models.ShoppingList
import pt.ipca.shopping_cart_app.data.room.models.Store
import pt.ipca.shopping_cart_app.data.room.models.StoreDao

class Repository( private val listDao: ListDao,
                  private val storeDao: StoreDao, private val itemDao: ItemDao) {


    val store = storeDao.getAllStores()
    val getItemsWithStoreAndList = listDao.getItemsWithStoreList()

    fun getItemWithStoreAndList(id:Int) = listDao.getItemsWithStoreListFilteredById(id)

    fun getItemWithStoreAndListFilteredById(id: Int) = listDao.getItemsWithStoreListFilteredById(id)

    suspend fun insertList(shoppingList: ShoppingList) {listDao.insertShoppingList(shoppingList)}

    suspend fun insertStore(store: Store) {storeDao.insert(store)}

    suspend fun insertItem(item: Item) {itemDao.insert(item)}

    suspend fun deleteItem(item: Item) {itemDao.delete(item)}

    suspend fun updateItem(item: Item) {itemDao.update(item)}
}