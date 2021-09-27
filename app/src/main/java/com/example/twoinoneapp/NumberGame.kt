package com.example.twoinoneapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class NumberGame : AppCompatActivity(){

    private var randomNumber= Random.nextInt(11)
    private lateinit var note : ConstraintLayout
    private lateinit var myRV : RecyclerView
    private lateinit var button : Button
    private lateinit var entry : EditText

    private var countGussies = 4
    private lateinit var list : ArrayList<String>

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
        item2.title="GUESS THE PHRASE"
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.numberGameMenu -> {
                recreate()
                return true
            }
            R.id.guessThePhraseMenu -> {
                startActivity(Intent(this, GuessPhrase::class.java))
                return true
            }
            item3.itemId -> {
                startActivity(Intent(this, MainActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun playAgain(){

        val dialogBuilder = AlertDialog.Builder(this)

        dialogBuilder.setMessage("The Correct Answer was $randomNumber \nWould You Like To Play Again:")
            .setCancelable(false)
            .setPositiveButton("Yes") { _, _ -> this.recreate() }
        //.setNegativeButton("No"){dialog,_ -> dialog.cancel()}

        val alert=dialogBuilder.create()
        alert.setTitle("Game Over!!")
        alert.show()

    }


    private fun show(str:ArrayList<String>){
        myRV.adapter = RecyclerViewAdapterNumber(str)
        myRV.layoutManager = LinearLayoutManager(this)
        if(str.size!=0)
            myRV.smoothScrollToPosition(str.size-1)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.numbergame)

        this.title = "Number Game"

        note=findViewById(R.id.mainL)
        myRV=findViewById(R.id.rvMain)
        button=findViewById(R.id.BGuss)
        entry=findViewById(R.id.Entry)
        list= arrayListOf()

        button.setOnClickListener {
            try {
                val number= entry.text.toString().toInt()

                if(number<0||number>10){
                    Snackbar.make(note, "Please Enter Number between 0 and 10 Only", Snackbar.LENGTH_LONG).show()
                    entry.text=null
                }
                else {
                    if (number == randomNumber) {
                        list.add("Congratulation Your Guss was Correct!!")
                        countGussies = 0
                    } else {
                        countGussies--
                        list.add(
                            "Sorry $number is Wrong Guss\nYou Guessed ${4 - countGussies} Times\n" +
                                    "You Have $countGussies Gussies Left")
                    }

                    show(list)

                    entry.text = null
                }
                if(countGussies<=0)
                    playAgain()
            }
            catch (e:Exception) {
                Snackbar.make(note, "Please Enter Valid Number", Snackbar.LENGTH_LONG).show()
                entry.text=null
            }
        }
    }


}