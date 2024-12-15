package pt.ipca.shopping_cart_app.Screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.shopping_cart_app.navigation.BackButtonHandler
import pt.ipca.shopping_cart_app.navigation.PostOfficeAppRouter
import pt.ipca.shopping_cart_app.navigation.Screen


private const val HOME_SCREEN_TAG = "HomeScreen"


@Composable
fun HomeScreen(auth: FirebaseAuth, onLogout: () -> Unit) {

    BackButtonHandler {

        PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp)
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(text = "Welcome, ${auth.currentUser?.email}")
            Button(onClick = {
                auth.signOut()
                onLogout()  // Chama o onLogout após o signOut
            }) {
                Text("Logout")
            }

            Button(
                onClick = {
                    PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Go Back to Sign Up")
            }
        }
    }
}



@Preview
@Composable
fun HomeScreenPreview() {

    val auth = FirebaseAuth.getInstance() //simulado porque e impossivel fazer a autenticacao real no preview


    HomeScreen(auth = auth, onLogout = {Log.i(HOME_SCREEN_TAG, "Login Out")})




}