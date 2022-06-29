package com.tsu.android.chess.activity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tsu.android.chess.ChessDelegate
import com.tsu.android.chess.ChessGame
import com.tsu.android.chess.ChessView
import com.tsu.android.chess.R
import com.tsu.android.chess.data.ChessPiece
import com.tsu.android.chess.data.Square

var BOARD_SIZE: Int = 3

class GameActivity : AppCompatActivity(), ChessDelegate {

    private lateinit var chessView: ChessView
    private lateinit var resetButton: Button
    private lateinit var whiteGivesUp: Button
    private lateinit var blackGivesUp: Button
    private lateinit var whiteScore: TextView
    private lateinit var blackScore: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        BOARD_SIZE = intent.getIntExtra("SIZE" ,3)


        chessView = findViewById(R.id.chess_view)
        resetButton = findViewById(R.id.reset_button)
        whiteGivesUp = findViewById(R.id.white_give_up)
        blackGivesUp = findViewById(R.id.black_give_up)
        whiteScore = findViewById(R.id.white_score)
        blackScore = findViewById(R.id.black_score)

        whiteScore.text = ChessGame.whiteWinCount.toString()
        blackScore.text = ChessGame.blackWinCount.toString()

        chessView.chessDelegate = this
        ChessGame.chessDelegate = this

        resetButton.setOnClickListener {
            ChessGame.reset()
            chessView.invalidate()
        }

        whiteGivesUp.setOnClickListener{
            ChessGame.blackWins()
            chessView.invalidate()

        }

        blackGivesUp.setOnClickListener{
            ChessGame.whiteWins()
            chessView.invalidate()
        }

    }

    override fun pieceAt(square: Square): ChessPiece? = ChessGame.pieceAt(square)

    override fun movePiece(from: Square, to: Square) {
        ChessGame.movePiece(from, to)
        chessView.invalidate()
    }

    override fun announceWhiteWins() {
        popupMessage("White Wins!")
        whiteScore.text = ChessGame.whiteWinCount.toString()
    }

    override fun announceBlackWins() {
        popupMessage("Black Wins!")
        blackScore.text = ChessGame.blackWinCount.toString()
    }

    override fun announceDraw() {
        popupMessage("Draw!")
    }

    override fun announceReset() {
        popupMessage("Game reset")
        whiteScore.text = ChessGame.whiteWinCount.toString()
        blackScore.text = ChessGame.blackWinCount.toString()
    }

    fun popupMessage(message : String){
        Toast.makeText(
            this, message,
            Toast.LENGTH_SHORT
        ).show()
    }
}