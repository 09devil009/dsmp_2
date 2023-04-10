package com.example.dsmp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dsmp.databinding.ActivityStudentBinding

class Student_Activity : AppCompatActivity() {
    private var binding :ActivityStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }
}