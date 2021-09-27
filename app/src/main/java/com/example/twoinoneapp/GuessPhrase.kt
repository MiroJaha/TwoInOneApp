package com.example.twoinoneapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class GuessPhrase : AppCompatActivity() {

    private lateinit var phraseView : TextView
    private lateinit var highestScoreView : TextView
    private lateinit var note : ConstraintLayout
    private lateinit var myRV : RecyclerView
    private lateinit var charButton : Button
    private lateinit var phraseButton : Button
    private lateinit var charEntry : EditText
    private lateinit var phraseEntry : EditText

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var list : ArrayList<String>
    private lateinit var noRepeat : ArrayList<String>
    private lateinit var phrase : String
    private var countGussiesPhrase = 10
    private var countGussiesChar = 10
    private lateinit var stars : CharArray
    private lateinit var phraseString : String
    private var highestScore : Int = 0


    private lateinit var item3 : MenuItem

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        if (menu != null) {
            item3 = menu.add("Main Menu")
        }
        return true
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item1 : MenuItem = menu!!.getItem(0)
        val item2 : MenuItem = menu!!.getItem(1)

        item1.title="NEW GAME"
        item2.title="Number Game"
        return super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.numberGameMenu -> {
                recreate()
                return true
            }
            R.id.guessThePhraseMenu -> {
                startActivity(Intent(this, NumberGame::class.java))
                return true
            }
            item3.itemId -> {
                startActivity(Intent(this, MainActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun show(str:ArrayList<String>){

        myRV.adapter = RecyclerViewAdapterPhrase(str)
        myRV.layoutManager = LinearLayoutManager(this)
        if(str.size!=0)
            myRV.smoothScrollToPosition(str.size - 1)

    }


    private fun saveNewChar(){
        phraseString="Phrase: "
        for(i in stars){
            phraseString += i
        }
        phraseView.text=phraseString
    }


    private fun playAgain(){

        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage("The Correct Phrase was $phrase \nWould You Like To Play Again:")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ -> this.recreate() }
        //.setNegativeButton("No"){dialog,_ -> dialog.cancel()}

        val alert=dialogBuilder.create()
        alert.setTitle("Game Over!!")
        alert.show()

    }


    private fun killButton(button: Button, edit: EditText){
        button.isEnabled = false
        button.isClickable = false
        edit.isEnabled = false
        edit.isClickable = false
    }


    private fun callData(){
        highestScore = sharedPreferences.getInt("HQ",0)!!
    }

    private fun saveData(){
        with(sharedPreferences.edit()) {
            putInt("HQ",highestScore)
            apply()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.guessphrase)

        this.title = "Guess The Phrase"

        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        callData()

        phraseView = findViewById(R.id.phraseHide)
        highestScoreView = findViewById(R.id.highestH)
        note = findViewById(R.id.mainL)
        charButton = findViewById(R.id.CharButton)
        phraseButton = findViewById(R.id.PhraseButton)
        charEntry = findViewById(R.id.CharEntry)
        phraseEntry = findViewById(R.id.PhraseEntry)
        myRV = findViewById(R.id.rvMain)

        highestScoreView.text="The Highest Score is $highestScore out of 10"

        val phraseList= listOf(
            "UP IN THE AIR",
            "KILL TWO BIRDS WITH ONE STONE",
            "PIECE OF CAKE",
            "BREAK A LEG"
        )

        phrase=phraseList[Random.nextInt(phraseList.size)]
        stars=phrase.toCharArray()
        phraseString="Phrase: "
        for(i in stars){
            if(i != ' ') {
                stars[stars.indexOf(i)] = '*'
                phraseString+='*'
            }
            else {
                stars[stars.indexOf(i)] = ' '
                phraseString+=' '
            }
        }


        list = arrayListOf()
        noRepeat = arrayListOf()

        phraseView.text=phraseString

        charButton.setOnClickListener {
            if(charEntry.text.isNotBlank()){
                if (charEntry.text.toString().uppercase() !in noRepeat) {
                    var index=0
                    for(i in phrase) {
                        if (i.toString() == charEntry.text.toString().uppercase())
                            stars[index] = i
                        index++
                    }

                    saveNewChar()
                    if(phrase.contains(charEntry.text.toString().uppercase())) {
                        list.add("You Guessed ${charEntry.text.toString().uppercase()} Correct")
                        show(list)
                    }
                    else{
                        countGussiesChar--
                        list.add("You Guessed ${charEntry.text.toString().uppercase()} Wrong\n" +
                                "$countGussiesChar Character Guesses Remaining")
                        show(list)

                    }
                    noRepeat.add(charEntry.text.toString().uppercase())
                    charEntry.text=null
                }
                else
                {
                    Snackbar.make(note, "Please Don't Repeat the Litter", Snackbar.LENGTH_LONG).show()
                    charEntry.text=null
                }
            }
            else {
                Snackbar.make(note, "Please Enter Valid Value", Snackbar.LENGTH_LONG).show()
                charEntry.text=null
            }
            if(countGussiesChar<=0&&countGussiesPhrase<=0||!stars.contains('*')) {
                if(highestScore<(countGussiesChar+countGussiesPhrase)/2)
                    highestScore=(countGussiesChar+countGussiesPhrase)/2
                saveData()
                playAgain()
            }
            if(countGussiesChar<=0)
                killButton(charButton,charEntry)
        }

        phraseButton.setOnClickListener {
            if(phraseEntry.text.isNotBlank()){
                var index=0
                if (phrase == phraseEntry.text.toString().uppercase()) {
                    list.add("You Guessed ${phraseEntry.text.toString().uppercase()} Correct")
                    show(list)
                    for(i in phrase)
                        stars[index++]=i
                }
                else{
                    countGussiesPhrase--
                    list.add("You Guessed ${phraseEntry.text.toString().uppercase()} Wrong\n" +
                            "$countGussiesPhrase Phrase Guesses Remaining")
                    show(list)

                }
                phraseEntry.text=null
            }
            else {
                Snackbar.make(note, "Please Enter Valid Value", Snackbar.LENGTH_LONG).show()
                phraseEntry.text=null
            }
            if(countGussiesChar<=0&&countGussiesPhrase<=0||!stars.contains('*')) {
                if(highestScore<(countGussiesChar+countGussiesPhrase)/2)
                    highestScore=(countGussiesChar+countGussiesPhrase)/2
                saveData()
                playAgain()
            }
            if(countGussiesPhrase<=0)
                killButton(phraseButton,phraseEntry)
        }

    }


}