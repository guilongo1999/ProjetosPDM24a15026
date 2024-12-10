

/*

package pt.ipca.shopping_cart_app.app

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import pt.ipca.shopping_cart_app.Screens.LoginScreen
import pt.ipca.shopping_cart_app.Screens.SignUpScreen
import pt.ipca.shopping_cart_app.Screens.TermsAndConditionsScreen
import pt.ipca.shopping_cart_app.navigation.PostOfficeAppRouter
import pt.ipca.shopping_cart_app.navigation.Screen
//import pt.ipca.shopping_cart_app.Screens
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.shopping_cart_app.Screens.DefaultPreviewOfSignUpScreen
import pt.ipca.shopping_cart_app.navigation.PostOfficeAppRouter.currentScreen
import pt.ipca.shopping_cart_app.navigation.PostOfficeAppRouter
import pt.ipca.shopping_cart_app.navigation.Screen

@Composable
fun PostOfficeApp() {


    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {


        Crossfade(targetState = PostOfficeAppRouter.currentScreen) {

                currentState ->

            when(currentState.value) {

                is Screen.SignUpScreen -> {

                    DefaultPreviewOfSignUpScreen()
                }

                is Screen.TermsAndConditionsScreen -> {

                    TermsAndConditionsScreen()
                }

                is Screen.LoginScreen -> {

                    LoginScreen()
                }

                else -> {
                }
            }

        }



    }


}


*/

package pt.ipca.shopping_cart_app.app

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.shopping_cart_app.Screens.HomeScreen
import pt.ipca.shopping_cart_app.Screens.LoginScreen
import pt.ipca.shopping_cart_app.Screens.SignUpScreen
import pt.ipca.shopping_cart_app.Screens.TermsAndConditionsScreen
import pt.ipca.shopping_cart_app.navigation.PostOfficeAppRouter
import pt.ipca.shopping_cart_app.navigation.Screen

private const val TAG = "PostOfficeApp"

@Composable
fun PostOfficeApp(auth: FirebaseAuth) {
    var currentUser by remember { mutableStateOf(auth.currentUser) }

    // Usando DisposableEffect para monitorar o estado do usuário e remover o listener
    DisposableEffect(auth) {
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            currentUser = firebaseAuth.currentUser
            Log.i(TAG, "AuthStateListener: ${currentUser?.email ?: "Usuário deslogado"}")
        }

        // Adiciona o listener
        auth.addAuthStateListener(authStateListener)

        // Remover o listener quando o Composable for descartado
        onDispose {
            auth.removeAuthStateListener(authStateListener)
            Log.i(TAG, "AuthStateListener removido")
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Crossfade(targetState = PostOfficeAppRouter.currentScreen) { currentState ->
            when (currentState.value) {
                is Screen.LoginScreen -> {
                    LoginScreen(
                        onLoginSuccess = { user ->
                            currentUser = user
                            PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
                        },
                        onNavigateToSignUp = {
                            PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
                        }
                    )
                }

                is Screen.SignUpScreen -> {
                    SignUpScreen(
                        onSignUpSuccess = { user ->
                            currentUser = user
                            PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
                        },
                        onNavigateToLogin = {
                            PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
                        }
                    )
                }

                is Screen.TermsAndConditionsScreen -> {
                    if (currentUser != null) {
                        TermsAndConditionsScreen(
                            onLogout = {
                                auth.signOut() // Realiza o logout
                                PostOfficeAppRouter.navigateTo(Screen.LoginScreen) // Redireciona para Login
                            }
                        )
                    } else {
                        // Se não houver um usuário autenticado, navega para Login
                        PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
                    }
                }


                is Screen.HomeScreen -> {
                    HomeScreen(
                        auth = auth,
                        onLogout = {
                            auth.signOut()
                            PostOfficeAppRouter.navigateTo(Screen.LoginScreen) // Redireciona para Login após logout
                        }
                    )
                }

                else -> {
                    // Redireciona para a tela de Login caso não haja estado válido
                    if (currentUser == null) {
                        PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
                    } else {
                        PostOfficeAppRouter.navigateTo(Screen.TermsAndConditionsScreen)
                    }
                }
            }
        }
    }
}

