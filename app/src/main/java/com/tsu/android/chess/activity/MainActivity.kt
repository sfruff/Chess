package com.tsu.android.chess.activity

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.tsu.android.chess.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val multiplayer = findViewById<Button>(R.id.Multiplayer)
        val startSound = MediaPlayer.create(this, R.raw.game_start)

        multiplayer!!.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startSound.start()
            startActivity(intent)
        }
    }
}
