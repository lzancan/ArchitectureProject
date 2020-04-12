package com.example.architectureproject.mvvm

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.architectureproject.R
import kotlinx.android.synthetic.main.activity_m_v_v_m.*

class MVVMActivity : AppCompatActivity() {

    private val listValues: ArrayList<String> = ArrayList()
    lateinit var adapter: ArrayAdapter<String>
    lateinit var listView: ListView
    private lateinit var viewModel: CountriesViewModel

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MVVMActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m_v_v_m)
        title = "MVVM ACTIVITY"

        viewModel = ViewModelProviders.of(this).get(CountriesViewModel::class.java)

        listView = list
        adapter = ArrayAdapter(this, R.layout.row_layout, R.id.listText, listValues)
        list.adapter = adapter
        list.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                Toast.makeText(
                    this@MVVMActivity,
                    "You clicked: ${listValues[position]}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.getCountries()?.observe(this, Observer {
            if(it.isNotEmpty()) {
                listValues.clear()
                listValues.addAll(it)
                list.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                adapter.notifyDataSetChanged()
            } else{
                list.visibility = View.GONE
                retryButton.visibility = View.VISIBLE
            }
        })

        viewModel.getCountryError()?.observe(this, Observer {
            if(it){
                Toast.makeText(this, "Error, try again", Toast.LENGTH_SHORT).show()
                retryButton.visibility = View.VISIBLE
            } else{
                retryButton.visibility = View.GONE
            }
        })
    }

    fun onRetry(view: View) {
        viewModel.onRefresh()
        list.visibility = View.GONE
        retryButton.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }
}
