package com.example.architectureproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.architectureproject.mvc.MVCActivity
import com.example.architectureproject.mvp.MVPActivity
import com.example.architectureproject.mvvm.MVVMActivity

class ArchitecturesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_architectures)
    }

    fun onMVC(view: View){
        startActivity(MVCActivity.getIntent(this))
    }

    fun onMVP(view: View){
        startActivity(MVPActivity.getIntent(this))
    }

    fun onMVVM(view: View){
        startActivity(MVVMActivity.getIntent(this))
    }
}
