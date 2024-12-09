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
import pt.ipca.shopping_cart_app.navigation.PostOfficeAppRouter.currentScreen

@Composable
fun PostOfficeApp() {
    val currentScreen = PostOfficeAppRouter.currentScreen.value
    Log.d("PostOfficeApp", "Recomposing PostOfficeApp with current screen: ${currentScreen::class.simpleName}")

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column {
            // Forçar a renderização da TermsAndConditionsScreen diretamente
            TermsAndConditionsScreen()  // TESTE DIRETO
            Text("Current Screen: ${currentScreen::class.simpleName}")  // Log no UI

            LaunchedEffect(currentScreen) {
                Log.d("PostOfficeApp", "Launched Effect: Current Screen - ${currentScreen::class.simpleName}")
            }


            when (currentScreen) {
                is Screen.SignUpScreen -> {
                    SignUpScreen()
                }
                is Screen.TermsAndConditionsScreen -> {
                    TermsAndConditionsScreen()
                }
                is Screen.LoginScreen -> {
                    LoginScreen()
                }
                else -> {
                    Text("Unknown screen")
                }
            }
        }
    }
}


