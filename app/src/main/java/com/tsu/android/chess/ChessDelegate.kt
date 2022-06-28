package com.tsu.android.chess

import com.tsu.android.chess.data.ChessPiece
import com.tsu.android.chess.data.Square

interface ChessDelegate {
    fun pieceAt(square: Square) : ChessPiece?
    fun movePiece(from: Square, to: Square)
    fun announceWhiteWins()
    fun announceBlackWins()
    fun announceDraw()
    fun announceReset()
}