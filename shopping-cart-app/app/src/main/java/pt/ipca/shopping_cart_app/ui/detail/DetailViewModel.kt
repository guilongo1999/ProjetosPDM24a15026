package pt.ipca.shopping_cart_app.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pt.ipca.shopping_cart_app.Graph
import pt.ipca.shopping_cart_app.data.room.models.Item
import pt.ipca.shopping_cart_app.data.room.models.ShoppingList
import pt.ipca.shopping_cart_app.data.room.models.Store
import pt.ipca.shopping_cart_app.ui.Category
import pt.ipca.shopping_cart_app.ui.Utils
import pt.ipca.shopping_cart_app.ui.repository.Repository
//import java.util.Date
//import java.sql.Date
import java.time.LocalDate
import pt.ipca.shopping_cart_app.data.room.models.ItemsWithStoreAndList


@Suppress("UNCHECKED_CAST")
class DetailViewModel constructor(private val itemId:Int, private val repository: Repository = Graph.repository): ViewModel() {

    var state by mutableStateOf(DetailViewModelFactor.DetailState())
        private set

    init {

        addListItem()
        getStores()
        if (itemId != -1) {

            viewModelScope.launch {

                repository.getItemWithStoreAndListFilteredById(itemId).collectLatest { resultList ->
                    resultList.forEach { result ->
                        state = state.copy(
                            item = result.item.itemName,  // Acessando o nome do item
                            store = result.store.storeName,  // Acessando o nome da loja
                            date = result.item.date,  // Acessando a data do item
                            category = Utils.category.find { c -> c.id == result.shoppingList.id }
                                ?: Category(),
                            qty = result.item.qty  // Acessando a quantidade do item
                        )
                    }
                }
            }
        }
    }
    init {

        state = if(itemId != -1) {

            state.copy(isUpdatingItem = true)
        } else {

            state.copy(isUpdatingItem = false)
        }
    }



    val isFieldsNotEmpty:Boolean
        get() = state.item.isNotEmpty() && state.store.isNotEmpty() && state.qty.isNotEmpty()

    fun onItemChange(newValue:String) {state = state.copy(item = newValue)}

    fun onStoreChange(newValue:String) {state = state.copy(store = newValue)}

    fun onQtyChange(newValue:String) {state = state.copy(qty = newValue)}

    fun onDateChange(newValue: java.sql.Date) {state = state.copy(date = newValue)}

    fun onCategoryChange(newValue:Category) {state = state.copy(category = newValue)}

    fun onScreenDialogDismissed(newValue:Boolean) {state = state.copy(isScreenDialogDismissed = newValue)}

    fun onUpdatingItem(newValue:Boolean) {state = state.copy(isUpdatingItem = newValue)}

    private fun addListItem() {

        viewModelScope.launch { Utils.category.forEach {

            repository.insertList(

                ShoppingList(

                    id = it.id,
                    name = it.title
                )
            )
        } }
    }

    fun addShoppingItem() {

        viewModelScope.launch {

            repository.insertItem(

                Item(

                    itemName = state.item,
                    listId = state.category.id,
                    date = java.sql.Date(state.date.time),
                    qty = state.qty,
                    storeIdFK = state.storeList.find { it.storeName == state.store }?.id ?: 0,
                    isChecked = false
                )
            )
        }
    }

    fun updateShoppingItem(id:Int) {

        viewModelScope.launch {

            repository.insertItem(

                Item(

                    itemName = state.item,
                    listId = state.category.id,
                    date = java.sql.Date(state.date.time),
                    qty = state.qty,
                    storeIdFK = state.storeList.find { it.storeName == state.store }?.id ?: 0,
                    isChecked = false,
                    id = id
                )
            )
        }
    }

    fun addStore() {

        viewModelScope.launch {

            repository.insertStore(

                Store(

                    storeName = state.store,
                    listIdFK = state.category.id
                )
            )
        }
    }

    fun getStores() {

        viewModelScope.launch {

            repository.store.collectLatest {

                state = state.copy(storeList = it)
            }
        }
    }

}

class DetailViewModelFactor(private val id: Int): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return DetailViewModel(itemId = id) as T
    }


    data class DetailState(
        val storeList: List<Store> = emptyList(),
        val item: String = "",
        val store: String = "",
        val date: java.util.Date = java.util.Date(),
        val qty: String = "",
        val isScreenDialogDismissed: Boolean = true,
        val isUpdatingItem: Boolean = false,
        val category: Category = Category(),
    )


}

