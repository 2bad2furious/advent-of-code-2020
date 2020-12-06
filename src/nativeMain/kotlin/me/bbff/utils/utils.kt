package me.bbff.utils

inline fun <T> Sequence<T>.countUnsigned(predicate: (T) -> Boolean): UInt {
    var count = 0u
    for (element in this) if (predicate(element)) ++count
    return count
}

inline fun CharSequence.countUnsigned(predicate: (Char) -> Boolean): UInt {
    var count = 0u
    for (char in this) if (predicate(char)) ++count
    return count
}

operator fun String.get(ui: UInt): Char {
    return get(ui.toInt())
}

fun Sequence<UInt>.multiply(): ULong = fold(1uL) { acc, uInt -> acc * uInt }

fun <T> Sequence<Iterable<T>>.intersect(): Set<T> {
    val iterator = iterator()
    if (!iterator.hasNext()) return setOf()

    val intersect = iterator.next().toMutableSet()
    while (iterator.hasNext()) {
        intersect.retainAll(iterator.next())
    }
    return intersect
}

fun Sequence<CharSequence>.joinToCharSet(): Set<Char> {
    return buildSet {
        for (seq in this@joinToCharSet) {
            for (char in seq) {
                add(char)
            }
        }
    }
    // return flatMap { person -> person.asIterable() }.toSet()
}

fun CharSequence.toSet(): Set<Char> = buildSet {
    for (ch in this@toSet)
        add(ch)
}
