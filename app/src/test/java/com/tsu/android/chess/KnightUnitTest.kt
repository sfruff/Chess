package com.tsu.android.chess

import com.tsu.android.chess.data.ChessPiece
import com.tsu.android.chess.data.Chessman
import com.tsu.android.chess.data.Player
import com.tsu.android.chess.data.Square
import org.junit.Test
import org.junit.Assert.*

class KnightUnitTest {

    @Test
    fun canKnightMove_singlePiece() {
        ChessGame.clear()
        ChessGame.addPiece(ChessPiece(3, 3, Player.WHITE, Chessman.KNIGHT, -1))
        println(ChessGame)
        assertTrue(ChessGame.canMove(Square(3, 3), Square(5, 4)))
        assertTrue(ChessGame.canMove(Square(3, 3), Square(4, 5)))
    }
}