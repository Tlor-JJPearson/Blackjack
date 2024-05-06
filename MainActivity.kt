package com.example.dicerolling

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(localClassName, "onCreate")
        setContentView(R.layout.activity_main)

        var score : Int = 0
        val button = findViewById<Button>(R.id.rollButton)
        val resetButton = findViewById<Button>(R.id.resetButton)

        button.setOnClickListener {
            score = rollDice(score)
            updateScore(score)
            if (score == 21) updateText(true)
            if (score > 21) updateText(false)
            if (score < 21) { score = score }
        }

        resetButton.setOnClickListener {
            score = 0
            updateScore(score)
            resetText()
        }
    }

    fun rollDice(scFull : Int): Int {
        val dice = Dice()
        var roll: Int
        var roll2: Int
        var score1: Int
        var score2: Int
        var scoreFull: Int = scFull
        roll = dice.roll()
        roll2 = dice.roll()
        while (roll2 == roll) roll2 = dice.roll()

        score1 = roll % 13 // ace to 10 are worth themselves
        score2 = roll2 % 13
        if (score1 == 1 && score2 == 1) { //two aces = INSTA WIN
            scoreFull = 21
        }
        else {
            if (score1 > 10 || score1 == 0) score1 = 10 //jacks, queens and kings are worth 10
            if (score2 > 10 || score2 == 0) score2 = 10
            if (score1 == 1) score1 = 11 //ace is worth 11
            if (score2 == 1) score2 = 11
            scoreFull += score1 + score2
            if (scoreFull > 21 && (roll % 13 == 1 || roll2 % 13 == 1)) { //if ace = 11 is too big, ace = 1
                scoreFull -= 10
            }
        }

        updateImg(roll, roll2)
        return scoreFull
    }

    fun updateScore(scoreFull: Int) {
        val scoreTxt = findViewById<TextView>(R.id.scoreView)
        scoreTxt.text = "Your Score: ${scoreFull}"
    }

    private fun updateImg(roll: Int, roll2: Int) {
        val dice1Img: ImageView = findViewById(R.id.dice1img)
        val dice2Img: ImageView = findViewById(R.id.dice2Img)
        dice1Img.setImageResource(resolveDrawable(roll))
        dice2Img.setImageResource(resolveDrawable(roll2))
    }

    fun resolveDrawable(value: Int): Int {
        return when (value) {
            1 -> R.drawable.ace_of_clubs
            2 -> R.drawable.card_2_of_clubs
            3 -> R.drawable.card_3_of_clubs
            4 -> R.drawable.card_4_of_clubs
            5 -> R.drawable.card_5_of_clubs
            6 -> R.drawable.card_6_of_clubs
            7 -> R.drawable.card_7_of_clubs
            8 -> R.drawable.card_8_of_clubs
            9 -> R.drawable.card_9_of_clubs
            10 -> R.drawable.card_10_of_clubs
            11 -> R.drawable.jack_of_clubs2
            12 -> R.drawable.queen_of_clubs2
            13 -> R.drawable.king_of_clubs2
            14 -> R.drawable.ace_of_diamonds
            15 -> R.drawable.card_2_of_diamonds
            16 -> R.drawable.card_3_of_diamonds
            17 -> R.drawable.card_4_of_diamonds
            18 -> R.drawable.card_5_of_diamonds
            19 -> R.drawable.card_6_of_diamonds
            20 -> R.drawable.card_7_of_diamonds
            21 -> R.drawable.card_8_of_diamonds
            22 -> R.drawable.card_9_of_diamonds
            23 -> R.drawable.card_10_of_diamonds
            24 -> R.drawable.jack_of_diamonds2
            25 -> R.drawable.queen_of_diamonds2
            26 -> R.drawable.king_of_diamonds2
            27 -> R.drawable.ace_of_hearts
            28 -> R.drawable.card_2_of_hearts
            29 -> R.drawable.card_3_of_hearts
            30 -> R.drawable.card_4_of_hearts
            31 -> R.drawable.card_5_of_hearts
            32 -> R.drawable.card_6_of_hearts
            33 -> R.drawable.card_7_of_hearts
            34 -> R.drawable.card_8_of_hearts
            35 -> R.drawable.card_9_of_hearts
            36 -> R.drawable.card_10_of_hearts
            37 -> R.drawable.jack_of_hearts2
            38 -> R.drawable.queen_of_hearts2
            39 -> R.drawable.king_of_hearts2
            40 -> R.drawable.ace_of_spades2
            41 -> R.drawable.card_2_of_spades
            42 -> R.drawable.card_3_of_spades
            43 -> R.drawable.card_4_of_spades
            44 -> R.drawable.card_5_of_spades
            45 -> R.drawable.card_6_of_spades
            46 -> R.drawable.card_7_of_spades
            47 -> R.drawable.card_8_of_spades
            48 -> R.drawable.card_9_of_spades
            49 -> R.drawable.card_10_of_spades
            50 -> R.drawable.jack_of_spades2
            51 -> R.drawable.queen_of_spades2
            else -> R.drawable.king_of_spades
        }
    }

    fun updateText(win: Boolean) {
        val rollResultTxt = findViewById<TextView>(R.id.rollResultText)
        if (win) {
            rollResultTxt.text = "You're a winner!"
            rollResultTxt.setTextColor(Color.rgb(0,200,0))
        } else {
            rollResultTxt.text = "You're a loser!"
            rollResultTxt.setTextColor(Color.rgb(255, 0, 0))
        }
    }

    fun resetText() {
        val text = findViewById<TextView>(R.id.rollResultText)
        text.setTextColor(Color.rgb(255,255,255))
        text.text = "Welcome to Blackjack!"
    }

    override fun onStart() {
        super.onStart()
        Log.i(localClassName, "onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.i(localClassName, "onResume")
    }
    override fun onStop() {
        super.onStop()
        Log.i(localClassName, "onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.i(localClassName, "onDestroy")
    }


}