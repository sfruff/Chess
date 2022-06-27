package com.goldenthumb.android.chess

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.goldenthumb.android.chess.data.ChessPiece
import com.goldenthumb.android.chess.data.Square
import java.io.PrintWriter
import java.net.ConnectException
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketException
import java.util.*
import java.util.concurrent.Executors

const val BOARD_SIZE = 8;

class MainActivity : AppCompatActivity(), ChessDelegate {

    private lateinit var chessView: ChessView
    private lateinit var resetButton: Button
    private lateinit var whiteGivesUp: Button
    private lateinit var blackGivesUp: Button
    private var printWriter: PrintWriter? = null
    private var serverSocket: ServerSocket? = null

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
            serverSocket?.close()
        }


    }

    override fun pieceAt(square: Square): ChessPiece? = ChessGame.pieceAt(square)

    override fun movePiece(from: Square, to: Square) {


        ChessGame.movePiece(from, to)
        chessView.invalidate()

        printWriter?.let {
            val moveStr = "${from.col},${from.row},${to.col},${to.row}"
            Executors.newSingleThreadExecutor().execute {
                it.println(moveStr)
            }
        }
    }
}