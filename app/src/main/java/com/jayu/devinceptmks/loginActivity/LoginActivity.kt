package com.jayu.devinceptmks.loginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jayu.devinceptmks.DashboardActivity
import com.jayu.devinceptmks.MainActivity
import com.jayu.devinceptmks.R
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail : EditText
    private lateinit var editTextPassword : EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewForgotPassword: TextView
    private lateinit var textViewSignUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        editTextEmail = findViewById(R.id.loginActivityEditTextEmail)
        editTextPassword = findViewById(R.id.loginActivityEditTextPassword)
        buttonLogin = findViewById(R.id.loginActivityButtonSignIn)
        textViewForgotPassword = findViewById(R.id.loginActivityTextViewForgotPassword)
        textViewSignUp = findViewById(R.id.loginActivityTextViewSignUp)


        val currentUser = auth.currentUser
        if(currentUser != null)
        {
            manageIntent(1)
        }

        initializeButton()
    }

    private fun initializeButton()
    {
        buttonLogin.setOnClickListener{

            val email: String = editTextEmail.text.toString()
            val password: String = editTextPassword.text.toString()

            if(email.isEmpty() || password.isEmpty())
                Toast.makeText(this, "Enter Credentials", Toast.LENGTH_SHORT).show()
            else
                login(email, password)
        }

        textViewSignUp.setOnClickListener {
            manageIntent(0)
        }

        textViewForgotPassword.setOnClickListener {
            manageIntent(2)
        }

    }

    /**
     * Manage Intent Activity Redirect
     * @param value
     *val = 0, Redirects to Register New User Activity
     *val = 1, Redirects to DashBoard Activity
     *val = 2, Redirects to Forgot Password Activity
     */
    private fun manageIntent(value: Int)
    {
        val intent: Intent

        if(value==1)
            intent = Intent(this, DashboardActivity::class.java)
        else if(value==2)
            intent = Intent(this, ForgotPasswordActivity::class.java)
        else
            intent = Intent(this, NewUserRegisterActivity::class.java)

        startActivity(intent)

        if(value==1)
            finishAffinity()

    }

    private fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Sign In Success.",Toast.LENGTH_SHORT).show()
                    manageIntent(1)
                } else {
                  Toast.makeText(baseContext, "Authentication failed.",Toast.LENGTH_SHORT).show()
                }
            }
    }

}