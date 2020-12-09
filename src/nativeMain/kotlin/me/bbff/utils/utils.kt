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

fun Sequence<UInt>.multiply(): UInt = reduce { acc, uInt -> acc * uInt }

fun <T, R> Sequence<T>.reduceWithFirstAsAccumulator(initial: (T) -> R, operation: (R, T) -> Unit): R? {
    val iterator = iterator()
    if (!iterator.hasNext()) return null

    val acc: R = initial(iterator.next())
    while (iterator.hasNext()) {
        operation(acc, iterator.next())
    }
    return acc
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

fun <T> List<T>.subListTillEndFrom(fromIndex: Int) = subList(fromIndex, size)
fun <T> List<T>.subListOfFirst(n: Int) = subList(0, n + 1)

fun <T : Comparable<T>> Iterable<T>.findMinMax(): Pair<T, T> {
    val iterator = iterator()
    var min = iterator.next()
    var max = min

    while (iterator.hasNext()) {
        val v = iterator.next()
        when {
            v < min -> min = v
            v > max -> max = v
        }
    }
    return min to max
}

fun Iterable<ULong>.sumMinAndMax(): ULong {
    return findMinMax().let { (min, max) -> min + max }
}

fun <T> List<T>.shrinkingListsRight(): Sequence<List<T>> = sequence {
    for (i in indices)
        yield(subListTillEndFrom(i))
}


// TODO optimize - no need to create indexedValue, which "filterIndexed" does
fun <T> Sequence<T>.firstIndexed(predicate: (Int, T) -> Boolean): T = filterIndexed(predicate).first()
