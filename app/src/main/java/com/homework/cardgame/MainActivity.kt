package com.homework.cardgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() { 
    private var lastBackPressTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnStart = findViewById<Button>(R.id.btnStartGame)
        btnStart.setOnClickListener {
            val i = Intent(applicationContext, Game::class.java)
            startActivity(i)
        }
    }
    override fun onBackPressed(){
        if(System.currentTimeMillis() < lastBackPressTime + 1500){
            super.onBackPressed()
        }else{
            Toast.makeText(applicationContext, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        lastBackPressTime=System.currentTimeMillis()
    }

}
