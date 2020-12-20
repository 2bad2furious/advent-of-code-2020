package me.bbff.aoc.day15

class LastPositions(lastPosition: UInt) {
    var lastPosition: UInt = lastPosition
        set(value) {
            nextToLastPosition = field
            field = value
        }
    private var nextToLastPosition: UInt? = null

    val diff get() = nextToLastPosition?.let { lastPosition - it }
}

fun part1(startingNumbers: List<UInt> = realInput.parsed): UInt = play(startingNumbers).take(2020).last()
fun part2(startingNumbers: List<UInt> = realInput.parsed): UInt = play(startingNumbers).take(30_000_000).last()

fun play(startingNumbers: List<UInt>) = sequence {
    yieldAll(startingNumbers)
    var round = (startingNumbers.size + 1).toUInt()
    val lastPositionsForNumbers = startingNumbers.asSequence().withIndex()
        .associateTo(mutableMapOf()) { (i, n) -> n to LastPositions(i.toUInt() + 1u) }
    var lastSpoken = startingNumbers.last()
    var lastPositions = lastPositionsForNumbers.getValue(lastSpoken)

    while (true) {
        lastSpoken = lastPositions.diff ?: 0u
        yield(lastSpoken)
        lastPositions = when (val positions = lastPositionsForNumbers[lastSpoken]) {
            null -> LastPositions(round).also { lastPositionsForNumbers[lastSpoken] = it }
            else -> positions.also { it.lastPosition = round }
        }
        round++
    }
}

val String.parsed get() = splitToSequence(',').map { it.toUInt() }.toList()

val realInput = """0,1,5,10,3,12,19"""
