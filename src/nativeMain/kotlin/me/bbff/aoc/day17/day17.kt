package me.bbff.aoc.day17

import me.bbff.utils.*

fun <C> PocketDimension<C>.play(): UInt {
    repeat(6) {
        val toChange = mutableMapOf<C, Boolean>()
        for ((coord, active) in allRelevant) {
            val count = getNeighborValues(coord).count { it }
            when {
                active && count !in 2..3 -> toChange[coord] = false
                !active && count == 3 -> toChange[coord] = true
            }
        }
        for ((coord, active) in toChange)
            this[coord] = active
    }
    return activeCubes.countUnsigned()
}


fun part1(input: Sequence<Coordinate2D> = realInput.parsed): UInt {
    val pocketDimension = PocketDimension(input.map { it.to3D(0) }, SequenceScope<Coordinate3D>::neighbors)
    return pocketDimension.play()
}

fun part2(input: Sequence<Coordinate2D> = realInput.parsed): UInt {
    val pocketDimension = PocketDimension(input.map { it.to4D(0, 0) }, SequenceScope<Coordinate4D>::neighbors)
    return pocketDimension.play()
}

val smallInput = """.#.
..#
###"""

val realInput = """..#..#.#
##.#..#.
#....#..
.#..####
.....#..
...##...
.#.##..#
.#.#.#.#"""
