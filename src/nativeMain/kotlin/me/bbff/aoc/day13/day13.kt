package me.bbff.aoc.day13

import me.bbff.utils.IndexedValue

data class Input(val arrival: UInt, val ids: Sequence<UInt?>)
data class Result(val id: UInt, val mod: UInt)

fun part1(input: Input = realInput.parsed): UInt {
    val result = input.ids
        .filterNotNull()
        .map { id ->
            val modActual = when (val mod = input.arrival % id) {
                0u -> mod
                else -> id - mod
            }
            Result(id, modActual)
        }.minByOrNull { it.mod }!!
    return result.id * result.mod
}


fun part2(ids: Sequence<UInt?> = realInput.parsed.ids): ULong {
    @Suppress("UNCHECKED_CAST")
    val idsIndexed: List<IndexedValue<ULong, ULong>> =
        ids.mapIndexed { i, id -> IndexedValue(i.toULong(), id?.toULong()) }
            .filter { it.value != null }
            .toList() as List<IndexedValue<ULong, ULong>>
    val max = idsIndexed.maxByOrNull { it.value }!!
    return (max.value..ULong.MAX_VALUE).step(max.value.toLong()).asSequence()
        .map { offset -> offset - max.index }
        .first { offset ->
            idsIndexed.all { (idIndex, id) -> (offset + idIndex) % id == 0uL }
        }
}

val String.parsed
    get(): Input {
        val (arrivalStr, ids) = split('\n')
        val idsParsed = ids.splitToSequence(',')
            .map { it.toUIntOrNull() } // Id could be 'x'
        return Input(arrivalStr.toUInt(), idsParsed)
    }

val smallInput = """939
7,13,x,x,59,x,31,19"""

val realInput = """1000067
17,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,439,x,29,x,x,x,x,x,x,x,x,x,x,13,x,x,x,x,x,x,x,x,x,23,x,x,x,x,x,x,x,787,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,19"""
