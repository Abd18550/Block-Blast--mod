package com.example.blockblastassistant.solver

// 8x8 classic grid

data class BoardState(
    val cells: BooleanArray = BooleanArray(64) // row-major
) {
    fun get(r: Int, c: Int): Boolean = cells[r * 8 + c]
    fun set(r: Int, c: Int, v: Boolean) { cells[r * 8 + c] = v }
    fun cloneState(): BoardState = BoardState(cells.copyOf())
}

// Pieces as list of points from top-left origin

data class Piece(val blocks: List<Pair<Int,Int>>) {
    val width = (blocks.maxOfOrNull { it.second } ?: 0) + 1
    val height = (blocks.maxOfOrNull { it.first } ?: 0) + 1
}

// Simplified stock shapes (subset) — extend as needed
object StockPieces {
    val I3 = Piece(listOf(0 to 0, 0 to 1, 0 to 2))
    val I4 = Piece(listOf(0 to 0, 0 to 1, 0 to 2, 0 to 3))
    val O2 = Piece(listOf(0 to 0, 0 to 1, 1 to 0, 1 to 1))
    val L3 = Piece(listOf(0 to 0, 1 to 0, 2 to 0, 2 to 1))
}
