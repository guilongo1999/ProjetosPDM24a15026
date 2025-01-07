package pt.ipca.shopping_cart_app.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.ipca.shopping_cart_app.data.room.models.Item
import pt.ipca.shopping_cart_app.data.room.models.ItemsWithStoreAndList
import pt.ipca.shopping_cart_app.ui.Category
import pt.ipca.shopping_cart_app.ui.Utils
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import pt.ipca.shopping_cart_app.Screens.HomeScreen
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateListOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.ipca.shopping_cart_app.data.room.models.SelectedList
import pt.ipca.shopping_cart_app.ui.Routes
import java.util.*


private const val HOME_TAG = "Home"



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    onNavigate: (Int) -> Unit, // Navegação para Detalhes
    auth: FirebaseAuth,        // Instância do Firebase Authentication
    onLogout: () -> Unit       // Callback de logout
) {
    //val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
    val homeViewModel: HomeViewModel = hiltViewModel()
    val homeState = homeViewModel.state

   // Log.d(HOME_TAG, "Categoria selecionada inicial: ${homeState.category}")
   // Log.d(HOME_TAG, "Itens iniciais: ${homeState.items}")

    val items = remember { mutableStateListOf<Item>() }

    // Lista de itens selecionados
    val selectedItems = remember { mutableStateListOf<Item>() }

    // Lista de listas de itens (armazenamento das seleções)
    val savedLists = remember { mutableStateListOf<SelectedList>() }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                actions = {
                    // Botão de Logout na TopBar
                    IconButton(onClick = {
                        auth.signOut() // Realiza logout
                        onLogout()     // Chama callback de logout
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.Red
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            Row(
                modifier = Modifier.padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Botão para navegar para listas salvas
                FloatingActionButton(
                    onClick = {
                        Log.d("Home", "Botão de listas salvas clicado. Navegando para saved_lists")
                        navController.navigate(Routes.SavedLists.name) },
                ) {
                    Icon(Icons.Default.List, contentDescription = "Ver Listas Salvas")
                }

                // Botão para adicionar um novo item
                FloatingActionButton(
                    onClick = {
                        Log.d("Home", "Botão de adicionar item clicado. Enviando ID: -1")




                            onNavigate.invoke(-1)




                    }


                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Item")
                }
            }
        },


        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botão para salvar seleção
                    Button(onClick = {
                        if (selectedItems.isNotEmpty()) {
                            savedLists.add(
                                SelectedList(
                                    id = UUID.randomUUID().toString(),
                                    items = selectedItems.toList()
                                )
                            )
                            selectedItems.clear() // Limpar seleção após salvar
                        }
                    }) {
                        Text("Salvar Seleção")
                    }

                    // Botão para limpar seleção
                    Button(onClick = {
                        selectedItems.clear()
                    }) {
                        Text("Limpar Seleção")
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            item {
                if (Utils.category.isEmpty()) {
                    Log.d(HOME_TAG, "Nenhuma categoria disponível.")
                    Text("Nenhuma categoria disponível.", Modifier.padding(16.dp))
                } else {
                    LazyRow {
                        items(Utils.category) { category:Category ->
                            categoryItem(
                                iconRes = category.resId,
                                title = category.title,
                                selected = category == homeState.category
                            ) {
                                Log.d(HOME_TAG, "Categoria clicada: ${category.title}")
                                homeViewModel.onCategoryChange(category)
                            }
                            Spacer(modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            if (homeState.isLoading) {
                Log.d(HOME_TAG, "Carregando dados...")
                item { CircularProgressIndicator(Modifier.padding(16.dp)) }
            } else if (homeState.items.isEmpty()) {
                Log.d(HOME_TAG, "Nenhum item disponível para exibição.")
                item { Text("Nenhum item disponível.", Modifier.padding(16.dp)) }
            } else {
                items(homeState.items) { item ->
                    ShoppingItems(
                        item = item,
                        isChecked = item.item.isChecked,
                        onCheckedChange = homeViewModel::onItemCheckedChange,
                        onDeleteClick = { homeViewModel.deleteItem(it) }, // Chama a função de exclusão no ViewModel
                        onItemClicked = {
                            Log.d(HOME_TAG, "Navegando para detalhes do item: ${item.item.id}")
                            onNavigate.invoke(item.item.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ShoppingItemRow(
    item: Item,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(item.itemName, style = MaterialTheme.typography.bodyLarge)
        Text("Quantidade: ${item.qty}", style = MaterialTheme.typography.bodyMedium)


        Checkbox(
            checked = isChecked,
            onCheckedChange = {onCheckedChange(it)}
        )
    }
}


@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun categoryItem(
    @DrawableRes iconRes: Int,
    title: String,
    selected: Boolean,
    onItemClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
            .selectable(
                selected = selected,
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(),
                onClick = { onItemClicked.invoke() }
            ),
        border = BorderStroke(
            1.dp, if (selected) MaterialTheme.colorScheme.primary.copy(.5f)
            else MaterialTheme.colorScheme.onSurface,
        ),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = if (selected)
                MaterialTheme.colorScheme.primary.copy(.5f)
            else MaterialTheme.colorScheme.surface,
            contentColor = if (selected) MaterialTheme.colorScheme.onPrimary
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
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ShoppingItems(
    item: ItemsWithStoreAndList,
    isChecked: Boolean,
    onCheckedChange: (Item, Boolean) -> Unit,
    onDeleteClick: (Item) -> Unit, // Novo parâmetro para exclusão
    onItemClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                Log.d(HOME_TAG, "Item clicado: ${item.item.itemName}")
                onItemClicked.invoke()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item.item.itemName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = item.store?.storeName ?: "Sem Loja")
                Spacer(modifier = Modifier.size(4.dp))
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)) {
                    Text(
                        text = formatDate(item.item.date),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Checkbox
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = {
                        Log.d(HOME_TAG, "Checkbox alterado: ${item.item.itemName}, checked: $it")
                        onCheckedChange.invoke(item.item, it)
                    }
                )

                Spacer(modifier = Modifier.size(8.dp))

                // Botão de Exclusão
                IconButton(onClick = {
                    Log.d(HOME_TAG, "Botão de exclusão clicado: ${item.item.itemName}")
                    onDeleteClick.invoke(item.item)
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Excluir item",
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                Text(
                    text = "Qty: ${item.item.qty}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


fun formatDate(date: Date): String =
    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)


/*

@Preview
@Composable
fun HomePreview() {

    val auth = FirebaseAuth.getInstance() //simulado porque e impossivel fazer a autenticacao real no preview


    Home(onNavigate = {}, auth = auth, onLogout = {Log.i(HOME_TAG, "Login Out")}, navController = {})




}


 */


/*

private const val TAG_HOME_SCREEN = "Home"


@Composable
fun Home(
    auth: FirebaseAuth,
    onLogout: () -> Unit,
    onNavigate: (Int) -> Unit
) {
    val user = auth.currentUser
    Log.d(TAG_HOME_SCREEN, "Usuário atual: ${user?.email ?: "Desconhecido"}")

    // Simulando dados para serem carregados
    var dataLoaded by remember { mutableStateOf(false) }
    var itemList by remember { mutableStateOf<List<String>?>(null) }

    // Carregando os dados
    LaunchedEffect(Unit) {
        try {
            Log.d(TAG_HOME_SCREEN, "Carregando dados...")
            delay(2000) // Simula uma operação assíncrona
            itemList = listOf("Produto 1", "Produto 2", "Produto 3") // Simula os dados carregados
            dataLoaded = true
            Log.d(TAG_HOME_SCREEN, "Dados carregados com sucesso: $itemList")
        } catch (e: Exception) {
            Log.e(TAG_HOME_SCREEN, "Erro ao carregar dados: ${e.message}", e)
            itemList = emptyList() // Define como lista vazia para evitar crashes
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (!dataLoaded) {
            // Mostra uma mensagem de carregamento enquanto os dados não estão disponíveis
            LoadingState()
        } else if (user == null) {
            // Verifica se o usuário não está autenticado
            Log.e(TAG_HOME_SCREEN, "Erro: Usuário não autenticado!")
            onLogout()
        } else {
            // Exibe a tela principal
            HomeContent(
                userEmail = user.email ?: "Usuário Desconhecido",
                itemList = itemList ?: emptyList(),
                onNavigate = onNavigate,
                onLogout = onLogout
            )
        }
    }
}

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
        Text("Carregando dados...", Modifier.padding(top = 16.dp))
    }
}

@Composable
fun HomeContent(
    userEmail: String,
    itemList: List<String>,
    onNavigate: (Int) -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bem-vindo, $userEmail", style = MaterialTheme.typography.titleLarge)

        if (itemList.isEmpty()) {
            Text("Nenhum produto disponível no momento.")
        } else {
            itemList.forEachIndexed { index, item ->
                Text("$item", Modifier.padding(8.dp))
                Button(onClick = { onNavigate(index) }) {
                    Text("Ver detalhes de $item")
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { onLogout() },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Logout")
        }
    }
}
















@Composable
fun Home(auth: FirebaseAuth, onLogout: () -> Unit, onNavigate: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Text(text = "Welcome to Home Screen")
    }
}



 */