package com.example.tictactoe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

// This activity is responsible for getting the names of the players before starting the game.
class AddPlayers : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_players)

        // Find the EditText views for player names and the start game button
        val playerOne = findViewById<EditText>(R.id.playerOne)
        val playerTwo = findViewById<EditText>(R.id.playerTwo)
        val startGameButton = findViewById<Button>(R.id.startGameButton)

        // Set a click listener for the start game button
        startGameButton.setOnClickListener {
            // Get the player names entered by the users
            val getPlayerOneName = playerOne.text.toString()
            val getPlayerTwoName = playerTwo.text.toString()

            // Check if both player names are entered
            if (getPlayerOneName.isEmpty() || getPlayerTwoName.isEmpty()) {
                // If any player name is missing, show a toast message
                Toast.makeText(this@AddPlayers, "Please enter player name", Toast.LENGTH_SHORT).show()
            } else {
                // If both player names are entered, create an intent to start the MainActivity
                val intent = Intent(this@AddPlayers, MainActivity::class.java)
                // Pass the player names as extras with the intent
                intent.putExtra("playerOne", getPlayerOneName)
                intent.putExtra("playerTwo", getPlayerTwoName)
                // Start the MainActivity with the intent
                startActivity(intent)
            }
        }
    }
}
