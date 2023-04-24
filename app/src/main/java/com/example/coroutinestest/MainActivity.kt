package com.example.coroutinestest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.coroutinestest.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var count = 0
    private var job2: Job = Job()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val job = lifecycleScope.launch {
            while (true) {
                count++
                binding.text.text = "count: $count"

                if (count % 5 == 0) {
                    delay(500)
                    doSomething()
//                    cancel()
                }
                delay(1000)
            }
        }
        binding.btn.setOnClickListener {
            lifecycleScope.launch {
                doSomething()
                job.cancel()
            }

        }
    }

    private suspend fun doSomething(){
        lifecycleScope.launch {
//            delay(100)
            binding.text.text = "thread 2"
            if (count % 5 == 0) {
                binding.text.text = "count % 5 == 0"
//                    cancel()
            }

        }
    }
}