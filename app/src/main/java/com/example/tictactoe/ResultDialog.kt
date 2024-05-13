package com.example.tictactoe

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull

// ResultDialog displays the result of the Tic Tac Toe game (win or draw) and provides an option to start again.
class ResultDialog(
    @NonNull context: Context,
    private val message: String,
    private val mainActivity: MainActivity
) : Dialog(context) {

    // Called when the dialog is being created.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the layout for the dialog
        setContentView(R.layout.activity_result_dialog)

        // Get reference to the message text view
        val messageText = findViewById<TextView>(R.id.messageText)
        // Get reference to the start again button
        val startAgainButton = findViewById<Button>(R.id.startAgainButton)

        // Set the message text
        messageText.text = message

        // Set a click listener for the start again button
        startAgainButton.setOnClickListener {
            // Call the restartMatch() method of the MainActivity
            mainActivity.restartMatch()
            dismiss() // Dismiss the dialog
        }
    }
}
