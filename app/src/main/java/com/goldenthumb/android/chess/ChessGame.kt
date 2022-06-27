package com.goldenthumb.android.chess

import com.goldenthumb.android.chess.data.ChessPiece
import com.goldenthumb.android.chess.data.Chessman
import com.goldenthumb.android.chess.data.Player
import com.goldenthumb.android.chess.data.Square

object ChessGame {
    private var piecesBox = mutableSetOf<ChessPiece>()

    init {
        reset()
    }

    fun clear() {
        piecesBox.clear()
    }

    fun reset() {
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
        return canPawnMove(from, to)
    }

    fun movePiece(from: Square, to: Square) {
        if (canMove(from, to)) {
            movePiece(from.col, from.row, to.col, to.row)
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
                 (pieceAt(to) != null && (from.col + 1 == to.col || from.col - 1 == to.col))
             )

    //TODO:Why do we need these???
    fun pgnBoard(): String {
        var desc = " \n"
        desc += "  a b c d e f g h\n"
        for (row in (BOARD_SIZE - 1) downTo 0) {
            desc += "${row + 1}"
            desc += boardRow(row)
            desc += " ${row + 1}"
            desc += "\n"
        }
        desc += "  a b c d e f g h"

        return desc
    }

    override fun toString(): String {
        var desc = " \n"
        for (row in (BOARD_SIZE - 1) downTo 0) {
            desc += "$row"
            desc += boardRow(row)
            desc += "\n"
        }
        desc += "  0 1 2 3 4 5 6 7"

        return desc
    }

    private fun boardRow(row: Int): String {
        var desc = ""
        for (col in 0 until BOARD_SIZE) {
            desc += " "
            desc += pieceAt(col, row)?.let {
                val white = it.player == Player.WHITE
                when (it.chessman) {
                    Chessman.KING -> if (white) "k" else "K"
                    Chessman.QUEEN -> if (white) "q" else "Q"
                    Chessman.BISHOP -> if (white) "b" else "B"
                    Chessman.ROOK -> if (white) "r" else "R"
                    Chessman.KNIGHT -> if (white) "n" else "N"
                    Chessman.PAWN -> if (white) "p" else "P"
                }
            } ?: "."
        }
        return desc
    }
    //what???
}