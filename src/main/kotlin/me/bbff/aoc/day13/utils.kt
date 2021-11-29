package me.bbff.aoc.day13

import me.bbff.utils.getAocFile
import java.time.Year
import kotlin.math.absoluteValue
import kotlin.math.floor


private val file = getAocFile(Year.of(2020), 13)

val input: Input
    get() {
        val (start, ids) = file.readLines()

        return Input(start.toUInt(), ids.mapToBuses())
    }

fun String.mapToBuses() = splitToSequence(",").map { it.toUIntOrNull() }.toList()


data class Input(val start: UInt, val ids: Collection<UInt?>)

fun Long.toULongStrict(): ULong {
    require(this >= 0) {
        "$this cannot be converted to ULong"
    }
    return toULong()
}


data class Bezout(val gcd: ULong, val a: Long, val b: Long)

// @link(https://introcs.cs.princeton.edu/java/99crypto/ExtendedEuclid.java.html)
fun gcdBezout(p: Long, q: Long): Bezout {
    if (q == 0L)
        return Bezout(p.absoluteValue.toULong(), 1, 0)
    val (d, b0, c) = gcdBezout(q, p % q)
    val b = b0 - (p / q) * c
    return Bezout(d, c, b)
}

/*
 * most of it is based on
 * @link(https://dml.cz/bitstream/handle/10338.dmlcz/402867/BranaKVedeni_002-1949-1_4.pdf)
 * @link(https://is.muni.cz/th/prfye/bakalarska_prace.pdf)
 */
class Expression private constructor(
    val a: Long,
    val b: Long,
    val x0: Long,
    val y0: Long,
    val c: Long,
    val gcd: ULong
) {

    val bDivGCD: Long = b / gcd.toLong()

    val smallestPositiveX: ULong get() {
        val bDividiedByGCD = bDivGCD
        val t = x0.toDouble() / bDividiedByGCD
        return (x0 - (floor(t).toLong() * bDividiedByGCD)).toULongStrict()
    }

    companion object {
        fun from(a: Long, b: Long, c: Long): Expression {
            val bezout = gcdBezout(a, b)
            val gcdLong = bezout.gcd.toLong()
            require(c % gcdLong == 0L) {
                "This is not solvable"
            }
            val ratio = c / gcdLong
            return Expression(a, b, bezout.a * ratio, bezout.b * ratio, c, bezout.gcd)
        }
    }
}
