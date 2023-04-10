package com.example.dsmp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import com.example.dsmp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Register_Activity : AppCompatActivity() {
    private var State: RadioButton? = null

    //  private var selectedId: Int
    private var progress: ProgressDialog? = null
    private var Auth: FirebaseAuth? = null
    private var Database: DatabaseReference? = null
    private var binding: ActivityRegisterBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        Auth = FirebaseAuth.getInstance()


        progress = ProgressDialog(this)
        progress?.setTitle("Register")
        progress?.setMessage("Creating Account")



        binding?.Register?.setOnClickListener {

            progress?.show()
            if (binding?.Email?.text?.isNotEmpty() == true &&
                binding?.radioGroup?.isNotEmpty() == true &&
                binding?.passwordIpt?.text?.isNotEmpty() == true &&
                binding?.usernameipt?.text?.isNotEmpty() == true) {
                Auth?.createUserWithEmailAndPassword(binding?.Email?.text.toString(),
                    binding?.passwordIpt?.text.toString())
                    ?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            database()
                            progress?.dismiss()
                            Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show()
                            if (State?.text == "Employee") {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else if (State?.text == "Student") {
                                val intent = Intent(this, Student_Activity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                        else {
                            progress?.dismiss()
                            Toast.makeText(this, "Singed Up Failed!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else {
                Toast.makeText(this, "Enter details", Toast.LENGTH_SHORT).show()

            }



        }

        binding?.radioGroup?.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                // The flow will come here when
                // any of the radio buttons in the radioGroup
                // has been clicked
                // Check which radio button has been clicked
                // Get the selected Radio Button
                State = (group.findViewById<View>(checkedId) as RadioButton)
                //selectedId = binding?.groupradio?.checkedRadioButtonId
                Toast.makeText(this, State?.text, Toast.LENGTH_SHORT).show()

            })

    }
        fun database() {
            val username = binding?.usernameipt?.text.toString()
            val Selected_state = State?.text
            val Email = binding?.Email?.text.toString()

            Database = FirebaseDatabase.getInstance().getReference("Users")
            val user = User(username, Email, Selected_state)
            Database?.child(username)?.setValue(user)?.addOnSuccessListener {
                binding?.usernameipt?.text?.clear()
                binding?.Email?.text?.clear()
                //      binding?.groupradio?.clearCheck()
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            }?.addOnFailureListener {
                Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
            }

        }

}