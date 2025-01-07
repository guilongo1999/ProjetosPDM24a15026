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
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pt.ipca.shopping_cart_app.data.room.models.SavedList
import pt.ipca.shopping_cart_app.data.room.models.SavedListItem
import pt.ipca.shopping_cart_app.data.room.models.SavedListWithItems
import pt.ipca.shopping_cart_app.data.room.models.SavedListsDao
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        getItems()
    }

    fun loadItems() {
        getItems()
    }

    private fun getItems() {
        viewModelScope.launch {
            repository.getItemsWithListAndStore.collectLatest {
                state = state.copy(
                    items = it
                )
            }
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                // Perform your delete operation here (database query)
                repository.deleteItem(item)
            }
        }
    }

    fun onCategoryChange(category: Category) {
        Log.d("HomeViewModel", "Categoria selecionada: ${category.title}, id: ${category.id}")
        state = state.copy(category = category)
        filterBy(category.id)
    }


    fun onItemCheckedChange(item: Item, isChecked: Boolean) {
        viewModelScope.launch {

            repository.updateItem(
                item = item.copy(isChecked = isChecked)
            )

        }


    }



    private fun filterBy(shoppingListId: Int) {
        Log.d("HomeViewModel", "Filtrando por shoppingListId: $shoppingListId")
        if (shoppingListId != 10001) {
            viewModelScope.launch {
                repository.getItemWithStoreAndListFilteredById(shoppingListId)
                    .collectLatest {
                        //Log.d("HomeViewModel", "Itens filtrados: $filteredItems")
                        state = state.copy(items = it)
                    }
            }
        } else {
            Log.d("HomeViewModel", "shoppingListId é 10001. Carregando todos os itens.")
            getItems()
        }
    }

}




data class HomeState(

    val items:List<ItemsWithStoreAndList> = emptyList(),
    val category: Category = Category(),
    val itemChecked:Boolean = false,
    val isLoading: Boolean = false

)



