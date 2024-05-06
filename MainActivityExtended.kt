package com.example.dicerolling

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dicerolling.databinding.ActivityMainExtendedBinding
import com.google.android.material.snackbar.Snackbar

class MainActivityExtended : AppCompatActivity() {
    private lateinit var binding: ActivityMainExtendedBinding

    private var numDice:Int = 2 //number of dice
    private var isHoldEnabled:Boolean = true //can user hold dice?

    private val diceImgIdsArray = arrayOf(R.id.imageView1, R.id.imageView3,R.id.imageView4, R.id.imageView5, R.id.imageView6)
    private val diceStatesArray = arrayOf(false,false,false,false,false)
    private val diceValuesArray = arrayOf(1, 1, 1, 1, 1)
    private var currentPlayer = 0
    private val playerScores = arrayOf(0, 0)
    private var rollCount = 0

    private val launchSettingsActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.i(localClassName, "onActivityResult")
            if (result.resultCode == RESULT_OK) {
                result.data?.let { data ->
                    numDice = data.getIntExtra(getString(R.string.num_dice_key), 2)
                    isHoldEnabled = data.getBooleanExtra(getString(R.string.hold_enable_key), true)
                }
                applySettings()
                resetGame()
                Snackbar.make(
                    binding.root, "Current settings: NumDice: $numDice, isHoldEnabled:$isHoldEnabled", Snackbar.LENGTH_SHORT).show()

            }
        }

    private fun applySettings() {
        binding.RollButton.isEnabled = true
        resetGame()
        val diceToHideBegin = numDice + 1
        for (num in 1..5) {
            if (num in diceToHideBegin ..5)
                findViewById<ImageView>(diceImgIdsArray[num - 1]).apply {
                    visibility = View.GONE
                    isClickable = false
                    isFocusable = false
                }
            else
                findViewById<ImageView>(diceImgIdsArray[num - 1]).apply {
                    visibility = View.VISIBLE
                    isClickable = isHoldEnabled
                    isFocusable = isHoldEnabled
                }
        }
    }

    private fun resetGame() {
        currentPlayer = 0
        playerScores[0] = 0
        playerScores[1] = 0
        rollCount = 0
        binding.textView2.text = getString(R.string.click_start)
        binding.playerLabel.visibility = View.INVISIBLE
        binding.RollButton.text = getString(R.string.button_initial)
        resetTurn()
    }

    private fun resetTurn() {
        for (num in 0..4) {
            diceValuesArray[num] = 1
            diceStatesArray[num] = false
            findViewById<ImageView>(diceImgIdsArray[num]).let {
                changeDiceTint(it, false)
                it.setImageResource(resolveDrawable(1))
            }
        }
        binding.playerLabel.apply { text = getString(R.string.player_label_format, currentPlayer) }
    }

    private fun changeDiceTint(img: ImageView, highLight: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            img.imageTintList = ColorStateList.valueOf(getColor(if (highLight) R.color.yellow else R.color.white))
        } else {
            @Suppress("DEPRECATION")
            img.imageTintList = ColorStateList.valueOf(resources.getColor(if (highLight) R.color.yellow else R.color.white))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(localClassName, "onCreate")
        binding = ActivityMainExtendedBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> startSettingsActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startSettingsActivity() {
        val intent: Intent = Intent(this, SettingsActivity::class.java).apply {
            putExtra(getString(R.string.num_dice_key), numDice)
            putExtra(getString(R.string.hold_enable_key), isHoldEnabled)
        }
        launchSettingsActivity.launch(intent)
    }

    fun resolveDrawable(value: Int): Int {
        return when (value) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
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