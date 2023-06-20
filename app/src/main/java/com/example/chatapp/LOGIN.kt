 package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.telephony.SignalStrengthUpdateRequest
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

 class LOGIN : AppCompatActivity() {
    private lateinit var mail: EditText
     private lateinit var pass: EditText
     private lateinit var login: Button
     private lateinit var signup: Button
     private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        mAuth= FirebaseAuth.getInstance()

        mail=findViewById(R.id.mail)
        pass=findViewById(R.id.pass)
        login=findViewById(R.id.login)
        signup=findViewById(R.id.signup)

        signup.setOnClickListener {
            val intent = Intent(this, SIGNIN::class.java)
            finish()
            startActivity(intent)
        }
        login.setOnClickListener {
            val email = mail.text.toString()
            val password = pass.text.toString()

            login(email,password)
        }
    }
     private fun login(email:String, password:String)
     {
         mAuth.signInWithEmailAndPassword(email, password)
             .addOnCompleteListener(this) { task ->
                 if (task.isSuccessful)
                 {
                     val intent = Intent(this@LOGIN, MainActivity::class.java)
                     finish()
                     startActivity(intent)
                     Toast.makeText(this@LOGIN, "Log In Successful",Toast.LENGTH_LONG).show()
                 }
                 else
                 {
                     Toast.makeText(this@LOGIN, "User does not exist",Toast.LENGTH_LONG).show()
                 }
             }
     }
}