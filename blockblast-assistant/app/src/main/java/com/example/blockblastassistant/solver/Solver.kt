package com.example.blockblastassistant.solver

/**
 * A small heuristic solver for Block Blast classic 8x8.
 * - Tries all placements for the current 3 pieces in any order (no rotations allowed here; define shapes accordingly)
 * - Scores by lines cleared + board smoothness (fewer holes / fragmented regions)
 */
object Solver {
    data class Placement(val pieceIndex: Int, val r: Int, val c: Int)
    data class Result(val score: Int, val placements: List<Placement>, val resulting: BoardState)

    fun best(board: BoardState, pieces: List<Piece>): Result? {
        var best: Result? = null
        fun placePiece(b: BoardState, p: Piece, r: Int, c: Int): BoardState? {
            // bounds
            if (r + p.height > 8 || c + p.width > 8) return null
            // collision
            for ((dr, dc) in p.blocks) {
                if (b.get(r + dr, c + dc)) return null
            }
            val nb = b.cloneState()
            for ((dr, dc) in p.blocks) nb.set(r + dr, c + dc, true)
            // clear lines
            clearLines(nb)
            return nb
        }
        fun score(b: BoardState): Int {
            var fullLines = 0
            for (r in 0 until 8) if ((0 until 8).all { b.get(r, it) }) fullLines++
            for (c in 0 until 8) if ((0 until 8).all { b.get(it, c) }) fullLines++
            // Smoothness: prefer lower occupancy in upper rows to avoid choke
            var penalty = 0
            for (r in 0 until 8) for (c in 0 until 8) if (b.get(r, c)) penalty += r
            return fullLines * 100 - penalty
        }
        fun permute(idx: Int, b: BoardState, used: BooleanArray, acc: MutableList<Placement>) {
            if (idx == pieces.size) {
                val sc = score(b)
                if (best == null || sc > best!!.score) best = Result(sc, acc.toList(), b)
                return
            }
            for (i in pieces.indices) if (!used[i]) {
                used[i] = true
                val p = pieces[i]
                var placed = false
                for (r in 0 until 8) {
                    for (c in 0 until 8) {
                        val nb = placePiece(b, p, r, c)
                        if (nb != null) {
                            placed = true
                            acc.add(Placement(i, r, c))
                            permute(idx + 1, nb, used, acc)
                            acc.removeAt(acc.lastIndex)
                        }
                    }
                }
                if (!placed) {
                    // allow skipping unplaceable piece
                    permute(idx + 1, b, used, acc)
                }
                used[i] = false
            }
        }
        permute(0, board, BooleanArray(pieces.size), mutableListOf())
        return best
    }

    private fun clearLines(b: BoardState) {
        // clear full rows
        for (r in 0 until 8) if ((0 until 8).all { b.get(r, it) }) {
            for (c in 0 until 8) b.set(r, c, false)
        }
        // clear full cols
        for (c in 0 until 8) if ((0 until 8).all { b.get(it, c) }) {
            for (r in 0 until 8) b.set(r, c, false)
        }
    }
}
