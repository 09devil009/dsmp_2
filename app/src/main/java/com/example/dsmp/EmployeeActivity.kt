package com.example.dsmp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dsmp.databinding.ActivityEmployeeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EmployeeActivity : AppCompatActivity() {
    private var auth :FirebaseUser? = null
    private var binding:ActivityEmployeeBinding? = null

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
     binding = ActivityEmployeeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding?.root)

auth = FirebaseAuth.getInstance().currentUser
       val name =  auth?.displayName
        Toast.makeText(this,name,Toast.LENGTH_SHORT).show()
    binding?.Signout?.setOnClickListener{
        Firebase.auth.signOut()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
    }
}