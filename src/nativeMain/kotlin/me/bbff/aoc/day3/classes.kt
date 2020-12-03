package me.bbff.aoc.day3

import kotlin.collections.Map as KtMap

data class Slope(val x: UInt, val y: UInt)
enum class Position {
    TREE, OPEN;

    companion object {
        fun whetherTree(isTree: Boolean): Position = when (isTree) {
            true -> TREE
            else -> OPEN
        }
    }
}

data class Map(private val map: KtMap<UInt, KtMap<UInt, Position>>) {

    private val height = map.size.toUInt()

    fun getAllPositionsFor(slope: Slope): Sequence<Position> = sequence {
        var x = 0u
        var y = 0u

        while (y < height) {
            yield(getLocation(x, y))
            x += slope.x
            y += slope.y
        }
    }

    private fun getLocation(x: UInt, y: UInt): Position {
        val row = map.getValue(y)
        return row.getValue(x % row.size.toUInt())
    }

    companion object {
        fun of(str: String): Map {
            val map = str.splitToSequence('\n')
                .withIndex()
                .associate { (y, row: String) ->
                    y.toUInt() to row.withIndex().associate { (x, col) ->
                        x.toUInt() to Position.whetherTree(col == '#')
                    }
                }
            return Map(map)
        }
    }
}
