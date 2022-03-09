package com.imooc.hi_library

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.imooc.hi_library.demo.HiLogDemoActivity

class MainActivity : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.tv_hilog -> {
                startActivity(Intent(this, HiLogDemoActivity::class.java));
            }
        }
    }
}