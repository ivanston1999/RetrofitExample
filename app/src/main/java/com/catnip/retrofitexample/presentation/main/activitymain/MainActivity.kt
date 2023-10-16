package com.catnip.retrofitexample.presentation.main.activitymain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.catnip.retrofitexample.databinding.ActivityMainBinding
import com.catnip.retrofitexample.presentation.main.adapter.ProductAdapter
import com.catnip.retrofitexample.presentation.main.viewmodel.MainViewModel
import com.catnip.retrofitexample.utils.ResultWrapper

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels()

    private val productAdapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        observeData()
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        binding.mainRv.layoutManager = LinearLayoutManager(this)
        binding.mainRv.adapter = productAdapter
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.productState.collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultWrapper.Success -> {
                        binding.progressBar.visibility = View.GONE
                        result.payload?.let { products ->
                            productAdapter.submitData(products)
                        }
                    }
                    is ResultWrapper.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                    is ResultWrapper.Empty -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, "Data kosong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}