package pt.ipca.shopping_cart_app.ui.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.ipca.shopping_cart_app.ui.Category
import java.sql.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detail(

    id:Int,
    navigateUp: () -> Unit
) {

    val viewModel = viewModel<DetailViewModel>(factory = DetailViewModelFactor(id))
    Scaffold {


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailEntry(

    modifier: Modifier = Modifier,
    state: DetailViewModelFactor.DetailState,
    onDateSelected:(Date) -> Date,
    onStoreChange:(String) -> Unit,
    onItemChange:(String) -> Unit,
    onQtyChange:(String) -> Unit,
    onCategoryChange:(Category) -> Unit,
    onDialogDismissed:(Category) -> Unit,
    onSaveStore:() -> Unit,
    updateItem:() -> Unit,
    saveItem:() -> Unit,
    navigateUp:() -> Unit,


    ) {

    var isNewsEnabled by remember {

        mutableStateOf(false)
    }



    Column(

        modifier = modifier.padding(16.dp)
    ) {

        TextField(value = state.item, onValueChange = {onItemChange(it)}, label = {Text(text = "item")},
            modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),

            shape = Shapes.large




        )

        Spacer(modifier = Modifier.Companion.size(12.dp))

        Row {

            TextField(value = state.store, onValueChange = {if(isNewsEnabled) onStoreChange.invoke(it)

            },

                modifier = Modifier.weight(1f),
                label = {Text(text = "Store")},
                leadingIcon = {Icon(imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null, modifier = Modifier.clickable {onDialogDismissed.invoke(!state.isScreenDialogDismissed)

                    }

                )

                }

                )

            if(!state.isScreenDialogDismissed) {

                Popup(

                    onDismissRequest = {
                        onDialogDismissed.invoke(!state.isScreenDialogDismissed)
                    }

                )
                 {

                }

                    }
            }

        }


    }
