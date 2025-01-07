package pt.ipca.shopping_cart_app.ui.detailsList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pt.ipca.shopping_cart_app.data.room.models.SavedListWithItems

@Composable
fun DetailsList(listWithItems: SavedListWithItems) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = listWithItems.list.name, style = MaterialTheme.typography.bodyLarge)
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(listWithItems.items) { item ->
                Text(text = "${item.itemName} - Qty: ${item.qty}")
            }
        }
    }
}
