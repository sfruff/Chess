package com.goldenthumb.android.chess

import com.goldenthumb.android.chess.data.ChessPiece
import com.goldenthumb.android.chess.data.Chessman
import com.goldenthumb.android.chess.data.Player
import com.goldenthumb.android.chess.data.Square
import org.junit.Test
import org.junit.Assert.*

class BishopUnitTest {

    @Test
    fun canBishopMove_singlePiece() {
        ChessGame.clear()
        ChessGame.addPiece(ChessPiece(3, 3, Player.WHITE, Chessman.BISHOP, -1))
        assertTrue(ChessGame.canMove(Square(3, 3), Square(7, 7)))
        assertFalse(ChessGame.canMove(Square(3, 3), Square(5, 4)))
    }

    @Test
    fun canBishopMove_blocked() {
        println(ChessGame)
        assertFalse(ChessGame.canMove(Square(2, 0), Square(5, 3)))
        assertFalse(ChessGame.canMove(Square(2, 0), Square(0, 2)))
        assertFalse(ChessGame.canMove(Square(2, 7), Square(0, 5)))
        assertFalse(ChessGame.canMove(Square(2, 7), Square(7, 2)))
    }
}