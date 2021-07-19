package com.jayu.devinceptmks.loginActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jayu.devinceptmks.R
import kotlin.math.sin

class NewUserRegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextConfirmPassword: EditText
    private lateinit var buttonSignUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user_register)

        editTextEmail = findViewById(R.id.signUpActivityEditTextEmail)
        editTextPassword = findViewById(R.id.signUpActivityEditTextPassword)
        editTextConfirmPassword = findViewById(R.id.signUpActivityEditTextConfirmPassword)
        buttonSignUp = findViewById(R.id.signUpActivityButtonSignUp)

        auth = Firebase.auth

        initializeButton()
    }

    private fun initializeButton()
    {
        buttonSignUp.setOnClickListener {
            var email = editTextEmail.text.toString()
            var password = editTextPassword.text.toString()
            var confirmPassword = editTextConfirmPassword.text.toString()

            if(email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
                Toast.makeText(this, "Fill the above fields to continue", Toast.LENGTH_SHORT).show()
            else if(password.length<6)
                Toast.makeText(this, "Password should be minimum of 6 characters", Toast.LENGTH_SHORT).show()
            else if(password != confirmPassword)
                Toast.makeText(this, "Password and Confirm password didn't matched", Toast.LENGTH_SHORT).show()
            else
                signUp(email, password)
        }
    }

    private fun signUp(email:String, password:String)
    {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Sing Up Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finishAffinity()
                } else {
                    Toast.makeText(this, "Error Creating New User", Toast.LENGTH_SHORT).show()
                }
            }
    }

}