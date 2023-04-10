package com.example.dsmp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.example.dsmp.R
import com.example.dsmp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    var User_type : String? = null
    private var State: RadioButton? = null
    private  val Auth = FirebaseAuth.getInstance()
    private  var Database: DatabaseReference? = null
    private var binding:ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)



        binding?.RegisterTV?.setOnClickListener {
            val intent = Intent(this,Register_Activity::class.java)
            startActivity(intent)
            finish()
        }

        binding?.Login?.setOnClickListener {
            Read_data()
            Auth.signInWithEmailAndPassword(binding?.emailInput?.text.toString(),binding?.passwordIpt?.text.toString()).
            addOnCompleteListener {
                if (it.isSuccessful) {
                    if (User_type == "Employee"){
                        val intent = Intent(this,EmployeeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else if(User_type == "Student"){
                        val intent = Intent(this,Student_Activity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else{
                    Toast.makeText(this, "Log In failed ", Toast.LENGTH_SHORT).show()
            }
            }
            }
        }

  private fun Read_data (){
      Database = FirebaseDatabase.getInstance().getReference("Users")
      Database!!.child(binding?.usernameipt?.text.toString()).get().addOnSuccessListener {
          if (it.exists()){
              val Username = it.child("username").value
               User_type = it.child("state").value as String

          }else{
              Toast.makeText(this,"User Doesn't Exist",Toast.LENGTH_SHORT).show()
          }
      }.addOnFailureListener{
          Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
      }
  }
}

