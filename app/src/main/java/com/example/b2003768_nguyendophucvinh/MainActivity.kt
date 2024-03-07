package com.example.b2003768_nguyendophucvinh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.b2003768_nguyendophucvinh.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            val userName = binding.tvName.text.toString()
            val intent = Intent(this@MainActivity, BmiActivity::class.java)
            intent.putExtra("name", userName)
            startActivity(intent)
        }

    }
}