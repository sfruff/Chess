package com.tsu.android.chess

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.tsu.android.chess.data.ChessPiece
import com.tsu.android.chess.data.Square

const val BOARD_SIZE = 3

class MainActivity : AppCompatActivity(), ChessDelegate {

    private lateinit var chessView: ChessView
    private lateinit var resetButton: Button
    private lateinit var whiteGivesUp: Button
    private lateinit var blackGivesUp: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chessView = findViewById(R.id.chess_view)
        resetButton = findViewById(R.id.reset_button)
        whiteGivesUp = findViewById(R.id.white_give_up)
        blackGivesUp = findViewById(R.id.black_give_up)
        chessView.chessDelegate = this

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
}