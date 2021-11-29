package me.bbff.aoc.day13

import java.time.Duration
import kotlin.math.ceil
import kotlin.system.measureNanoTime


fun main() {
    val value: ULong
    val input = input.ids
    val duration = measureNanoTime {
        value = part2(input)
    }
    println(value)
    println(Duration.ofNanos(duration).toMillis())
}

fun part2(ids: Iterable<UInt?> = input.ids): ULong {
    var offset = 0uL
    val iterator = ids.iterator()
    var lastMeet: ULong = iterator.next()!!.toULong()
    var lcm: ULong = lastMeet

    while (iterator.hasNext()) {
        offset++
        val id = iterator.next() ?: continue
        val c = -offset.toLong() -lastMeet.toLong()
        val expression = Expression.from(lcm.toLong(), id.toLong(), c)

        val x = expression.smallestPositiveX

        lastMeet = (x * lcm) + lastMeet
        lcm = expression.bDivGCD.toULong() * lcm
    }

    return lastMeet
}

private fun part1(i: Input = input): UInt {
    val (start, ids) = i
    val startDouble = start.toDouble()

    val missingParts = ids.asSequence().filterNotNull().associateWith { startDouble / it.toInt() }

    val (closestId) = missingParts.maxByOrNull { it.value % 1 } ?: throw IllegalArgumentException("No bus found")

    // return id times remaining minutes
    return closestId * ((closestId * ceil(startDouble / closestId.toInt()).toUInt()) - start)
}
