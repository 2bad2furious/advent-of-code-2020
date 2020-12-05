package me.bbff.aoc.day5

data class Location(val row: ULong, val column: ULong) {
    val seatId get() = seatId(row, column)

    companion object {
        fun seatId(row: ULong, col: ULong) = (row * 8u) + col
        fun parse(str: String): Location {
            val row = BitSet(7) { i -> str[i] == 'B' }.toULong()
            val column = BitSet(3) { i -> str[7 + i] == 'R' }.toULong()
            return Location(row, column)
        }
    }
}

fun part1(input: Sequence<Location> = parsedInput): ULong {
    return input.maxOf { location -> location.seatId }
}

fun part2(input: Sequence<Location> = parsedInput): ULong {
    try {
        val seats = mutableMapOf<ULong, MutableSet<ULong>>()
        for ((row, col) in input) {
            seats.getOrPut(row, ::mutableSetOf).add(col)
        }

        val rowIndices = (0uL..7uL)
        val fullRow = rowIndices.toList()

        return seats
            .asSequence()
            .filter { (_, cols) -> cols.size == 7 }
            .map { (row, cols) ->
                val missing = (fullRow - cols).single()
                Location.seatId(row, missing)
            }
            .single()
    } catch (e: Throwable) {
        e.printStackTrace()
        return 0uL
    }
}

fun BitSet.toULong(): ULong {
    var res = 0uL
    val maxIndex = size - 1
    for (i in 0 until size) {
        if (this[i])
            res += 1uL shl (maxIndex - i)
    }
    return res
}


val parsedInput get() = rawInput.splitToSequence('\n').map { Location.parse(it) }
val rawInput = """FFBFBFBRRL
FBFFBFBLRR
FFFBBFBRLR
FBBFFBFRRR
FBBBFFFLRR
FBBBFFBRRL
FBBBBFBRRL
BFBBFFBLLL
BBFFFFFRRR
BFBFFBFLRR
FBFFFBBRRR
BFBFBFBLRR
FFFBFBBRRL
BFBFFBFRRL
FBFFFBBRLR
FFBBFFBLRR
FFBFBBBLLR
FBFFBFBLLR
BBBFFFFLRR
FFBBBBBRLR
FBBBBFBLLL
FFFBFBFLRR
FFBFFFFRLL
FBBFFFFRRR
BFFFBBFRLR
BFBBFFFLRR
FBBBBFBRRR
FBFFBFBLLL
BBFBFFFRRL
FBBBFBBRRR
BBFFFFBLLL
FFFBFFBLRR
FFBFFBBRLL
BBBFFFFRLL
BFBBBBBLRR
FFBBFBBLRL
BFBFBFFLLL
FBFBBFFRLL
FFFBBFBLLR
FBBBBBFRRL
BFBFBBBRLR
FBBBFBFLLL
FBBFFFFLRL
BFBBBFBRRL
FFBBFFFRRR
BFBFBFFRLL
FBFBBBBLLR
FBFBBBFRLL
BFFBBBFLRR
BFFFBBFLLR
BFBFBBBLRR
BFBFFFBRRR
BBBFFFFLRL
FBFBBFFLRL
FBBFFBBLLR
FBBFBFBRLL
FBFFBBFRLR
BFBFBBBLLL
FBBFBBFLLL
BFBFBBBRLL
BBFFFFFRLL
BFBBFFFLLL
BFFFFFFRLR
FBFFBFFLRR
FFFBBBFRLR
BFFBBBFRRL
BFBBFBBRRL
BFFBBBBRLR
BBFBFFFRRR
FFFBFFBRLR
BFBFBBFLRL
BBFFFFBLRL
BBFFBBFRLL
FBBBFBFLRR
BFFBBBFLLL
BFBFBFBRRR
FBFFFBFRRL
FBBBFFFRLL
BFFBBFFRRL
BFFFBFBLLR
FBBFBFFLLR
BFBFBFBLLL
BBBFFFFRLR
FFBFFFFLRR
BFBFBBFRRR
FBBFBFFRLR
BFFFFBFLLL
FFBFFFFRRL
FBFBBFBRRL
FBFBBFBLRR
BFBFBFBRLL
FFBFBFFRRR
BFFFBFFRRR
FBFBFFFRRL
FFFBBFFLRR
FBBFFBFRRL
BFFFBFFRLR
BBFBBBBRLL
BFBFBBBLLR
BFFFBBFLRR
FFFBFFFLRL
BBFBFFFLLR
FFBBBFFRRR
FBBFBBFLRR
FBBFFFFLRR
FBBFBFBRRL
FFFBBBBLRL
BBFFFFBRLL
FBFBFFBRLL
BFFFFFFLLL
BFBBBBFRLL
FBBBFFBRRR
BFFFBBBRLR
FBBBBBBRRL
FBFFBFBRLL
FBBFBFBLLR
BBFFFFBRRL
BBFBBBFLLR
FBFBBFFRLR
BBFBBBFRLR
FFBFFFBLLL
FBFFFBBLRR
BBFFBFFRLL
FBFBFFFRLL
BFFFFFBRLR
FBFBBBBRLL
BBFBFFBLLL
FFBFFBFRLL
FBBBBBFRRR
BFBFBBFRLR
FFBFFBBLRR
FBFFFBBRLL
FBBFBFBLRL
FBBFBBBLLR
BBFFBBBLRL
FFFBFFBRLL
BFBBBFFRLL
BFFBFFFLRL
BFFFFFBLLR
FFBFBBBRRR
FFBFFBBLRL
BBFBBBFRLL
BFFBBBFRRR
BFBBFBFLLR
FFBFFFFRRR
FBBBFFFLLR
FFFBFBBLRR
FBBBBFBRLR
FFFFBBBRLL
BFBBFBBRLL
FFFBBBBRLR
FBFFFFFRRR
FBBBBBBRLR
FFBFBFFRLL
BBFBFFFLLL
BBFFFBFLLL
BFBFBFFRLR
BFFBBFBLLL
BFFFFFBLRR
FFBBFFBRRR
FFFBFBBRLL
FFBFBBBLRL
BFFBFFBLRL
BBFBFBBRRL
BFFBFFBRRL
BFFBBFBRLR
FBBBBBFLLL
BBFBBFBRRR
FFBBFFFRLR
FFFBFFFRLR
BFBBBFBLLR
BBFBFBFRLL
BBFBFFBRRR
FBBBBFBRLL
BFFFFBBLRR
FBFBFFFRRR
BFBFBFBLLR
BFBBFFBLRR
FFBFBFBLRR
FFBFBFBLLR
FFBBBFFLRR
FBFBBBBRRR
FBFFBBBLRL
BBFBBFFLLL
BFFFBFFLRL
BFBFFBFRRR
BFFBFBBRRR
FBBBBBFLRR
BFBFFFFLRR
FFBBFFFRRL
FBBFFFFLLL
FBBBFBBLLR
BBFFFBFRRR
BFBFFFFRLL
BBFFFFBLLR
FBBBBBFRLR
FBFFBBBRRR
BFBBBBBLLR
BFFFBBBLRL
BFFBFFBLLR
BFFBFFFLLL
FFBFFFBRLR
FBBFFBFRLL
FBFFBBFRRL
FFFBBFBLLL
FFFBFFFRLL
BFFFFBFLLR
FBFFFFFRLL
FFBBBBBLLL
FBFFFBBLRL
FBBFFBBRLL
BFBBBBFRRR
BFBBBBFLLR
BBFBBBFLLL
BFFBFBBRLR
FBBBBBBLRR
BFBFFFFLLR
BFBFBFFLRR
FBBFBFBLRR
BFBFBBBRRL
BBBFFFBLLL
FFBBBFBRRL
FFBBBFBRLR
BFBBBFBRLR
BFBBFFBLRL
FBFFBFFLRL
BFBBFBBRLR
FFBFFBFLRL
BFFBBBFRLL
FBFBFBFLLL
BFBBBFFLRR
FBFBBBBRRL
BFFBFBBRLL
BBFBBFFLRR
FBFFBFFLLL
FFBBBFBLLR
BBFFFFFLLL
FBFBFBBRLL
FFBFFBFRLR
BFBBFBFRLL
FFFBFFFLLL
FBBBFBFLLR
FBFFFBBLLL
BFFFBFFRLL
FBBFFFFRRL
FFFBFBFRRR
FBFFFFFLLL
BFBFFBFLLL
BFFBBFBRRL
BFFBBBBRLL
FBFFFFFLLR
BBFBBFBRLR
BBFBFBBRLL
BBBFFFFRRL
FFFBBFBRRR
FBBBBFBLRL
FFBBBFFRLL
BFBBFBFRRL
FBBBBFFRRR
FBFBBBBLRL
BBFBFBFRRL
BBFFBFBLLR
FFFFBBBRLR
FFBBFBFRRR
BBFBFBFLLL
BBFFFBBLLL
FBBBBFFLRL
BBFFBBFLRR
BFFFFFFRLL
BBFFBFBLRL
FFBBBBFRLL
BFBFFBBRLL
FBBBFFBLLL
BFFBFFBLLL
FFFFBBBRRR
FFFBBFFLLR
BFBFFFBLRL
FBFBBBFLRR
FBBFBFBRRR
BFFFFFBRRR
BBFFBBFRRR
BFFFFBFRLL
FFBFBBFLLL
FBFFBBFLLR
BFFBBFBRRR
BBFFBFFRLR
FBBBBBBLLR
BFBFBFBRLR
BBFBBFBLRR
FFFBFBBLLL
FBFBFFFRLR
BFBFFBBRRL
FFBBFBBLRR
BBFBFFBLLR
BFFBFBBLRL
FBFFBFBRRL
FFBBFFBLLL
BFBFFFFRLR
FBFFFBFRLL
FBBBFBBRLL
FFFBBBFLLL
FBBFBBBLRR
FFBFFBBRRL
FFFBFBBRLR
FBFFBBBRRL
BBFBFBFRRR
BFFBFBFLLR
FBFFFBFRLR
BBFBBBBLRL
FFFBBFFRRL
FBFBFBFLLR
FFBBBBFRRR
BFFFBFBLRL
BFBBBFBLRR
BFFBBFBLRL
FFBBFBFRRL
FFBBBBFLLR
BFFBFFBLRR
FBBFFBBLLL
FBBBBBFRLL
FBFBFBBLLL
FBBBBFFRLR
BBFFFFBLRR
BFFBBBBLRL
BFFBFBFRLR
FFBBBBFRLR
FBBBFFFLLL
BBBFFFFRRR
BBFBFBFLLR
FBBFBBBLLL
BBFFBFFRRL
BFFFBFBRLL
FFFBFBFLLL
FBBBBBFLLR
FFBBBBFRRL
BFFFBBFRLL
FFBFFFBLLR
FFBFBFBRLR
FFBFFBFLRR
FBBFBFFRRL
FBFFFBFLRL
BFBBBFFLRL
BFFFBBFRRR
BBFFFFBRLR
FBFFBBBLRR
BBFFBBBRRL
FBFBFBBRRR
FFBBBFBRRR
BBFFFFFLLR
FBBFBBFRLL
BFFFFFFRRR
BBFBFFFRLL
FBFBBFFRRL
FFFBFFBLLR
BFBBFFFLLR
FBBFFBBRLR
BFFBFBFLRL
FFFBBFFLRL
BFBFFFFRRR
BFFBFFFRRR
FBBFBFFRLL
BBFFBBFRLR
BFBBBBBLRL
FFBBFFFLRL
BFFBFFBRRR
FBBBFFBLLR
BBFBFFFLRL
FBFFBBFLLL
FFFBBFFRRR
FFFBFBFRLR
FFBBFBFRLR
BFBFFFFLLL
FBFBBFFLRR
BFFFBBFRRL
FBBFBBFRLR
FFFBFBFRLL
BFFFBFBRLR
BBFFFBFRLL
FBBBFBBRRL
BBFBBFFRRR
FFFBFFFRRL
BBFBBFFLLR
BBFFBBFRRL
FFBBFFBLLR
BBFFBFBRLL
FBBBFBBLRR
BFFBFFBRLR
BFFBBBBLLR
FFFBBFFLLL
BFFFBFFLLL
BBFFBFBRLR
FBFBFBFLRL
FBBBBFFRRL
BBFFFBBLRR
FFFBFBBRRR
BFBBFBFRLR
BFBBFBFLLL
FFFBFBBLLR
BFBBFFFRLL
FBBFFFFRLL
BFFFBFBLRR
FFBFFBBRRR
FFFBBFFRLL
BFFFFBFRRL
BBFBBFBRRL
BFBFFBBRRR
FBFFBFFRLR
BFFBFBFRLL
BFBFBBBLRL
FFFBBFFRLR
BFFFFFBLLL
FFFBFFFLRR
FBBFFBFLRR
FBFBBFFLLL
BFFBFBBLRR
BFFFFFFLRL
FFFBFFBLRL
BFFBBBBLLL
BFFFFFBRLL
BBFFBFFLRR
BFFBBFBLRR
BFFFBFFLLR
BBFFBFBLRR
BFBBBBBRRL
FBFBFFFLRR
FBFBBFFRRR
FBFFBBFLRL
FBFFFFBRRL
FFBBBBBLRR
BFFFFBFLRR
BBFBBBFRRR
FFBBBFBRLL
FBBFFFFLLR
BFBBBBFLRR
FFBFFFBRRL
BFBBFBBLRL
FFBBFBBRRR
FBBBFBFRLL
FBFBBBFLLL
BFFFFBBLRL
BBFBFBBLRR
FFFBFBFRRL
FBBFFFBRLL
BFFFFFFRRL
FBFFFBFLLL
BFBBBBBRLR
BBFBBBFLRR
FBBBFFBLRR
BBFFBBBLRR
FFFBFBFLLR
FBBBFBFRRL
BFFFBBFLRL
FFFBBBBLRR
BFBBBBFRLR
BBFBFBBLLR
BFBFBBFLLR
FBBBBBBLRL
BFBFFBBLLL
BFFFBFFRRL
BFFBBFFRRR
BBFFFBBRLL
FBBBBFFLLL
BFFFFFFLRR
BBFBBBFRRL
BFBBFBFRRR
BFBFBFBRRL
BFBFFFBLRR
FBBFBBBRLR
FBFBBFBRLL
FBFBFFBRRR
BFBBFBFLRR
BFBBFBBRRR
BFBBFFFLRL
FBFFFFFRLR
BBFFBBBLLL
BFBBFFBRLL
FBBBFFFRLR
FFBBFBBRLL
FBFBBBBRLR
BBFFBFFLLL
BBFFBBBRLL
FBFFFFBRLL
FFBBFBBLLL
FFBFFFFLRL
BBFBBFBLLR
FBFBFBBLRR
FFBBFFFLLL
BBFBFBBLLL
BFBFFFFRRL
FFBFBFFLRL
BFFBFFFRRL
FBFFFFFRRL
BBFBFBFRLR
FFBBBFBLRL
BFBBFFBRRR
BFBBBBBRRR
BBFFBFFLLR
FBBFFBBRRL
BFBFFFBRLR
FFFBBBFRLL
FFFBBBBRLL
FFBBBBBRRR
BBFBBBBLRR
FBFBBBBLRR
FBBFFBFRLR
BFFFFBFLRL
BFBBFBBLLL
FBBFBBBRLL
FFBBFBFLRR
BFBFBBBRRR
FFBFBFFRRL
BFFFFBFRRR
FBFBBFFLLR
BBFBBFFLRL
FBFFBBBRLL
FFBBFBBRRL
FFBFBFBLRL
BBFFBFBRRL
FFFBBBFLLR
FBFFBFFRRL
FFBFBFFLRR
BFFBBFFRLR
FFBFBFFRLR
BBFBFBBRRR
FBFBBBFLRL
FBFFBBFRRR
BFBFBFFLRL
FBBBFFBRLL
BFBFFBFRLR
BBFBFBBRLR
FBFBFFFLLL
FBFFFBBLLR
FBFFBFFRRR
FFFBBFBLRR
FFBFBFFLLL
BFFBBFBRLL
BFBFFFBLLR
BFFBBBFLLR
FBBFBFBRLR
FBBBFBFLRL
FBFFBBBRLR
FFBBBFFLRL
FFFBBBFLRL
BFFBBBFLRL
FFBBFFFLRR
FFBBBBFLLL
BFFBFFFRLR
FFBBBFBLRR
FFBBBFFRLR
BFFBBFFLRL
FBBBFFFLRL
BFFFBBBLRR
FFFFBBBLRR
BBFFFFBRRR
FBBFFFBLLR
BFBFFBFLRL
BFBBFFBRLR
BFBBBBFRRL
BFBFFBBLRL
FBBBBFFRLL
BFFFFBBLLL
FBBBFFFRRL
FFBBFBBLLR
BFBBBFFLLR
FBFBFBBLLR
FFBFFBFRRL
BBFBBFFRRL
BFBBBBBLLL
FBFBBFBLLL
BBFBFFBLRR
FBBFFBFLRL
BFFBFBBLLR
FFBFBBFLRR
BBFFBBBRLR
FFFBBBFRRL
BFFBBBFRLR
FBBBFBBLLL
FFBFFFFLLR
FBBFFFBRRL
BBFFBBFLLR
BFBBFBBLRR
FFFBFFBRRL
FFBFBBBRLR
FBBFFFBRLR
BFBFBFFRRR
BBFFFBBRLR
FBBBBFBLLR
BFBBBBBRLL
FBFBFBBRRL
FBBFFBBRRR
BBFFFBFLRL
FBBBBFFLRR
FBBFBFFLLL
FBBBFFBLRL
BFBBBFFRLR
BFBBFFBRRL
FFBFBBBLRR
BFFBFBFLLL
BFFFFBBRLR
BFFBFBFRRR
BFFFBBBRRL
FBFBBBFRLR
FBBBFBBRLR
FBFFFFFLRR
BFFFFBFRLR
BFBFFBFRLL
BBFBBFFRLL
FBBBFBFRLR
BBFBBBBRRL
BFBBBFBRLL
BBFFFFFLRR
FBFFFFBRLR
BFBFBFFRRL
FBFFBBBLLR
BBFFFBBRRR
BFBFBFFLLR
BBFFBBFLRL
BFBBFFBLLR
FFFBBFBRRL
BFFBBBBRRL
BFBBBBFLLL
BFFFBBBLLL
BBFBBFBLLL
BBFBBFBRLL
FFBFFFFRLR
FFBFBBFLRL
BFFBBBBRRR
FBFFBFBRRR
BFFFBFBRRL
FFBFFFBLRR
BFBBBFBLRL
BBFBBFFRLR
FBFBFBBLRL
FBBFFBBLRL
BFFFFFBLRL
BFFFBBBLLR
FBBFBBBRRR
BFBFFFFLRL
BFBBBBFLRL
BFBBBFBLLL
FBFBBBFRRR
FBFBFFBRRL
BFFFFFFLLR
FBBBBBBRLL
FFBBFBFLRL
FFBFFBFLLR
FFBFFFFLLL
FFBFFBFLLL
BFBFBBFLRR
FBFFFBBRRL
FBFFFBFRRR
FBBBFBFRRR
FFBBFFFLLR
FBBFBBFLRL
BFBBBFFRRR
FBFBFFBLRR
BBFBBBBLLR
BBFFBBBRRR
BBFFFBFLLR
BFBBFFFRLR
FBFFFBFLLR
FFBFBBBRRL
FFFBBBFRRR
BFBBFFFRRL
FBBFBBFRRR
FFBFBBFRRL
FFFFBBBRRL
BBFFFBBLRL
BFBFBBFRRL
FBBBBBFLRL
FBBBBFFLLR
FBFFFBFLRR
FBFFBFBLRL
FFBFBFBRLL
BFBBFFFRRR
BFFFBFBRRR
FFBFFFBRLL
FBBBFBBLRL
BFFFFFBRRL
BFFBBFBLLR
BFFBBBBLRR
BFFBFFFLLR
FBFFBFBRLR
BFFBFBBLLL
FBFFBBBLLL
BBFBBBBRLR
BBFFFBFLRR
FBFBBFBRRR
BBFBFFBLRL
FFFBFBBLRL
FFFBBBFLRR
BFBFFBBLRR
BFBFBBFRLL
FBBFBFFRRR
FFBFFBBLLR
FFBFFFBRRR
FBFBFBBRLR
BFBBFBBLLR
FBFFBBFLRR
BBFFFBFRLR
FFBFBBFRRR
BBFFFBBLLR
BBFFFBFRRL
FFFBBBBLLR
FBBFFFBLLL
FFBFFFBLRL
BBBFFFFLLL
FFBBBFFRRL
FBFBFFFLRL
FBBBBBBLLL
FFBFBBFLLR
BFFFFBBRRR
FBBFFBBLRR
BBFBFFBRRL
FFFBBFBLRL
BBFFBBBLLR
FFFBFFFRRR
FFFBBFBRLL
BFFBBFFLRR
BBFBBBBLLL
BFBFFFBRRL
BBFFBFFRRR
FBBFFFBLRR
FBBBBBBRRR
FFFBFFBRRR
FBFBFBFRRR
BBFFBBFLLL
BBFBFBBLRL
FFBBFBFRLL
BBFBFBFLRL
BFBFBFBLRL
FBFFFFBRRR
FFFBBBBLLL
FFBBFFBRLR
BBFFFFFLRL
BFFBFBBRRL
FBBFFBFLLL
BBFFBFBRRR
FFBBBBFLRL
FFBBFBFLLL
BFFFBBFLLL
FFBBBBFLRR
FBFFBBFRLL
BBFBBBBRRR
FBFBFFFLLR
FBFFFFFLRL
BFBFBBFLLL
BFBFFBBLLR
FFBBBBBLRL
FFBFBBFRLR
FBFFFFBLLR
FBFFBFFRLL
FBBFBBFRRL
FFBFFBFRRR
FBBBBFBLRR
FBBFBFFLRL
FBBFBBFLLR
FBFBBFBRLR
BBFFBFBLLL
BBFBFFFLRR
FBFBFBFLRR
BBFBFFBRLL
BBFBFFBRLR
BFBBBFFLLL
BFFBFBFRRL
BFFFFBBRLL
BFFBBFFLLR
BFFBFBFLRR
BBFBFBFLRR
BFFFBFBLLL
FFFBFBFLRL
FFBFBBBLLL
BBFBBFBLRL
FFBBFFBRLL
BBFBBBFLRL
FBBBFFBRLR
BFBFFFBRLL
FFBFBBBRLL
BFBFFBFLLR
FFBBBBBRLL
FFBFBFBRRR
FBFBFFBLRL
FFBBFFFRLL
FFBBFFBLRL
BFFBBFFLLL
BFFFBBBRLL
FFBFFBBRLR
BFFFBFFLRR
BFFFBBBRRR
FFBFFBBLLL
FBBFBFFLRR
FBFBBBFRRL
FBFFFFBLRL
FBBFBBBRRL
BFFBBFFRLL
BBFFFBBRRL
FFBBFBFLLR
FFBFBBFRLL
FFBFBFFLLR
FFBBBFFLLL
FBBFBFBLLL
FBFBFFBRLR
FFBBFBBRLR
BFBFFFBLLL
FFBBBBBRRL
FFFBBBBRRR
FFBBBBBLLR
FBFBFBFRLL
FBFBFBFRRL
BBFFBFFLRL
FBFBBBBLLL
FBFBFFBLLL
FBBFFFBLRL
FFFBFFFLLR
BBFFFFFRLR
BFFFFBBLLR
FBFBBBFLLR
BFFBFFFRLL
BBBFFFFLLR
FBFBBFBLRL
FFBBFFBRRL
FBFBFBFRLR
BFBBBFBRRR
BFFFFBBRRL
FFFBBBBRRL
FFFBFFBLLL
FBFFFFBLRR
BBFBFFFRLR
FFBBBFFLLR
FBBFFFBRRR
BFBBFBFLRL
BBFFFFFRRL
FBFBBFBLLR
FBFBFFBLLR
FBBFBBBLRL
BFBBBFFRRL
FBBBFFFRRR
FFBBBFBLLL
FBFFBFFLLR
BFFBFFBRLL
FBFFFFBLLL
FFBFBFBLLL
FBBFFFFRLR
BFFBFFFLRR
FBBFFBFLLR"""
