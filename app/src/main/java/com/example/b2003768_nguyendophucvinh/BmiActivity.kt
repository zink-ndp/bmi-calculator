package com.example.b2003768_nguyendophucvinh

import android.hardware.biometrics.BiometricManager.Strings
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.b2003768_nguyendophucvinh.databinding.ActivityBmiBinding
import kotlin.math.roundToInt

class BmiActivity : AppCompatActivity() {
    private lateinit var userName: String
    private lateinit var binding: ActivityBmiBinding
    private var unitPos: Int = 0
    private var genderPos: Int = 0
    private var units: List<String> = listOf("Weight in Kg & Height in Cm", "Weight in Pounds (lb) & Height in Inch (in)")
    private var gender: List<String> = listOf("Male", "Female", "Unknown")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var unitAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, units)
        var genderAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, gender)

        binding.spinUnit.adapter = unitAdapter
        binding.spinGender.adapter = genderAdapter

        binding.spinUnit.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                unitPos = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
               null
            }
        }

        binding.spinGender.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                genderPos = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                null
            }
        }

        userName = intent.getStringExtra("name").toString()

        binding.btnCalc.setOnClickListener {
            if (binding.tvAge.text.isEmpty() || binding.tvHeight.text.isEmpty() || binding.tvWeight.text.isEmpty()){
                Toast.makeText(this@BmiActivity, "Please fill information", Toast.LENGTH_SHORT).show()
            } else {
                if (binding.tvHeight.text.toString() == "0"){
                    Toast.makeText(this@BmiActivity, "Height can not be 0", Toast.LENGTH_SHORT).show()
                } else {
                    calcBMI()

                }
            }
        }

        binding.btnReset.setOnClickListener {
            reset()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

    }

    private fun calcBMI(){
        binding.cvResult.visibility = VISIBLE
        val mass = binding.tvWeight.text.toString().toFloat()
        val height = binding.tvHeight.text.toString().toFloat()

        var cate = ""
        var bmi: Double
        bmi = if (unitPos==0){
            calcbySI(mass, height)
        } else {
            calcbyUSC(mass, height)
        }


        cate = when (bmi){
            in 0.0 .. 16.0 -> "Severe Thinness"
            in 16.0 .. 17.0 -> "Moderate Thinness"
            in 17.0 .. 18.5 -> "Mild Thinness"
            in 18.5 .. 25.0 -> "Normal"
            in 25.0 .. 30.0 -> "Overweight"
            in 30.0 .. 35.0 -> "Obese Class I"
            in 35.0 .. 40.0 -> "Obese Class II"
            else -> "Obese Class III"
        }

        binding.tvUser.text = userName + ", age:" + binding.tvAge.text + ", gender: "+gender[genderPos]
        binding.tvScore.text = bmi.toString()
        binding.tvCate.text = cate

    }

    private fun reset(){
        binding.apply {
            cvResult.visibility = GONE
            tvAge.text.clear()
            tvWeight.text.clear()
            tvHeight.text.clear()
        }
    }

    private fun calcbyUSC(mass: Float, height: Float): Double{
        var bmiVal = 0.0
        bmiVal = (703 * (mass/(height*height))).toDouble()
        bmiVal = ((bmiVal * 100).toInt()).toDouble()/100
        return bmiVal
    }
    private fun calcbySI(mass: Float, height: Float): Double{
        var bmiVal = 0.0
        val h = height/100
        bmiVal = (mass/(h*h)).toDouble()
        bmiVal = ((bmiVal * 100).toInt()).toDouble()/100
        return bmiVal
    }



}