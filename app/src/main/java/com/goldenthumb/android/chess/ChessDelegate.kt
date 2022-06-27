package com.goldenthumb.android.chess

import com.goldenthumb.android.chess.data.ChessPiece
import com.goldenthumb.android.chess.data.Square

interface ChessDelegate {
    fun pieceAt(square: Square) : ChessPiece?
    fun movePiece(from: Square, to: Square)
}