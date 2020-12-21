package me.bbff.aoc.day17

import me.bbff.utils.Coordinate2D
import me.bbff.utils.Coordinate3D
import me.bbff.utils.Coordinate4D


val String.parsed: Sequence<Coordinate2D>
    get() = splitToSequence('\n')
        .mapIndexed { y, line ->
            line.asSequence()
                .mapIndexedNotNull { x: Int, c: Char ->
                    when (c) {
                        '#' -> Coordinate2D(x, y)
                        else -> null
                    }
                }
        }.flatten()

suspend fun SequenceScope<Coordinate3D>.neighbors(coordinate: Coordinate3D) {
    for (x in -1..1)
        for (y in -1..1)
            for (z in -1..1)
                if (x != 0 || y != 0 || z != 0)
                    yield(Coordinate3D(coordinate.x + x, coordinate.y + y, coordinate.z + z))
}

suspend fun SequenceScope<Coordinate4D>.neighbors(coordinate: Coordinate4D) {
    for (x in -1..1)
        for (y in -1..1)
            for (z in -1..1)
                for (w in -1..1)
                    if (x != 0 || y != 0 || z != 0 || w != 0)
                        yield(Coordinate4D(coordinate.x + x, coordinate.y + y, coordinate.z + z, coordinate.w + w))
}
