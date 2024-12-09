package pt.ipca.shopping_cart_app.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.ipca.shopping_cart_app.Components.ButtonComponent
import pt.ipca.shopping_cart_app.Components.ClickableLoginTextComponent
import pt.ipca.shopping_cart_app.Components.DividerTextComponent
import pt.ipca.shopping_cart_app.Components.MyTextFieldComponent
import pt.ipca.shopping_cart_app.Components.PasswordTextFieldComponent
import pt.ipca.shopping_cart_app.Components.SignUpText
import pt.ipca.shopping_cart_app.Components.SignUpToContinueText
import pt.ipca.shopping_cart_app.Components.UnderlineSignUpText
import pt.ipca.shopping_cart_app.R
import pt.ipca.shopping_cart_app.navigation.BackButtonHandler
import pt.ipca.shopping_cart_app.navigation.PostOfficeAppRouter
import pt.ipca.shopping_cart_app.navigation.Screen

@Composable
fun LoginScreen() {

    Surface(

        //color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp)
            .background(Color.White)
    ) {

        Column(modifier = Modifier
            .fillMaxSize()
        ) {

            SignUpText(value = stringResource(id = R.string.login))
            SignUpToContinueText(value = stringResource(id = R.string.sign_in_continue))
            Spacer(modifier = Modifier.height(20.dp))
            MyTextFieldComponent(labelValue = stringResource(id = R.string.email), painterResource(id = R.drawable.email))
            PasswordTextFieldComponent(labelValue = stringResource(id = R.string.password), painterResource(id = R.drawable.password))
            Spacer(modifier = Modifier.height(40.dp))
            UnderlineSignUpText(value = stringResource(id = R.string.forgot_password))
            Spacer(modifier = Modifier.height(40.dp))
            ButtonComponent(value = stringResource(id = R.string.login))
            Spacer(modifier = Modifier.height(20.dp))
            DividerTextComponent()
            ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {

                PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
            })
        }

    }

    BackButtonHandler {

        PostOfficeAppRouter.navigateTo(Screen.SignUpScreen)
    }


}


@Preview
@Composable
fun LoginScreenPreview() {

    LoginScreen()
}