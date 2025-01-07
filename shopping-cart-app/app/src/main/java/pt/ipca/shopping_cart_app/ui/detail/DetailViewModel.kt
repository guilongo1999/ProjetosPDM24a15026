package pt.ipca.shopping_cart_app.ui.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pt.ipca.shopping_cart_app.data.room.models.Item
import pt.ipca.shopping_cart_app.data.room.models.ShoppingList
import pt.ipca.shopping_cart_app.data.room.models.Store
import pt.ipca.shopping_cart_app.ui.Category
import pt.ipca.shopping_cart_app.ui.Utils
import pt.ipca.shopping_cart_app.ui.repository.Repository
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    var state by mutableStateOf(DetailState())
        private set

    init {
        val itemId = savedStateHandle.get<Int>("itemId") ?: -1
        Log.d("DetailViewModel", "itemId recuperado: $itemId")
        initializeState(itemId)
    }

    fun loadItem(id: Int) {
        viewModelScope.launch {
            repository.getItemWithStoreAndList(id).collectLatest { result ->
                state = state.copy(
                    item = result.item.itemName,
                    store = result.store.storeName,
                    date = result.item.date,
                    qty = result.item.qty,
                    category = Utils.category.find { it.id == result.shoppingList.id }
                        ?: Category(id = -1, title = "None")
                )
            }
        }
    }

    fun resetState() {
        state = state.copy(
            item = "",
            store = "",
            qty = "",
            date = Date(),
            category = Category(),
            isUpdatingItem = false
        )
        addDefaultCategories()
        fetchStores()
    }


    private fun initializeState(itemId: Int) {
        state = state.copy(isUpdatingItem = itemId != -1)
        addDefaultCategories()
        fetchStores()
        if (itemId != -1) {
            loadItemDetails(itemId)
        }
    }

    private fun loadItemDetails(itemId: Int) {
        viewModelScope.launch {
            repository.getItemWithStoreAndList(itemId).collectLatest { result ->
                state = state.copy(
                    item = result.item.itemName,
                    store = result.store.storeName,
                    date = result.item.date,
                    qty = result.item.qty,
                    category = Utils.category.find { it.id == result.shoppingList.id }
                        ?: Category(id = -1, title = "None")
                )
            }
        }
    }






    private fun addDefaultCategories() {
        viewModelScope.launch {
            Utils.category.forEach { category ->
                repository.insertList(ShoppingList(id = category.id, name = category.title))
            }
        }
    }

    private fun fetchStores() {
        viewModelScope.launch {
            repository.store.collectLatest { stores ->
                state = state.copy(storeList = stores)
            }
        }
    }

    val isFieldsNotEmpty: Boolean
        get() = state.item.isNotEmpty() && state.store.isNotEmpty() && state.qty.isNotEmpty()

    fun onItemChange(newValue: String) {
        Log.d("DetailViewModel", "onItemChange: $newValue")
        state = state.copy(item = newValue)
    }

    fun onStoreChange(newValue: String) {
        state = state.copy(store = newValue)
    }

    fun onQtyChange(newValue: String) {
        state = state.copy(qty = newValue)
    }

    fun onDateChange(newValue: Date) {
        state = state.copy(date = newValue)
    }

    fun onCategoryChange(newValue: Category) {
        state = state.copy(category = newValue)
    }

    fun onScreenDialogDismissed(newValue: Boolean) {
        state = state.copy(isScreenDialogDismissed = newValue)
    }

    fun addShoppingItem() {
        viewModelScope.launch {
            repository.insertItem(
                createItem()
            )
        }
    }

    fun updateShoppingItem(id: Int) {
        viewModelScope.launch {
            repository.insertItem(
                createItem(id)
            )
        }
    }

    private fun createItem(id: Int? = null): Item {
        val storeId = state.storeList.find { it.storeName == state.store }?.id ?: 0
        return Item(
            id = id ?: 0,
            itemName = state.item,
            listId = state.category.id,
            date = state.date,
            qty = state.qty,
            storeIdFk = storeId,
            isChecked = false
        )
    }

    fun addStore() {
        viewModelScope.launch {
            repository.insertStore(
                Store(
                    storeName = state.store,
                    listIdFk = state.category.id
                )
            )
        }
    }

    fun saveShoppingItem(item: Item) {
        viewModelScope.launch {
            Log.d("DetailViewModel", "Tentando inserir item: $item")
            repository.insertItem(item)
        }
    }
}

data class DetailState(
    val storeList: List<Store> = emptyList(),
    val item: String = "",
    val store: String = "",
    val date: Date = Date(),
    val qty: String = "",
    val isScreenDialogDismissed: Boolean = true,
    val isUpdatingItem: Boolean = false,
    val category: Category = Category(),
)




