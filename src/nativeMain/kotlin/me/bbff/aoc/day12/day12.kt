package me.bbff.aoc.day12

import me.bbff.aoc.day12.Direction.Companion.MAX_DEGREES
import me.bbff.utils.inRangeFromZeroTo
import me.bbff.utils.minus
import me.bbff.utils.plus
import me.bbff.utils.times
import kotlin.math.abs

data class Coordinate(val x: Int, val y: Int) {
    operator fun plus(other: Coordinate): Coordinate = Coordinate(x + other.x, y + other.y)

    operator fun times(by: UInt): Coordinate = Coordinate(x * by, y * by)

    val manhattan get() = abs(x) + abs(y)
}

enum class Direction(val degrees: UInt, val coordinate: Coordinate) {
    EAST(90u, Coordinate(1, 0)),
    WEST(270u, Coordinate(-1, 0)),
    NORTH(0u, Coordinate(0, 1)),
    SOUTH(180u, Coordinate(0, -1));

    operator fun plus(degrees: UInt) = byDegrees(this.degrees.toInt() + degrees)
    operator fun minus(degrees: UInt) = byDegrees(this.degrees.toInt() - degrees)

    companion object {
        const val MAX_DEGREES = 360
        fun byDegrees(degrees: Int): Direction {
            val actual = degrees.inRangeFromZeroTo(MAX_DEGREES)
            return values().firstOrNull { it.degrees == actual } ?: throw IllegalArgumentException("$actual x $degrees")
        }
    }
}

fun part1(instructions: Sequence<Pair<Char, UInt>> = realInput.parsed): Int {
    var coordinates = Coordinate(0, 0)
    var facing = Direction.EAST
    for ((dir, by) in instructions) {
        var moveBy = by
        val moveDir: Direction = when (dir) {
            'F' -> facing
            'N' -> Direction.NORTH
            'S' -> Direction.SOUTH
            'W' -> Direction.WEST
            'E' -> Direction.EAST
            'R' -> (facing + by).also {
                facing = it
                moveBy = 0u
            }
            'L' -> (facing - by).also {
                facing = it
                moveBy = 0u
            }
            else -> TODO(dir.toString())
        }
        coordinates += moveDir.coordinate * moveBy
    }
    return coordinates.manhattan
}

val degreeMultipliers = buildMap<UInt, (Coordinate) -> Coordinate> {
    put(0u) { it }
    put(90u) { Coordinate(it.y, -it.x) }
    put(180u) { Coordinate(-it.y, -it.x) }
    put(270u) { Coordinate(-it.y, it.x) }
}

fun part2(instructions: Sequence<Pair<Char, UInt>> = realInput.parsed): Int {
    var shipCoords = Coordinate(0, 0)
    var waypointCoords = Coordinate(10, 1)
    for ((dir, by) in instructions) {
        var moveDir: Direction? = null
        when (dir) {
            'F' -> shipCoords += (waypointCoords * by)
            'N' -> moveDir = Direction.NORTH
            'S' -> moveDir = Direction.SOUTH
            'W' -> moveDir = Direction.WEST
            'E' -> moveDir = Direction.EAST
            'R' -> waypointCoords =
                degreeMultipliers.getValue(by.toInt().inRangeFromZeroTo(MAX_DEGREES))(waypointCoords)
            'L' -> {
                val k = by.toInt().times(-1)
                val l = k.inRangeFromZeroTo(MAX_DEGREES)
                waypointCoords = degreeMultipliers.getValue(l)(waypointCoords)
            }
            else -> TODO(dir.toString())
        }
        if (moveDir != null)
            waypointCoords += (moveDir.coordinate * by)
    }
    return shipCoords.manhattan
}

val String.parsed
    get() = splitToSequence('\n')
        .map {
            val char = it[0]
            val number = it.substring(1).toUInt()
            char to number
        }

val simpleInput = """F10
N3
F7
R90
F11"""

val realInput = """R90
S1
R90
W2
S3
L270
L90
S2
F2
L90
W2
S5
E2
S5
F42
W5
L90
F15
R90
S2
F76
E1
F26
R90
W1
L90
W1
F20
L180
F38
S2
W5
F13
N3
E2
N4
L90
N1
E4
L90
E2
F21
R90
N5
F78
E3
L90
W4
S2
R180
F53
E5
F42
N1
R90
F70
E1
N5
W4
S5
R90
W3
L90
F74
E5
L180
L90
S5
F51
W4
F33
N5
F55
L90
W3
R180
E3
N4
E3
R180
E2
N2
W4
L90
F13
W3
S2
W4
F89
L90
F92
R90
N5
R90
W3
N1
R90
N3
F38
W3
L90
W3
S2
R90
E1
F24
L90
F70
E4
S1
R90
E4
S2
F3
W4
N3
E1
L180
F10
E5
F5
N4
F83
F98
E1
N4
E2
E1
L180
W2
F29
N4
L90
E2
F66
N2
W5
L180
E4
F24
L180
N3
S1
E4
L180
N3
R90
F84
F25
N4
E5
N4
E4
F37
L180
F50
L90
E4
L90
S2
R90
N1
L90
E1
N3
F59
R90
N2
E3
S2
R90
F25
L90
W5
F6
E2
F5
N3
L90
W2
F18
R90
N5
F10
N5
R90
E4
L90
N3
F61
W2
F77
R180
F76
W5
N5
S5
L180
F90
R90
E1
S1
E2
L90
F12
S5
F10
R90
S1
E1
L90
F51
S4
W4
F6
R90
W2
F62
R180
S5
E1
R90
N5
F37
E5
F41
R90
E3
S5
E5
N2
N5
R90
W2
S3
W3
R90
F9
R90
E4
L90
N3
F9
R180
S3
R90
W3
F100
F58
L180
N2
L90
S1
W1
N1
W2
S5
E2
W3
F44
R90
F11
R90
E4
S5
R270
F20
S4
F90
L90
E1
S4
E2
L90
S3
F69
L90
F79
E3
R180
F22
E2
F71
S3
L90
F58
E2
S4
L90
S1
E4
L180
F86
N5
R90
F57
L180
W1
L90
F64
S1
W2
R180
E3
F26
W5
R90
S5
E3
F66
L180
F57
S4
F21
L180
F99
W5
F20
L180
S3
R90
N5
R180
N4
F53
L90
W4
R90
N1
W2
S4
F50
E1
S3
L180
F38
E2
F97
E2
F50
L90
F21
L90
F10
L90
N3
F55
E1
F99
R90
E4
N2
W2
L90
N2
W4
S5
R270
E5
L90
F27
E3
R90
F93
E3
R90
E5
L270
E1
S1
E4
F88
R270
N5
F2
N1
R270
E5
L90
F82
L180
F52
R90
W5
F61
W3
F1
W5
R180
F78
S3
L270
W4
N1
F83
W1
L270
N4
F23
N1
E1
S5
L90
S5
E1
R180
E4
F98
N5
R90
F18
R90
F10
L90
F100
L90
W3
S2
E2
N1
W4
F3
W1
L90
F42
S4
E5
L90
F13
S5
F82
S2
W3
N5
F50
W4
F50
L90
F2
F26
W2
N3
F2
S3
F94
S2
E3
S5
E4
S1
F17
N4
W1
L90
W5
N4
E5
F31
R270
E3
F21
E5
F62
W1
N5
F43
R90
S4
F81
W3
S4
L90
E2
L90
S1
F67
W3
R180
W2
F76
F96
R90
F72
E2
R90
N5
E3
E1
F87
E3
L90
S2
L90
S3
W1
R90
S2
E1
L180
F82
E5
F91
N4
R90
W3
S5
E2
F74
S5
F32
S5
L180
N3
R90
N1
F11
E3
F9
N3
F83
E3
F51
R90
S1
F19
L180
F61
R180
E4
F34
S5
R180
S1
W4
N4
R90
W1
F23
W2
F93
E1
S4
F6
E1
E5
F82
E1
S1
E3
R180
E5
F71
N1
L90
N3
W1
F94
R270
F33
N2
R90
E1
F52
N4
W1
L90
S1
F71
N4
R180
S1
E1
S4
F29
W4
L90
F43
L90
F13
W2
S3
W4
N5
W3
F41
E2
F69
L90
F4
W1
F77
N5
F17
E1
F99
S2
E5
L90
F92
R90
E3
R90
N1
F19
S1
R90
W5
N5
L90
S1
E2
N2
E3
S1
F76
E2
S4
L90
F32
N1
W1
F12
E2
W2
F19
S1
F94
N3
F72
L180
F4
N4
W5
F82
W3
F30
S5
F21
S1
W3
R90
W5
L270
N1
W3
S1
W5
F45
R90
N1
R90
F21
N2
F42
L90
F72
N5
F85
W2
F68
F20
W3
S1
W4
S3
F59
N1
L90
E5
N1
R90
S5
F56
W2
S1
F3
N4
W3
F89
E5
N3
E3
N5
W4
F5
R90
W3
E3
R270
E2
N4
F68
R90
N1
L90
S3
F59
S2
F98
R90
S2
L90
F59
F97
E5
F20
S5
F67
W3
F100
W2
R90
N2
E4
L180
S5
R90
E2
S1
L90
S1
F38
N3
W4
F47
E3
S5
F65
N3
F10
W4
N5
L90
N3
L90
F64
S2
L90
E5
S2
E3
N5
F82
R180
F97
S2
E5
R90
W2
R90
F67
L180
E3
N2
S4
N5
R90
E1
F81
R180
N3
R90
E2
N3
W3
W1
S3
L90
F72"""
