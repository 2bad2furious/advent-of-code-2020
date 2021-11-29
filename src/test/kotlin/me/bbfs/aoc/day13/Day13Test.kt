package me.bbfs.aoc.day13

import me.bbff.aoc.day13.mapToBuses
import me.bbff.aoc.day13.part2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class Day13Test {
    @Test
    fun testPart2() {
        assertEquals(3417uL, part2("17,x,13,19".mapToBuses()))
        assertEquals(754018uL, part2("67,7,59,61".mapToBuses()))
        assertEquals(779210uL, part2("67,x,7,59,61".mapToBuses()))
        assertEquals(1261476uL, part2("67,7,x,59,61".mapToBuses()))
        assertEquals(1202161486uL, part2("1789,37,47,1889".mapToBuses()))
    }

}
