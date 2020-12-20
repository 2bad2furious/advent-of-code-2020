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
fun <T> Sequence<T>.firstIndexed(predicate: (Int, T) -> Boolean): T = sequence<T> {
    val iterator = this@firstIndexed.iterator()
    var counter = 0
    while (iterator.hasNext()) {
        val v = iterator.next()
        if (predicate(counter++, v)) yield(v)
    }
}.first()

inline fun <K, V> MutableMap<K, V>.getValueAndSet(key: K, newValue: (V) -> V) {
    val old = this.getValue(key)
    this[key] = newValue(old)
}

operator fun Int.minus(other: UInt): Int = minus(other.toInt())
operator fun Int.plus(other: UInt): Int = plus(other.toInt())
operator fun Int.plus(other: ULong): Long = plus(other.toLong())
operator fun Int.times(other: UInt): Int = times(other.toInt())
operator fun Int.div(other: UInt): Int = div(other.toInt())

fun Int.inRangeFromZeroTo(other: Int) = when {
    this < 0 -> other - (-this % other)
    else -> this % other
}.toUInt()


fun <T> Iterable<T>.allIndexed(predicate: (UInt, T) -> Boolean): Boolean {
    var counter = 0u
    for (element in this) if (!predicate(counter++, element)) return false
    return true
}

data class IndexedValue<I, T>(val index: I, val value: T)

fun ULong.toBitSet(): BitSet {
    val bits = BitSet()
    var index = 0
    var value = this
    while (value != 0uL) {
        if (value % 2u != 0uL) {
            bits.set(index)
        }
        ++index
        value = value shr 1
    }
    return bits
}

fun BitSet.mapNotNull(block: (UInt) -> Boolean?): BitSet = BitSet(size) { block(it.toUInt()) ?: get(it) }

fun BitSet.toULong(): ULong {
    var value = 0uL
    for (i in 0 until size) {
        if (get(i)) value += 1uL shl i
    }
    return value
}
