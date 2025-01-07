/*

package pt.ipca.shopping_cart_app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import pt.ipca.shopping_cart_app.Screens.DefaultPreviewOfSignUpScreen
import pt.ipca.shopping_cart_app.Screens.SignUpScreen
import pt.ipca.shopping_cart_app.app.PostOfficeApp
//import pt.ipca.shopping_cart_app.ui.theme.ShoppingcartappTheme

private const val TAG = "MainActivity"



class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val auth = Firebase.auth
        Log.i(TAG, "onCreate utilizador atual: ${auth.currentUser}")
        auth.currentUser
        auth = FirebaseAuth.getInstance()





           auth.createUserWithEmailAndPassword("pedro@gmail.com", "pedro123").addOnCompleteListener {

                 task ->

                 if(task.isSuccessful) {


                     Log.i(TAG, "create user: success")


                 } else {

                     Log.i(TAG, "create user: falhado -> ${task.exception}")
                 }
             }






       // auth.signInWithEmailAndPassword("pedro@gmail.com", "pedro123")

       // auth.signOut()



        setContent {


            PostOfficeApp(auth = auth)

        }


    }

}

*/

package pt.ipca.shopping_cart_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import pt.ipca.shopping_cart_app.Screens.TermsAndConditionsScreen
import pt.ipca.shopping_cart_app.app.PostOfficeApp
//import pt.ipca.shopping_cart_app.ui.AppNavigationHomeDetail
//import pt.ipca.shopping_cart_app.ui.AuthNavigation
//import pt.ipca.shopping_cart_app.ui.AuthNavigationFlow
import pt.ipca.shopping_cart_app.ui.NavControllerNavigation
//import pt.ipca.shopping_cart_app.ui.appNavigationHomeDetail
import pt.ipca.shopping_cart_app.ui.detail.Detail
import pt.ipca.shopping_cart_app.ui.home.Home

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        setContent {

            NavControllerNavigation(auth = auth)


        }
    }
}

/*

@Composable
fun AuthFlow(auth: FirebaseAuth) {
    var isAuthenticated by remember { mutableStateOf(auth.currentUser != null) }

    DisposableEffect(auth) {
        val authStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            isAuthenticated = firebaseAuth.currentUser != null
        }
        auth.addAuthStateListener(authStateListener)
        onDispose { auth.removeAuthStateListener(authStateListener) }
    }

    if (isAuthenticated) {
        AppNavigationHomeDetail(
            auth = auth,
            onLogout = {
                isAuthenticated = false // Força a navegação de volta para Login
            }
        )
    } else {
        AuthNavigation(
            auth = auth)
    }
}

 */



















//-------------------------------------------------------------------------codigo para binding----------------------------------------------------------------------------------------

    /*


    private fun createUserWithEmail(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

            task ->

            if(task.isSuccessful) {

                Log.d(TAG, "createUserWithEmailAndPassword:Success")
                val user = auth.currentUser
            } else {

                Log.w(TAG,"createUserWithEmailAndPassword:Failure", task.exception)
                Toast.makeText(baseContext,"Failed to Authenticate",Toast.LENGTH_SHORT).show()
            }


        }
    }

    private fun signInWithEmailAndPassword(email: String, password: String) {

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {

            task ->

            if(task.isSuccessful) {

                Log.d(TAG, "signInUserWithEmailAndPassword:Success")
                val user = auth.currentUser

            } else {

                Log.w(TAG,"signInUserWithEmailAndPassword:Failure", task.exception)
                Toast.makeText(baseContext,"Failed to Authenticate",Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

        private var TAG = "EmailAndPassword"
    }



}

*/