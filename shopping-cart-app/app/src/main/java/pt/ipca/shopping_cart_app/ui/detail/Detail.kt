package pt.ipca.shopping_cart_app.ui.detail

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.shopping_cart_app.ui.Category
//import pt.ipca.shopping_cart_app.ui.home.formatDate
import java.util.Calendar
import pt.ipca.shopping_cart_app.componentShapes
import pt.ipca.shopping_cart_app.navigation.BackButtonHandler
import pt.ipca.shopping_cart_app.ui.Utils
import pt.ipca.shopping_cart_app.ui.home.Home
import pt.ipca.shopping_cart_app.ui.home.categoryItem
import pt.ipca.shopping_cart_app.ui.home.formatDate
import java.util.*
//import java.sql.Date



private const val DETAIL_TAG = "Detail"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detail(

    id:Int,
    navigateUp: () -> Unit,
    auth: FirebaseAuth,
    //onLogout: () -> Unit
) {

    val viewModel: DetailViewModel = hiltViewModel()

    if (id == -1) {
        viewModel.resetState()
    } else {
        viewModel.loadItem(id)
    }


    Scaffold {

        DetailEntry(
            state = viewModel.state,
            onDateSelected = viewModel::onDateChange,
            onStoreChange = viewModel::onStoreChange,
            onItemChange = viewModel::onItemChange,
            onQtyChange = viewModel::onQtyChange,
            onCategoryChange = viewModel::onCategoryChange,
            onDialogDismissed = viewModel::onScreenDialogDismissed,
            onSaveStore = viewModel::addStore,
            updateItem = { viewModel.updateShoppingItem(id) },
            saveItem = viewModel::addShoppingItem

        ) {

            navigateUp.invoke()
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailEntry(

    modifier: Modifier = Modifier,
    state: DetailState,
    onDateSelected: (Date) -> Unit,
    onStoreChange: (String) -> Unit,
    onItemChange: (String) -> Unit,
    onQtyChange: (String) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onDialogDismissed: (Boolean) -> Unit,
    onSaveStore: () -> Unit,
    updateItem: () -> Unit,
    saveItem: () -> Unit,
    navigateUp: () -> Unit,
    //onScreenDialogDismissed: (Boolean) -> Unit  // Added the function parameter here

    ) {


    var itemState by remember { mutableStateOf(state.item) }
    var storeState by remember { mutableStateOf(state.store) }
    var qtyState by remember { mutableStateOf(state.qty) }
    var categoryState by remember { mutableStateOf(state.category) }


    var isNewsEnabled by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        TextField(
            value = itemState,  // Agora usando itemState
            onValueChange = { newItem ->
                itemState = newItem // Atualizando itemState ao digitar
                onItemChange(newItem) // Passando o valor para o ViewModel
            },
            label = { Text(text = "Item") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = componentShapes.large
        )
        Spacer(modifier = Modifier.Companion.size(12.dp))

        Row {
            TextField(
                value = storeState,
                onValueChange = {
                    storeState = it
                    if (isNewsEnabled) onStoreChange.invoke(it)
                },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = componentShapes.large,
                label = { Text(text = "Store") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onDialogDismissed.invoke(!state.isScreenDialogDismissed)
                        }
                    )
                }
            )
            if (!state.isScreenDialogDismissed) {
                Popup(
                    onDismissRequest = {
                        onDialogDismissed.invoke(!state.isScreenDialogDismissed)
                    }
                ) {
                    Surface(modifier = Modifier.padding(16.dp)) {
                        Column {
                            state.storeList.forEach { store ->
                                Text(
                                    text = store.storeName,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable {
                                            onStoreChange.invoke(store.storeName)
                                            onDialogDismissed(!state.isScreenDialogDismissed)
                                        },
                                )

                            }
                        }
                    }

                }
            }
            TextButton(onClick = {
                isNewsEnabled = if (isNewsEnabled) {
                    onSaveStore.invoke()
                    !isNewsEnabled
                } else {
                    !isNewsEnabled
                }
            }) {
                Text(text = if (isNewsEnabled) "Save" else "New")
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = formatDate(state.date))
                Spacer(modifier = Modifier.size(4.dp))
                val mDatePicker = datePickerDialog(
                    context = LocalContext.current,
                    onDateSelected = { dateinMillis ->
                        onDateSelected(Date(dateinMillis))
                    }
                )
                IconButton(onClick = { mDatePicker.show() }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                }
            }
            TextField(
                value = qtyState,
                onValueChange = { input ->
                    qtyState = input.filter { it.isDigit() }
                    onQtyChange(qtyState)
                },
                label = { Text(text = "Qty") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                shape = componentShapes.large
            )
        }
        Spacer(modifier = Modifier.size(12.dp))
        LazyRow {
            items(Utils.category) { category: Category ->
                categoryItem(
                    iconRes = category.resId,
                    title = category.title,
                    selected = category == categoryState
                ) {
                    categoryState = category
                    onCategoryChange(category)
                }
                Spacer(modifier = Modifier.size(16.dp))
            }
        }

        val buttonTitle = if (state.isUpdatingItem) "Update Item"
        else "Add item"
        Button(
            onClick = {
                when (state.isUpdatingItem) {
                    true -> {
                        updateItem.invoke()
                    }

                    false -> {
                        saveItem.invoke()
                    }
                }
                navigateUp.invoke()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = itemState.isNotEmpty() &&
                    storeState.isNotEmpty() &&
                    qtyState.isNotEmpty(),
            shape = componentShapes.large
        ) {
            Text(text = buttonTitle)
        }
    }


    // Botão de Logout na TopBar
    IconButton(onClick = {
        navigateUp()

    }, modifier = Modifier
        .padding(top = 500.dp)

        ) {
        Icon(
            imageVector = Icons.Default.ExitToApp,
            contentDescription = "Logout",
            tint = Color.Red,

            )
    }
    }


fun formatDate(dateInMillis: Long): String {
    val date = Date(dateInMillis)
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return dateFormat.format(date)
}




@Composable
fun datePickerDialog(context: Context,
                     onDateSelected:(Long) -> Unit):DatePickerDialog {


    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    //calendar.time = Date()



    val mDatePickerDialog = DatePickerDialog(

        context,

        {

                _, mYear, mMonth, mDayOfMonth ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(mYear, mMonth, mDayOfMonth)
            val selectedDateInMillis = selectedCalendar.timeInMillis // Em milissegundos
            onDateSelected(selectedDateInMillis)

        }, year,month,day
    )

    return  mDatePickerDialog

}


@Preview(showSystemUi = true)
@Composable
fun prevDetailEntry() {

    DetailEntry(
        state = DetailState(),
        onDateSelected = {},
        onStoreChange = {},
        onItemChange = {},
        onQtyChange = {},
        onCategoryChange = {},
        onDialogDismissed = {},
        onSaveStore = { /*TODO*/ },
        updateItem = { /*TODO*/ },
        saveItem = { /*TODO*/ }) {
        
    }
}


/*


@Preview
@Composable
fun DetailPreview() {

    val auth = FirebaseAuth.getInstance() //simulado porque e impossivel fazer a autenticacao real no preview


    Detail(auth = auth, onLogout = { Log.i(DETAIL_TAG, "Login Out")}, id = 1, navigateUp = {})




}

*/

