

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
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.shopping_cart_app.Screens.HomeScreen
import pt.ipca.shopping_cart_app.Screens.LoginScreen
import pt.ipca.shopping_cart_app.Screens.SignUpScreen
import pt.ipca.shopping_cart_app.Screens.TermsAndConditionsScreen
import pt.ipca.shopping_cart_app.navigation.PostOfficeAppRouter
import pt.ipca.shopping_cart_app.navigation.Screen
//import pt.ipca.shopping_cart_app.ui.AppNavigationHomeDetail
import pt.ipca.shopping_cart_app.ui.AuthRoutes
import pt.ipca.shopping_cart_app.ui.Routes
import pt.ipca.shopping_cart_app.ui.detail.Detail
import pt.ipca.shopping_cart_app.ui.home.Home

private const val POST_OFFICE_TAG = "PostOfficeApp"



@Composable
fun PostOfficeApp(auth: FirebaseAuth, navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        LoginScreen(
            onLoginSuccess = { authUser ->
                if (authUser != null) {
                    Log.i(POST_OFFICE_TAG, "Login efetuado: ${authUser.email}")
                    // Navegar para a tela Home após login
                    navController.navigate(Routes.Home.name)
                }
            },
            onNavigateToSignUp = {
                Log.i(POST_OFFICE_TAG, "Navegação para tela de cadastro.")
                // Navegar para a tela de cadastro
                navController.navigate(AuthRoutes.SignUp.name)
            },
            onNavigateToTerms = {
                Log.i(POST_OFFICE_TAG, "Navegação para tela de termos e condições.")
                // Navegar para a tela de termos
                navController.navigate(AuthRoutes.TermsAndConditions.name)
            }
        )
    }
}







