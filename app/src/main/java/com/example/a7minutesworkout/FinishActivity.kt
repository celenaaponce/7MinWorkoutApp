package com.example.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarFinishActivity)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        val btnFinish: Button = findViewById(R.id.btnFinish)
        btnFinish.setOnClickListener{
            finish()
        }

        binding?.toolbarFinishActivity?.setNavigationOnClickListener{
            onBackPressed()
        }

        val historyDao = (application as WorkOutApp).db.historyDao()
        addDateToDatabase(historyDao)
    }

    private fun addDateToDatabase(historyDao: HistoryDao){

        val c = Calendar.getInstance()
        val dateTime = c.time
        Log.e("Date: ", "" + dateTime)

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        Log.e("Formatted Date: ", "" + date)
        lifecycleScope.launch{
            historyDao.insert(HistoryEntity(date))
            Log.e(
                "Date : ",
                "Added..."
            )
        }

    }
}