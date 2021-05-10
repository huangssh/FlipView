package com.hss.cn

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {

        btnPause.setOnClickListener {
            mainFuncView.pause()
        }

        btnResume.setOnClickListener {
            mainFuncView.resume()
        }
    }

}