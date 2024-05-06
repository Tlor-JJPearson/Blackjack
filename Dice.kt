package com.example.dicerolling

import java.util.*


class Dice(val numSides: Int = 52) {
    fun roll(isRangesRandom: Boolean = true): Int{
        if(isRangesRandom){
            return (1..numSides).random()
        }else{
            return Random().nextInt(numSides) + 1
        }
    }
}