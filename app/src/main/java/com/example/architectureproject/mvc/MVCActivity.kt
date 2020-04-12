package com.example.architectureproject.mvc

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.architectureproject.R
import kotlinx.android.synthetic.main.activity_m_v_c.*

class MVCActivity : AppCompatActivity() {

    private val listValues: ArrayList<String> = ArrayList()
    lateinit var adapter: ArrayAdapter<String>
    lateinit var listView: ListView
    private lateinit var controller: CountriesController

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, MVCActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m_v_c)
        title = "MVC ACTIVITY"
        controller = CountriesController(this)

        listView = list
        adapter = ArrayAdapter(this, R.layout.row_layout, R.id.listText, listValues)
        list.adapter = adapter
        list.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                Toast.makeText(
                    this@MVCActivity,
                    "You clicked: ${listValues[position]}",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }

    fun onError(){
        Toast.makeText(this, "Error, try again please", Toast.LENGTH_SHORT).show()
        retryButton.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    fun setValues(values: List<String>) {
        listValues.clear()
        listValues.addAll(values)
        retryButton.visibility = View.GONE
        progressBar.visibility = View.GONE
        list.visibility = View.VISIBLE
        adapter.notifyDataSetChanged()
    }

    fun onRetry(view: View){
        controller.onRefresh()
        retryButton.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }
}