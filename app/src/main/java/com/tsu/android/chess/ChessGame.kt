package com.tsu.android.chess

import android.widget.Toast
import com.tsu.android.chess.activity.BOARD_SIZE
import com.tsu.android.chess.data.ChessPiece
import com.tsu.android.chess.data.Chessman
import com.tsu.android.chess.data.Player
import com.tsu.android.chess.data.Square

object ChessGame {

    var whiteWinCount = 0
    var blackWinCount = 0

    var chessDelegate : ChessDelegate? = null
    private var turn: Player = Player.WHITE
    private var piecesBox = mutableSetOf<ChessPiece>()

    init {
        reset()
    }

    fun updateOnMove() {
        nextTurn()
        checkForResults()
    }

    fun clear() {
        piecesBox.clear()
        turn = Player.WHITE
    }

    fun reset() {
        whiteWinCount = 0
        blackWinCount = 0
        chessDelegate?.announceReset()
        resetPieces()
    }

    fun whiteWins() {
        whiteWinCount++
        resetPieces()
        chessDelegate?.announceWhiteWins()
    }

    fun blackWins() {
        blackWinCount++
        resetPieces()
        chessDelegate?.announceBlackWins()
    }

    fun resetPieces() {
        clear()
        for (i in 0 until BOARD_SIZE) {
            addPiece(
                ChessPiece(
                    i,
                    0,
                    Player.WHITE,
                    Chessman.PAWN,
                    R.drawable.pawn_white
                )
            )
            addPiece(
                ChessPiece(
                    i,
                    BOARD_SIZE - 1,
                    Player.BLACK,
                    Chessman.PAWN,
                    R.drawable.pawn_black
                )
            )
        }
    }

    fun addPiece(piece: ChessPiece) {
        piecesBox.add(piece)
    }

    fun pieceAt(square: Square): ChessPiece? {
        return pieceAt(square.col, square.row)
    }

    fun canMove(from: Square, to: Square): Boolean {
        if (from.col == to.col && from.row == to.row) {
            return false
        }
        pieceAt(from) ?: return false
        if(pieceAt(from)?.player != turn) return false
        return canPawnMove(from, to)
    }

    fun movePiece(from: Square, to: Square) {
        if (canMove(from, to)) {
            movePiece(from.col, from.row, to.col, to.row)
            updateOnMove()
        }
    }

    private fun movePiece(fromCol: Int, fromRow: Int, toCol: Int, toRow: Int) {
        if (fromCol == toCol && fromRow == toRow) return
        val movingPiece = pieceAt(fromCol, fromRow) ?: return

        pieceAt(toCol, toRow)?.let {
            if (it.player == movingPiece.player) {
                return
            }
            piecesBox.remove(it)
        }

        piecesBox.remove(movingPiece)
        addPiece(movingPiece.copy(col = toCol, row = toRow))
    }

    private fun pieceAt(col: Int, row: Int): ChessPiece? {
        for (piece in piecesBox) {
            if (col == piece.col && row == piece.row) {
                return piece
            }
        }
        return null
    }


    //**Utility**\\
    private fun canPawnMove(from: Square, to: Square): Boolean =
        ((pieceAt(from)?.player == Player.WHITE && from.row + 1 == to.row) ||
                (pieceAt(from)?.player == Player.BLACK && from.row - 1 == to.row))
                &&
                (
                        (pieceAt(to) == null && from.col == to.col) ||
                                (pieceAt(to) != null && pieceAt(to)?.player != pieceAt(from)?.player
                                        && (from.col + 1 == to.col || from.col - 1 == to.col))
                        )

    private fun nextTurn() {
        if (turn == Player.WHITE) turn = Player.BLACK
        else turn = Player.WHITE
    }

    private fun draw() {
        resetPieces()
        chessDelegate?.announceDraw()
    }

    private fun checkForResults() {
        val winner: Player? = checkWhoWins()

        if (winner == Player.WHITE) {
            whiteWins()
            return
        } else if (winner == Player.BLACK){
            blackWins()
            return
        }

        if (checkForDraw()) draw()

    }

    private fun checkForDraw(): Boolean {
        piecesBox.forEach {
            if (it.player == turn) {
                if (
                    (turn == Player.WHITE && canPawnMoveToRow(it, 1))
                    || canPawnMoveToRow(it, -1)
                ) {
                    return false
                }
            }
        }
        return true
    }

    private fun checkWhoWins(): Player? {
        piecesBox.forEach {
            if (it.player == Player.WHITE && it.row == BOARD_SIZE - 1) return Player.WHITE
            else if (it.player == Player.BLACK && it.row == 0) return Player.BLACK
        }
        return null
    }

    /***
     * @param rowFromIt increment this value from current row to determine row on which you want to check if pawn can move
     */
    private fun canPawnMoveToRow(it: ChessPiece, rowFromIt: Int): Boolean {
        val col = it.col
        val row = it.row
        val currSquare = Square(col, row);

        for (incCol in -1 until 2) {
            val newCol = col + incCol
            val newRow = row + rowFromIt
            if (canPawnMove(currSquare, Square(newCol, newRow))){
                return true
            }
        }
        return false
    }

}