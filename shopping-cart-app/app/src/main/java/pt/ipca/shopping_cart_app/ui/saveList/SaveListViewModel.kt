package pt.ipca.shopping_cart_app.ui.saveList

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import pt.ipca.shopping_cart_app.data.room.models.Item
import pt.ipca.shopping_cart_app.data.room.models.SavedList
import pt.ipca.shopping_cart_app.data.room.models.SavedListItem
import pt.ipca.shopping_cart_app.data.room.models.SavedListWithItems
import pt.ipca.shopping_cart_app.data.room.models.SavedListsDao
import javax.inject.Inject


/*
class SavedListsViewModel(
    private val dao: SavedListsDao
) : ViewModel() {
    val savedLists: Flow<List<SavedListWithItems>> = dao.getSavedListsWithItems()

 */

@HiltViewModel
class SaveListViewModel @Inject constructor(
    private val dao: SavedListsDao
) : ViewModel() {

    // Obter listas salvas com itens
    val savedLists: Flow<List<SavedListWithItems>> = dao.getSavedListsWithItems()


    fun saveList(name: String, items: List<Item>) {
        viewModelScope.launch {
            try {
                Log.d("SavedListsViewModel", "Salvando lista: $name com ${items.size} itens")
                val listId = dao.insertList(SavedList(name = name))
                Log.i("SavedListsViewModel", "Lista salva com ID: $listId")
                dao.insertListItems(items.map {
                    SavedListItem(
                        listId = listId.toInt(),
                        itemName = it.itemName,
                        qty = it.qty
                    )
                })
                Log.i("SavedListsViewModel", "Itens adicionados à lista com sucesso")
            } catch (e: Exception) {
                Log.e("SavedListsViewModel", "Erro ao salvar lista: ${e.message}")
            }
        }
    }

}