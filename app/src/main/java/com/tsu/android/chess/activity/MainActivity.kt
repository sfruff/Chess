package com.tsu.android.chess.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.tsu.android.chess.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.boardSize)

        var size : Int = textView.text.toString().toInt()

        findViewById<Button>(R.id.minus)!!.setOnClickListener {
            if(size > 3){
                size -= 1
                textView.text = size.toString()
            }
        }


        findViewById<Button>(R.id.plus)!!.setOnClickListener {
            if(size < 10){
                size += 1
                textView.text = size.toString()
            }
        }


        findViewById<Button>(R.id.Multiplayer)!!.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("SIZE", size)
            startActivity(intent)
        }
    }
}
