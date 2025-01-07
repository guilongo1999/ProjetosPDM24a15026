package pt.ipca.shopping_cart_app.navigation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


sealed class Screen() {

    object SignUpScreen: Screen()
    object TermsAndConditionsScreen: Screen()
    object LoginScreen: Screen()
   // object HomeScreen: Screen()
    object Home: Screen()
    //object Detail: Screen()
}



object PostOfficeAppRouter {



    val currentScreen: MutableState<Screen> = mutableStateOf(Screen.SignUpScreen)




    fun navigateTo(destination: Screen) {
        Log.d("PostOfficeAppRouter", "Navigating to: $destination")
        currentScreen.value = destination
        Log.d("PostOfficeAppRouter", "Current screen after navigation: ${currentScreen.value::class.simpleName}")
    }


}






