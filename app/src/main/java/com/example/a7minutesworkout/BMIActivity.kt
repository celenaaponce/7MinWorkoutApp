package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private var currentVisibleView: String = "METRIC_UNIT_VIEW"
    private var binding: ActivityBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarBmiActivity)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener{
            onBackPressed()
        }

        makeVisibleMetricUnits()
        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->

            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnits()
            }else{
                makeVisibleUSUnits()
            }
        }

        binding?.btnCalculate?.setOnClickListener {
            if(currentVisibleView=="METRIC_UNITS_VIEW" && validateMetricUnits()){
                val heightValue : Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                val weightValue : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                val bmi = weightValue/(heightValue*heightValue)
                displayBMIResults(bmi)

            }else if(currentVisibleView=="US_UNITS_VIEW" && validateUSUnits()){
                val heightValueIn : Float = binding?.etUSUnitHeightIn?.text.toString().toFloat()
                val heightValueFt : Float = binding?.etUSUnitHeightFt?.text.toString().toFloat()
                val weightValue : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                val bmi = (weightValue/((heightValueFt*12 + heightValueIn)*(heightValueFt*12 + heightValueIn)))*703

                displayBMIResults(bmi)
            }else{
                Toast.makeText(this@BMIActivity, "Please enter valid values.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun makeVisibleMetricUnits(){
        currentVisibleView = "METRIC_UNITS_VIEW"
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilUSUnitHeight?.visibility = View.GONE
        binding?.tilUSUnitHeightIn?.visibility = View.GONE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUSUnits(){
        currentVisibleView = "US_UNITS_VIEW"
        binding?.tilMetricUnitHeight?.visibility = View.GONE
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilUSUnitHeight?.visibility = View.VISIBLE
        binding?.tilUSUnitHeightIn?.visibility = View.VISIBLE

        binding?.etUSUnitHeightFt?.text!!.clear()
        binding?.etUSUnitHeightIn?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }
    private fun displayBMIResults(bmi: Float){
        val bmiLabel:String
        val bmiDescription:String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Underweight"
            bmiDescription = "None"
        }else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <=0){
            bmiLabel = "Underweight"
            bmiDescription = "None"
        }else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <=0){
            bmiLabel = "Underweight"
            bmiDescription = "None"
        }else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <=0){
            bmiLabel = "Normal"
            bmiDescription = "None"
        }else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <=0){
            bmiLabel = "Overweight"
            bmiDescription = "None"
        }else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <=0){
            bmiLabel = "Overweight"
            bmiDescription = "None"
        }else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <=0){
            bmiLabel = "Overweight"
            bmiDescription = "None"
        }else{
            bmiLabel = "Overweight"
            bmiDescription = "None"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIDescription?.text = bmiDescription
        binding?.tvBMIType?.text = bmiLabel

    }
    private fun validateMetricUnits(): Boolean{
        var isValid = true

        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun validateUSUnits(): Boolean{
        var isValid = true

        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etUSUnitHeightFt?.text.toString().isEmpty()){
            isValid = false
        }else if(binding?.etUSUnitHeightIn?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }
}