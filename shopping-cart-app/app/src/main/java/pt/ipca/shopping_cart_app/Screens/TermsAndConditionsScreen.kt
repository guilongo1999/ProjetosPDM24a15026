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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.ipca.shopping_cart_app.Components.SignUpText
import pt.ipca.shopping_cart_app.R
import pt.ipca.shopping_cart_app.navigation.BackButtonHandler
import pt.ipca.shopping_cart_app.navigation.PostOfficeAppRouter
import pt.ipca.shopping_cart_app.navigation.Screen

/*

@Composable
fun TermsAndConditionsScreen() {

    BackButtonHandler {

        PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
    }

    // Estrutura principal da tela
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        // Conteúdo da tela
        androidx.compose.foundation.layout.Column {
            Text(
                text = stringResource(id = R.string.terms_and_conditions_header),
                modifier = Modifier.padding(bottom = 16.dp)
            )

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

*/

@Composable
fun TermsAndConditionsScreen() {
    Log.d("TermsAndConditionsScreen", "Rendering TermsAndConditionsScreen")

    Surface(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = "This is the Terms and Conditions screen",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Preview
@Composable
fun TermsAndConditionsScreenPreview() {

    TermsAndConditionsScreen()




}