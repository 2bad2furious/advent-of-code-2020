package me.bbff.aoc.day2

data class PasswordRecord(
    val i1: UInt,
    val i2: UInt,
    val char: Char,
    val password: String
) {
    override fun toString(): String {
        return "$i1-$i2 $char: $password"
    }

    companion object {
        private val regex = """(\d+)-(\d+) (\w): (\w+)""".toRegex()

        fun parseFrom(str: String): PasswordRecord {
            val values = regex.matchEntire(str)!!.groupValues
            return PasswordRecord(values[1].toUInt(), values[2].toUInt(), values[3].single(), values[4])
        }
    }
}
