package pt.ipca.shopping_cart_app.ui.home

import android.annotation.SuppressLint
import android.widget.CheckBox
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.selects.select
import pt.ipca.shopping_cart_app.data.room.models.Item
import pt.ipca.shopping_cart_app.data.room.models.ItemsWithStoreAndList
import pt.ipca.shopping_cart_app.ui.Category
import pt.ipca.shopping_cart_app.ui.Utils
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.material3.LocalContentColor


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(

    onNavigate:(Int) -> Unit
) {

    val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
    val homeState = homeViewModel.state
    Scaffold(

        floatingActionButton = {

            FloatingActionButton(onClick = {onNavigate.invoke(-1)}) {


                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {

        LazyColumn {

            item {

                LazyRow {

                    items(Utils.category) {category: Category ->
                        categoryItem(iconRes = category.resId, title = category.title, selected = category == homeState.category) {

                            homeViewModel.onCategoryChange(category)
                        }
                            Spacer(modifier = Modifier.size(16.dp))
                    }
                }
            }

            items(homeState.items) {

                ShoppingItems(item = it, isChecked = it.item.isChecked, onCheckedChange = homeViewModel:: onItemCheckedChange) {

                    onNavigate.invoke(it.item.id)
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun categoryItem(

    @DrawableRes iconRes:Int,
    title:String,
    selected:Boolean,
    onItemClicked:() -> Unit
) {

    Card(

        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
            .selectable(selected = selected, interactionSource = MutableInteractionSource(),
                indication = rememberRipple(), onClick = { onItemClicked.invoke() }),
            border = BorderStroke(

                1.dp, if(selected) MaterialTheme.colorScheme.primary.copy(.5f)
                else MaterialTheme.colorScheme.onSurface,
            ),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
        containerColor = if(selected)
            MaterialTheme.colorScheme.primary.copy(.5f)
        else MaterialTheme.colorScheme.surface,
        contentColor = if(selected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurface
        )

    ) {

        Row(

            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {

            Icon(

                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(20.dp)

            )

            Spacer(modifier = Modifier.size(8.dp))
            Text(text = title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun ShoppingItems(

    item:ItemsWithStoreAndList,
    isChecked: Boolean,
    onCheckedChange: (Item, Boolean) -> Unit,
    onItemClicked: () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                onItemClicked.invoke()
            }
            .padding(8.dp)
    ) {

        Row(

            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically


        ) {

            Column(

                modifier = Modifier.padding(8.dp)
            ) {

                Text(text = item.item.itemName, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.size(4.dp))

                Text(text = item.store.storeName)

                Spacer(modifier = Modifier.size(4.dp))
                
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)) {

                    Text(text = formatDate(item.item.date), style = MaterialTheme.typography.headlineSmall,)

                }
            }

            Column(modifier = Modifier.padding(8.dp)) {

                Text(text = "Qty: ${item.item.qty}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.size(4.dp))

                Checkbox(checked = isChecked, onCheckedChange = {onCheckedChange.invoke(item.item, it)})
            }
        }
    }

}

fun formatDate(date: Date): String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)



