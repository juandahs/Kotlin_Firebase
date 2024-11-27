package com.example.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth

        binding.btnLogin.setOnClickListener{
            //val email = binding.etEmail.toString()
            //val clave = binding.etPassword.toString()

            val email = "juancastro@gmail.com"
            val clave = "123456"

            SingIn(email,clave)
        }
    }

    private fun SingIn(email: String, clave: String) {
        firebaseAuth.signInWithEmailAndPassword(email,clave)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful){

                    Toast.makeText(this,"Bienvenido",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,HomeActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(this,"Error Listener",Toast.LENGTH_SHORT).show()
            }
    }
}