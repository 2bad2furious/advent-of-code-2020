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
