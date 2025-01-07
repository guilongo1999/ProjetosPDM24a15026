

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
import com.google.firebase.auth.FirebaseUser
import pt.ipca.shopping_cart_app.Components.CheckBoxComponent
import pt.ipca.shopping_cart_app.Components.UnderlineSignUpText
import pt.ipca.shopping_cart_app.R
import pt.ipca.shopping_cart_app.navigation.PostOfficeAppRouter
import pt.ipca.shopping_cart_app.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: (FirebaseUser) -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToTerms: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val auth = FirebaseAuth.getInstance()
    var isLoading by remember { mutableStateOf(false) } // Indicador de loading
    var isNavigating by remember { mutableStateOf(false) } // Flag para navegação controlada


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botão de login
        Button(
            onClick = {
                isLoading = true // Define o estado de carregamento
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        isLoading = false // Finaliza o estado de carregamento
                        if (task.isSuccessful) {
                            Log.i("LoginScreen", "Login successful")
                            auth.currentUser?.let { onLoginSuccess(it) } // Chama o sucesso do login
                        } else {
                            val error = task.exception?.localizedMessage ?: "Login failed"
                            Log.e("LoginScreen", error)
                            errorMessage = error
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading // Desabilita o botão enquanto carrega
        ) {
            Text("Login")
        }

        // Mensagem de erro
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage ?: "", color = MaterialTheme.colorScheme.error)
        }

        // Se estiver carregando, mostra um indicador de carregamento
        if (isLoading) {
            CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(8.dp))




        TextButton(onClick = {
            if (!isNavigating) {
                isNavigating = true
                Log.d("onNavigaetToTerms", "Navigate to Terms from Login")
                onNavigateToTerms()  // Navegar para os Termos
                isNavigating = false
            }
        }) {
            Text("Terms and Conditions")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botão de navegação para a tela de SignUp
        TextButton(onClick = {
            if (!isNavigating) {
                isNavigating = true
                Log.d("LoginScreen", "Navigate to SignUp")
                onNavigateToSignUp()  // Navegar para a tela de SignUp
                isNavigating = false
            }
        }) {
            Text("Don't have an account? Sign Up")
        }




    }


}






/*
@Preview
@Composable
fun LoginScreenPreview() {

    LoginScreen(
       onLoginSuccess = {user ->
           Log.i(LOGIN_TAG, "Login Success with user: $user")
       },
        onNavigateToTerms = {}
    )
}


 */