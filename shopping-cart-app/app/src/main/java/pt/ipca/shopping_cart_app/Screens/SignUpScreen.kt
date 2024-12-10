package pt.ipca.shopping_cart_app.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.ipca.shopping_cart_app.Components.ButtonComponent
import pt.ipca.shopping_cart_app.Components.CheckBoxComponent
import pt.ipca.shopping_cart_app.Components.ClickableLoginTextComponent
import pt.ipca.shopping_cart_app.Components.ClickableTextComponent
import pt.ipca.shopping_cart_app.Components.DividerTextComponent
import pt.ipca.shopping_cart_app.Components.MyTextFieldComponent
import pt.ipca.shopping_cart_app.Components.PasswordTextFieldComponent
import pt.ipca.shopping_cart_app.Components.SignUpText
import pt.ipca.shopping_cart_app.Components.SignUpToContinueText
import pt.ipca.shopping_cart_app.R
import pt.ipca.shopping_cart_app.navigation.PostOfficeAppRouter
import pt.ipca.shopping_cart_app.navigation.Screen

/*

@Composable
fun SignUpScreen() {


    Surface(

        //color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp)
            .background(Color.White)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {


            SignUpText(value = stringResource(id = R.string.sign_in))
            SignUpToContinueText(value = stringResource(id = R.string.sign_in_continue))
            //MyTextFieldComponent(labelValue = stringResource(id = R.string.app_name))

        }

    }
}


@Preview
@Composable
fun DefaultPreviewOfSignUpScreen() {


    Surface(

        //color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp)
            .background(Color.White)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {




            SignUpText(value = stringResource(id = R.string.sign_in))
            SignUpToContinueText(value = stringResource(id = R.string.sign_in_continue))
            Spacer(modifier = Modifier.height(20.dp))
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.name),
                painterResource(id = R.drawable.name)
            )
            MyTextFieldComponent(
                labelValue = stringResource(id = R.string.email),
                painterResource(id = R.drawable.email)
            )
            PasswordTextFieldComponent(
                labelValue = stringResource(id = R.string.password),
                painterResource(id = R.drawable.password)
            )

            CheckBoxComponent(value = stringResource(id = R.string.terms_and_conditions),
            onTextSelected = {

                PostOfficeAppRouter.navigateTo(Screen.TermsAndConditionsScreen)
            }
            )




            Spacer(modifier = Modifier.height(80.dp))


            ButtonComponent(value = stringResource(id = R.string.register))


            Spacer(modifier = Modifier.height(20.dp))

            DividerTextComponent()
            
            ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
             PostOfficeAppRouter.navigateTo(Screen.LoginScreen)
            })

        }


    }

}

 */



import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.shopping_cart_app.Components.UnderlineSignUpText

private const val TAG = "SignUpScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onSignUpSuccess: (user: com.google.firebase.auth.FirebaseUser?) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign Up", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password Field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sign Up Button
        Button(
            onClick = {
                if (password != confirmPassword) {
                    errorMessage = "Passwords do not match!"
                    return@Button
                }
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.i(TAG, "Sign up successful")
                            onSignUpSuccess(auth.currentUser)
                        } else {
                            val error = task.exception?.localizedMessage ?: "Sign up failed"
                            Log.e(TAG, error)
                            errorMessage = error
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }

        Spacer(modifier = Modifier.height(8.dp))

        CheckBoxComponent(value = stringResource(id = R.string.terms_and_conditions),
            onTextSelected = {

                PostOfficeAppRouter.navigateTo(Screen.TermsAndConditionsScreen)
            }
        )




        Spacer(modifier = Modifier.height(8.dp))


        // Login Navigation Button
        TextButton(onClick = onNavigateToLogin) {
            Text("Already have an account? Log in")
        }

        // Error Message
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage ?: "", color = MaterialTheme.colorScheme.error)
        }
    }
}

