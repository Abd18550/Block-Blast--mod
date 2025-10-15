package com.example.blockblastassistant.detect

import android.graphics.Bitmap
import android.graphics.Color
import com.example.blockblastassistant.solver.BoardState
import com.example.blockblastassistant.solver.Piece

/**
 * Very lightweight color-based detector for Block Blast board occupancy and pieces.
 * In practice you might want ML or template matching, but we provide a starting point.
 */
object Detector {
    data class Calibration(
        val gridLeft: Int,
        val gridTop: Int,
        val cell: Int,
        val pieceAreaTop: Int,
        val pieceAreaLeft: Int,
        val pieceAreaRight: Int,
        val pieceAreaBottom: Int,
    )

    fun detectBoard(bmp: Bitmap, cal: Calibration): BoardState {
        val board = BoardState()
        for (r in 0 until 8) for (c in 0 until 8) {
            val cx = cal.gridLeft + c * cal.cell + cal.cell / 2
            val cy = cal.gridTop + r * cal.cell + cal.cell / 2
            val color = bmp.getPixel(cx.coerceIn(0, bmp.width - 1), cy.coerceIn(0, bmp.height - 1))
            val occupied = isTileColor(color)
            board.set(r, c, occupied)
        }
        return board
    }

    // Placeholder piece detection (needs enhancement): returns empty list
    fun detectPieces(@Suppress("UNUSED_PARAMETER") bmp: Bitmap, @Suppress("UNUSED_PARAMETER") cal: Calibration): List<Piece> {
        return emptyList()
    }

    private fun isTileColor(color: Int): Boolean {
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        // Heuristic: bright yellow/green blocks in the provided screenshot
        return (r > 180 && g > 160) || (g > 150 && b < 120)
    }
}
