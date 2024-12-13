package pt.ipca.shopping_cart_app.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pt.ipca.shopping_cart_app.Graph
import pt.ipca.shopping_cart_app.data.room.models.Item
import pt.ipca.shopping_cart_app.data.room.models.ItemsWithStoreAndList
import pt.ipca.shopping_cart_app.ui.Category
import pt.ipca.shopping_cart_app.ui.repository.Repository


class HomeViewModel(

    private val repository: Repository = Graph.repository
):ViewModel() {



    var state by mutableStateOf(HomeState())
        private set

    init {

        getItems()
    }

    private fun getItems() {

        viewModelScope.launch { repository.getItemsWithStoreAndList.collectLatest {

            state = state.copy(
                items = it
            )
        } }
    }

    fun deleteItem(item: Item) {

        viewModelScope.launch {

            repository.deleteItem(item)
        }
    }



    fun onCategoryChange(category: Category) {

        state = state.copy(category = category)
        filterBy(category.id)
    }

    fun onItemCheckedChange(item: Item, isChecked: Boolean) {

        viewModelScope.launch {

            repository.updateItem(item = item.copy(isChecked = isChecked))
        }
    }


    private fun filterBy(shoppingListId: Int) {

        if(shoppingListId != 10001) {

            viewModelScope.launch {

                repository.getItemWithStoreAndListFilteredById(shoppingListId).collectLatest {
                    state = state.copy(items = it) } }


        }   else {

            getItems()

        }
    }


}


data class HomeState(

    val items:List<ItemsWithStoreAndList> = emptyList(),
    val category: Category = Category(),
    val itemChecked:Boolean = false

)
