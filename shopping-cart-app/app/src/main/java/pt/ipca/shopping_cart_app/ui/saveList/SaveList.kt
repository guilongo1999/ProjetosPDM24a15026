package pt.ipca.shopping_cart_app.ui.saveList

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import pt.ipca.shopping_cart_app.data.room.models.SavedList
import pt.ipca.shopping_cart_app.data.room.models.SavedListWithItems
import pt.ipca.shopping_cart_app.data.room.models.SavedListsDao
import pt.ipca.shopping_cart_app.ui.saveList.SaveListViewModel
import java.util.Locale
import javax.inject.Inject




@Composable
fun SaveList(
        viewModel: SaveListViewModel = hiltViewModel(),
        onNavigateToListDetails: (SavedListWithItems) -> Unit
) {
        val savedLists by viewModel.savedLists.collectAsState(initial = emptyList())

        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                // Verifique se savedLists não é nulo
                items(savedLists) { savedList ->
                        Card(
                                modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                        .clickable { onNavigateToListDetails(savedList) }
                        ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                                text = savedList.list.name,
                                                style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                                text = "Criado em: ${
                                                        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                                                .format(savedList.list.date)
                                                }",
                                                style = MaterialTheme.typography.bodyMedium
                                        )
                                        Text(
                                                text = "Itens: ${savedList.items.size}",
                                                style = MaterialTheme.typography.bodyMedium
                                        )
                                }
                        }
                }
        }
}




