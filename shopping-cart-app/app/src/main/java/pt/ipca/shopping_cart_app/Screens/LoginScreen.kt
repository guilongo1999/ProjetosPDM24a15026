

/*

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


*/  //velha implementacao, usar componentes caso necessario

package pt.ipca.shopping_cart_app.Screens



import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.shopping_cart_app.Components.CheckBoxComponent
import pt.ipca.shopping_cart_app.Components.UnderlineSignUpText
import pt.ipca.shopping_cart_app.R
import pt.ipca.shopping_cart_app.navigation.PostOfficeAppRouter
import pt.ipca.shopping_cart_app.navigation.Screen

private const val LOGIN_TAG = "LoginScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: (user: com.google.firebase.auth.FirebaseUser?) -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)

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

        // Login Button
        Button(
            onClick = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.i(LOGIN_TAG, "Login successful")
                            onLoginSuccess(auth.currentUser)
                        } else {
                            val error = task.exception?.localizedMessage ?: "Login failed"
                            Log.e(LOGIN_TAG, error)
                            errorMessage = error
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }


        Spacer(modifier = Modifier.height(8.dp))


        CheckBoxComponent(value = stringResource(id = R.string.terms_and_conditions),
            onTextSelected = {

                PostOfficeAppRouter.navigateTo(Screen.TermsAndConditionsScreen)
            }
        )



        Spacer(modifier = Modifier.height(8.dp))


        // Sign Up Navigation Button
        TextButton(onClick = onNavigateToSignUp) {
            Text("Don't have an account? Sign Up")
        }

        // Error Message
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage ?: "", color = MaterialTheme.colorScheme.error)
        }
    }
}




@Preview
@Composable
fun LoginScreenPreview() {

    LoginScreen(
       onLoginSuccess = {user ->
           Log.i(LOGIN_TAG, "Login Success with user: $user")
       },
        onNavigateToSignUp = {
            Log.i(LOGIN_TAG, "Navigating to SignUp Screen")
        }
    )
}