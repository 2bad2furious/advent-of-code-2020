package me.bbff.aoc.day17

class PocketDimension<Coordinate>(
    activeCubes: Sequence<Coordinate>,
    private val yieldNeighbors: suspend SequenceScope<Coordinate>.(Coordinate) -> Unit
) {
    private val activeCubesCoords = activeCubes.toMutableSet()

    val activeCubes: Sequence<Coordinate> get() = activeCubesCoords.asSequence()
    val allRelevant: Sequence<Pair<Coordinate, Boolean>>
        get() = activeCubesCoords.asSequence()
            .flatMap { coord ->
                sequence {
                    yield(coord)
                    yieldNeighbors(coord)
                }
            }
            .toSet()
            .asSequence()
            .map { coord -> Pair(coord, get(coord)) }

    private operator fun get(coordinate: Coordinate): Boolean = activeCubesCoords.contains(coordinate)

    operator fun set(coordinate: Coordinate, active: Boolean) = when {
        active -> activeCubesCoords.add(coordinate)
        else -> activeCubesCoords.remove(coordinate)
    }

    fun getNeighborValues(coordinate: Coordinate): Sequence<Boolean> = sequence { yieldNeighbors(coordinate) }
        .map { get(it) }
}
