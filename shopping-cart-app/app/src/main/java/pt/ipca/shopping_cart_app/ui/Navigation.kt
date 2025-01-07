package pt.ipca.shopping_cart_app.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.shopping_cart_app.Screens.LoginScreen
import pt.ipca.shopping_cart_app.Screens.SignUpScreen
import pt.ipca.shopping_cart_app.Screens.TermsAndConditionsScreen
//import pt.ipca.shopping_cart_app.app.PostOfficeApp
import pt.ipca.shopping_cart_app.navigation.Screen
import pt.ipca.shopping_cart_app.ui.detail.Detail
import pt.ipca.shopping_cart_app.ui.detailsList.DetailsList
import pt.ipca.shopping_cart_app.ui.home.Home
import pt.ipca.shopping_cart_app.ui.saveList.SaveList
import pt.ipca.shopping_cart_app.ui.saveList.SaveListViewModel


enum class Routes {
    Home,
    Detail,
    SavedLists,
    ListDetails
}

enum class AuthRoutes {
    Login,
    SignUp,
    TermsAndConditions
}





private const val HOME_NAVIGATION_TAG = "HomeNavigation"

@Composable
fun NavControllerNavigation(auth: FirebaseAuth) {
    val navController = rememberNavController()

    val savedListsViewModel: SaveListViewModel = hiltViewModel()


    val savedLists by savedListsViewModel.savedLists.collectAsState(initial = emptyList())

    // Estado para verificar se o usuário está autenticado
    var isAuthenticated by remember { mutableStateOf(auth.currentUser != null) }

    DisposableEffect(auth) {
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            isAuthenticated = firebaseAuth.currentUser != null
        }
        auth.addAuthStateListener(authStateListener)
        onDispose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) Routes.Home.name else AuthRoutes.Login.name
    ) {
        // Tela de Login
        composable(route = AuthRoutes.Login.name) {
            LoginScreen(
                onLoginSuccess = {
                    auth.currentUser?.let { user ->
                        Log.i("PostOfficeApp", "Login efetuado: ${user.email}")
                    }
                    navController.navigate(Routes.Home.name) // Navega para a Home após login
                },
                onNavigateToSignUp = {
                    Log.i(HOME_NAVIGATION_TAG, "Navegação para o signup atraves do login.")
                    navController.navigate(AuthRoutes.SignUp.name) // Navega para a tela de cadastro
                },
                onNavigateToTerms = {
                    Log.i(HOME_NAVIGATION_TAG, "Navegação para os termos atraves do login.")
                    try {
                        navController.navigate(AuthRoutes.TermsAndConditions.name)
                    } catch (e: Exception) {
                        Log.e(
                            HOME_NAVIGATION_TAG,
                            "Erro ao tentar navegar para os termos: ${e.message}"
                        )
                    }
                }

            )
        }

        // Tela de Cadastro
        composable(route = AuthRoutes.SignUp.name) {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(Routes.Home.name) // Navega para a Home após cadastro
                },
                onNavigateToLogin = {
                    Log.i(HOME_NAVIGATION_TAG, "Navegação para login.")
                    navController.navigate(AuthRoutes.Login.name) // Volta para a tela de login
                },
                onNavigateToTerms = {
                    Log.i(HOME_NAVIGATION_TAG, "Navegação para tela de termos e condições.")
                    navController.navigate(AuthRoutes.TermsAndConditions.name)
                }
            )
        }

        // Tela de Termos e Condições
        composable(route = AuthRoutes.TermsAndConditions.name) {
            TermsAndConditionsScreen(

                onNavigateToSignUp = {
                    Log.d(
                        "RouteCheck",
                        "Navegando para rota: ${AuthRoutes.TermsAndConditions.name}"
                    )

                    navController.navigate(AuthRoutes.SignUp.name) // Navega para a tela de cadastro
                }

                //onNavigateBack = { navController.popBackStack() } // Retorna à tela anterior
            )
        }

        // Tela Home
        composable(route = Routes.Home.name) {
            Home(
                navController = navController,
                onNavigate = { id ->
                    navController.navigate("${Routes.Detail.name}?itemId=$id")
                    Log.d("Navigate", "travel to details:   ${Routes.Detail.name}?id=$id")
                },
                auth = auth,
                onLogout = {
                    auth.signOut() // Realiza o logout
                    Log.i("HomeNavigation", "Logout realizado. Navegando para Login...")
                    navController.navigate(AuthRoutes.Login.name)
                }

            )

            Log.d("teste", "valor do {$id}")
        }

        // Tela Detail
        composable(
            route = "${Routes.Detail.name}?itemId={itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: -1
            Log.d("DetailScreen", "Received itemId: $itemId")
            if (id == -1) {
                Log.d("DetailScreen", "Novo item adicionado")
                Detail(id = itemId, auth = auth, navigateUp = {
                    navController.popBackStack()
                })

            } else {
                Log.d("DetailScreen", "Exibindo detalhes do item com id: $id")
                Detail(id = itemId, auth = auth, navigateUp = {
                    navController.popBackStack()
                })
            }
        }

        //--------------------------------------------------------------------------------------
        // Rota para listas salvas
        composable(Routes.SavedLists.name) {
            SaveList(
                viewModel = hiltViewModel(),
                onNavigateToListDetails = { savedList ->
                    Log.d("SaveList", "Clicou na lista: ${savedList.list.name}")
                    navController.navigate("list_details/${savedList.list.id}")
                }
            )
        }

// Rota para detalhes da lista
        composable(
            "${Routes.ListDetails.name}/{listId}",
            arguments = listOf(navArgument("listId") { type = NavType.IntType })
        ) { backStackEntry ->
            val listId = backStackEntry.arguments?.getInt("listId") ?: -1
            val listWithItems = savedLists.find { it.list.id == listId }

            if(listId != -1) {Log.d("teste", "listID é {$listId}")}

            if (listWithItems != null) {
                DetailsList(listWithItems)
            } else {
                Text(
                    "Erro: Lista não encontrada.",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )

            }


            //---------------------------------------------------------------------------------------


        }

    }
}