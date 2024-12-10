/*

package pt.ipca.shopping_cart_app


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity: ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)



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
                Toast.makeText(baseContext,"Failed to Authenticate", Toast.LENGTH_SHORT).show()
            }


        }

    }


        companion object {

            private var TAG = "EmailAndPassword"
        }




}

 */
