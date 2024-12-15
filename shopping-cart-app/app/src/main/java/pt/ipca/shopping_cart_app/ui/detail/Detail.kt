package pt.ipca.shopping_cart_app.ui.detail

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
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
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.shopping_cart_app.ui.Category
import pt.ipca.shopping_cart_app.ui.home.formatDate
import java.sql.Date
import java.util.Calendar
import pt.ipca.shopping_cart_app.componentShapes
import pt.ipca.shopping_cart_app.ui.home.Home

private const val DETAIL_TAG = "Detail"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detail(

    id:Int,
    navigateUp: () -> Unit,
    auth: FirebaseAuth,
    onLogout: () -> Unit
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
    onDateSelected: (Date) -> Date,
    onStoreChange: (String) -> Unit,
    onItemChange: (String) -> Unit,
    onQtyChange: (String) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onDialogDismissed: (Category) -> Unit,
    onSaveStore: () -> Unit,
    updateItem: () -> Unit,
    saveItem: () -> Unit,
    navigateUp: () -> Unit,
    onScreenDialogDismissed: (Boolean) -> Unit  // Added the function parameter here

    ) {

    var isNewsEnabled by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {

        TextField(
            value = state.item,
            onValueChange = { onItemChange(it) },
            label = { Text(text = "Item") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = componentShapes.large
        )

        Spacer(modifier = Modifier.size(12.dp))

        Row {

            TextField(
                value = state.store,
                onValueChange = {
                    if (isNewsEnabled) onStoreChange.invoke(it)
                },
                modifier = Modifier.weight(1f),
                label = { Text(text = "Store") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            // Correctly dismiss the dialog and pass a Boolean
                            onScreenDialogDismissed(true)
                        }
                    )
                }
            )

            if (!state.isScreenDialogDismissed) {

                Popup(
                    onDismissRequest = {
                        // Correct function call for dismissing dialog
                        onScreenDialogDismissed(true)
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
                                            onScreenDialogDismissed(true) // Dismiss dialog after selection
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun datePickerDialog(context: Context,
                     onDateSelected:(Date) -> Unit):DatePickerDialog {


    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = java.util.Date()

    val selectedDate = remember {

        mutableStateOf("")
    }



    val mDatePickerDialog = DatePickerDialog(

        context,

        {

            _:DatePicker,mYear:Int,mMonth:Int,mDayofMonth:Int ->

                val calendar = Calendar.getInstance()
                calendar.set(mYear,mMonth, mDayofMonth)
                onDateSelected.invoke(calendar.time as Date)

        }, year,month,day
    )

    return  mDatePickerDialog

}


@Preview
@Composable
fun DetailPreview() {

    val auth = FirebaseAuth.getInstance() //simulado porque e impossivel fazer a autenticacao real no preview


    Detail(auth = auth, onLogout = { Log.i(DETAIL_TAG, "Login Out")}, id = 1, navigateUp = {})




}



