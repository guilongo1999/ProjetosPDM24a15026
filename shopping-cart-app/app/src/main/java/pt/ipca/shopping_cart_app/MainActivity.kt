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
import pt.ipca.shopping_cart_app.app.PostOfficeApp
//import pt.ipca.shopping_cart_app.ui.theme.ShoppingcartappTheme

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {



                DefaultPreviewOfSignUpScreen()

        }

        auth = Firebase.auth
    }

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

