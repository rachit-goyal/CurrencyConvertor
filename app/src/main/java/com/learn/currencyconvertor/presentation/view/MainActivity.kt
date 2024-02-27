package com.learn.currencyconvertor.presentation.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.learn.currencyconvertor.R
import com.learn.currencyconvertor.adapter.RvAdapter
import com.learn.currencyconvertor.data.remote.models.CurrencyRate
import com.learn.currencyconvertor.databinding.ActivityMainBinding
import com.learn.currencyconvertor.presentation.viewModel.MainViewModel
import com.learn.currencyconvertor.worker.CurrencyWorker
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var mainViewModel: MainViewModel
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var toCurrencySelected: String = ""
    private var amount: Double = 0.0
    private var currencyList: List<CurrencyRate>? = null
    private lateinit var rvAdapter: RvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setUpWorker()
        mainViewModel.productsLiveData.observe(this) {
            it?.let {
                if (it.isEmpty()) {
                    binding.progress.visibility = View.VISIBLE
                    binding.mainLayout.visibility = View.GONE
                } else {
                    currencyList = it
                    binding.progress.visibility = View.GONE
                    binding.mainLayout.visibility = View.VISIBLE
                    binding.numberEditText.filters =
                        arrayOf<InputFilter>(DecimalDigitsInputFilter(5, 2))
                    setSpinnerData(it)
                    setRecyclerView(it)
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun setRecyclerView(it: List<CurrencyRate>) {
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 3)

        binding.recyclerView.layoutManager = layoutManager


        rvAdapter = RvAdapter(it) {
            if (binding.numberEditText.text?.isEmpty()?.not() == true) {
                val convertedDigit =
                    (binding.numberEditText.text.toString().toDouble() / amount) * it.rate

                binding.numberEditText.setText(
                    BigDecimal(convertedDigit).setScale(
                        2,
                        RoundingMode.HALF_EVEN
                    ).toString()
                )

                binding.numberEditText.setSelection(binding.numberEditText.length())
            }
            else{
               Toast.makeText(this,getString(R.string.please_enter_amount),Toast.LENGTH_SHORT).show()
            }
        }

        binding.recyclerView.adapter = rvAdapter
    }

    private fun setSpinnerData(currencyRates: List<CurrencyRate>) {
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencyRates.map { it.name })

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinner.adapter = dataAdapter

        binding.spinner.onItemSelectedListener = this@MainActivity
    }

    private fun setUpWorker() {
        val EXPIRY_CHECK = "my_worker"
        val cons = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workRequest =
            PeriodicWorkRequest.Builder(CurrencyWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(cons).build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(EXPIRY_CHECK, ExistingPeriodicWorkPolicy.KEEP, workRequest)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        toCurrencySelected = currencyList?.get(position)?.name.toString()
        amount = currencyList?.get(position)?.rate ?: 1.0
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}


class DecimalDigitsInputFilter(digitsBeforeZero: Int, digitsAfterZero: Int) :
    InputFilter {
    private var mPattern: Pattern

    init {
        mPattern =
            Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?")
    }

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int,
    ): String? {
        val matcher = mPattern.matcher(dest)
        return if (!matcher.matches()) "" else null
    }
}

