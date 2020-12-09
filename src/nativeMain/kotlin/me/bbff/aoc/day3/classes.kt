package me.bbff.aoc.day3

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

class Map(private val map: List<List<Position>>) {

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
        val row = map[y]
        return row[x % row.size.toUInt()]
    }

    companion object {
        fun of(str: String): Map {
            val map = str.splitToSequence('\n')
                .map { row ->
                    row.map { col -> Position.whetherTree(col == '#') }
                }.toList()
            return Map(map)
        }

        operator fun <T> List<T>.get(key: UInt): T = this[key.toInt()]
    }
}
