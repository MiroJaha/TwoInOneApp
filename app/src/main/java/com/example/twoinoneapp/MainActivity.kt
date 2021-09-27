package com.example.twoinoneapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var mainLayOut : ConstraintLayout

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.numberGameMenu -> {
                val intent = Intent(this, NumberGame::class.java)
                startActivity(intent)
                return true
            }
            R.id.guessThePhraseMenu -> {
                startActivity(Intent(this, GuessPhrase::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainLayOut =findViewById(R.id.mainL)

        val numberGameB=findViewById<Button>(R.id.numberButton)
        val phraseGameB=findViewById<Button>(R.id.PhraseButton)

        numberGameB.setOnClickListener {
            startActivity(Intent(this, NumberGame::class.java))
        }

        phraseGameB.setOnClickListener {
            startActivity(Intent(this, GuessPhrase::class.java))
        }

    }
}