package me.bbff.aoc.day17

import me.bbff.utils.*

fun <C> PocketDimension<C>.play(): UInt {
    repeat(6) {
        val toChange = mutableMapOf<C, Boolean>()
        for ((coord, active) in allRelevant) {
            val activeAround = getNeighborValues(coord).filter { it }
            when {
                active && !activeAround.isCountInRange(2u..3u) -> toChange[coord] = false
                !active && activeAround.countEq(3u) -> toChange[coord] = true
            }
        }
        for ((coord, active) in toChange)
            this[coord] = active
    }
    return activeCubes.countUnsigned()
}


fun part1(input: Sequence<Coordinate3D> = realInput.parse { x, y -> Coordinate3D(x, y, 0) }): UInt {
    val pocketDimension = PocketDimension(input, SequenceScope<Coordinate3D>::neighbors)
    return pocketDimension.play()
}

fun part2(input: Sequence<Coordinate4D> = realInput.parse { x, y -> Coordinate4D(x, y, 0, 0) }): UInt {
    val pocketDimension = PocketDimension(input, SequenceScope<Coordinate4D>::neighbors)
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
