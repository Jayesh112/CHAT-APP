package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SIGNIN : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var mail: EditText
    private lateinit var pass: EditText
    private lateinit var signup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        supportActionBar?.hide()

        mAuth= FirebaseAuth.getInstance()

        name=findViewById(R.id.name)
        mail=findViewById(R.id.mail)
        pass=findViewById(R.id.pass)
        signup=findViewById(R.id.signup)

        signup.setOnClickListener {
            val name=name.text.toString()
            val email=mail.text.toString()
            val password=pass.text.toString()

            createuser(name, email, password)

        }
    }
    private fun createuser(name:String,email:String,password:String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful)
                {
                    addUsertoDatabase(name, email, mAuth?.uid!!)
                    val intent = Intent(this@SIGNIN, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                    Toast.makeText(this@SIGNIN, "Sign Up Successful", Toast.LENGTH_LONG).show()
                }
                else
                {
                    Toast.makeText(this@SIGNIN, "Error Creating User ID", Toast.LENGTH_LONG).show()
                }

            }
    }
            private fun addUsertoDatabase(name: String, email: String,uid: String )
            {
                mDatabaseReference=FirebaseDatabase.getInstance().getReference()
                mDatabaseReference.child("users").child(uid).setValue(User(name,email,uid))
            }

}