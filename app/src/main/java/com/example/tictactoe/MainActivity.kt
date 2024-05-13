package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe.databinding.ActivityMainBinding

// MainActivity handles the main gameplay logic and user interface for the Tic Tac Toe game.
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val combinationList = listOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8),
        intArrayOf(2, 4, 6),
        intArrayOf(0, 4, 8)
    )
    private var boxPositions = IntArray(9) { 0 } // Represents the positions of Xs and Os on the game board.
    private var playerTurn = 1 // Tracks the current player's turn (1 for Player One, 2 for Player Two).
    private var totalSelectedBoxes = 1 // Tracks the total number of boxes selected.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve player names from the previous activity
        val getPlayerOneName = intent.getStringExtra("playerOne")
        val getPlayerTwoName = intent.getStringExtra("playerTwo")

        // Set player names on the UI
        binding.playerOneName.text = getPlayerOneName
        binding.playerTwoName.text = getPlayerTwoName

        // Set up click listeners for each image view representing a box on the game board
        setupClickListeners()
    }

    // Set up click listeners for each box on the game board
    private fun setupClickListeners() {
        val imageViews = listOf(
            binding.image1, binding.image2, binding.image3,
            binding.image4, binding.image5, binding.image6,
            binding.image7, binding.image8, binding.image9
        )

        // Iterate through each image view and set a click listener
        imageViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                // Check if the box is selectable
                if (isBoxSelectable(index)) {
                    // Perform the action when the box is selected
                    performAction(imageView, index)
                }
            }
        }
    }

    // Perform an action when a box is selected
    private fun performAction(imageView: ImageView, selectedBoxPosition: Int) {
        // Update the box position with the current player's turn
        boxPositions[selectedBoxPosition] = playerTurn

        // Set the image resource based on the current player's turn (X or O)
        imageView.setImageResource(if (playerTurn == 1) R.drawable.ximage else R.drawable.oimage)

        // Check for game results
        if (checkResults()) {
            // If there is a winner, display the result dialog
            val winnerName = if (playerTurn == 1) binding.playerOneName.text else binding.playerTwoName.text
            val resultDialog = ResultDialog(this@MainActivity, "$winnerName is the Winner!", this@MainActivity)
            resultDialog.setCancelable(false)
            resultDialog.show()
        } else if (totalSelectedBoxes == 9) {
            // If all boxes are selected and there is no winner, it's a draw
            val resultDialog = ResultDialog(this@MainActivity, "Match Draw", this@MainActivity)
            resultDialog.setCancelable(false)
            resultDialog.show()
        } else {
            // Switch to the next player's turn
            playerTurn = if (playerTurn == 1) 2 else 1
            // Increment the total number of selected boxes
            totalSelectedBoxes++
            // Update the UI to reflect the current player's turn
            changePlayerTurn(playerTurn)
        }
    }

    // Change the UI to reflect the current player's turn
    private fun changePlayerTurn(currentPlayerTurn: Int) {
        val playerOneLayout = binding.playerOneLayout
        val playerTwoLayout = binding.playerTwoLayout

        if (currentPlayerTurn == 1) {
            // Player One's turn
            playerOneLayout.setBackgroundResource(R.drawable.black_border)
            playerTwoLayout.setBackgroundResource(R.drawable.white_box)
        } else {
            // Player Two's turn
            playerOneLayout.setBackgroundResource(R.drawable.white_box)
            playerTwoLayout.setBackgroundResource(R.drawable.black_border)
        }
    }

    // Check for game results (win or draw)
    private fun checkResults(): Boolean {
        // Iterate through each winning combination
        combinationList.forEach { combination ->
            // Check if any combination matches the current player's positions
            if (boxPositions[combination[0]] == playerTurn &&
                boxPositions[combination[1]] == playerTurn &&
                boxPositions[combination[2]] == playerTurn
            ) {
                return true // Return true if there is a match
            }
        }
        return false // Return false if no match is found
    }

    // Check if a box is selectable (not already selected)
    private fun isBoxSelectable(boxPosition: Int): Boolean {
        return boxPositions[boxPosition] == 0 // Return true if the box is not already selected
    }

    // Restart the match by resetting all game variables and clearing the game board
    fun restartMatch() {
        boxPositions = IntArray(9) { 0 } // Reset box positions
        playerTurn = 1 // Reset player turn to Player One
        totalSelectedBoxes = 1 // Reset total selected boxes

        // Iterate through each image view representing a box and set it to the default white box
        val imageViews = listOf(
            binding.image1, binding.image2, binding.image3,
            binding.image4, binding.image5, binding.image6,
            binding.image7, binding.image8, binding.image9
        )

        // Set each image view to the default white box
        imageViews.forEach { imageView ->
            imageView.setImageResource(R.drawable.white_box)
        }
    }
}
