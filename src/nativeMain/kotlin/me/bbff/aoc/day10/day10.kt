package me.bbff.aoc.day10

import me.bbff.utils.getValueAndSet

fun part1(adapterDifferences: Sequence<UInt> = realInput.parsed): UInt {
    println(adapterDifferences.toList())
    val diffCounts = (1u..3u).associateWithTo(mutableMapOf()) { 0u }

    for (diff in adapterDifferences)
        diffCounts.increaseFor(diff)

    return diffCounts.getValue(1u) * diffCounts.getValue(3u)
}

fun part2(adapterDifferences: Sequence<UInt> = realInput.parsed): ULong = findWays(adapterDifferences.toList())

fun findWays(adapterDifferences: List<UInt>): ULong {
    var ways = 1uL
    var list: MutableList<UInt>? = null
    for (diff in adapterDifferences) {
        when {
            diff != 3u -> {
                if (list == null) list = mutableListOf()
                list.add(diff)
            }
            list != null -> {
                when (list.size) {
                    1 -> {
                    }
                    2 -> ways *= 2u
                    3 -> ways *= 4u
                    4 -> ways *= 7u
                    5 -> ways *= 12u
                    else -> TODO()
                }
                list = null
            }
        }
    }
    return ways
}

fun MutableMap<UInt, UInt>.increaseFor(key: UInt) = getValueAndSet(key) { it + 1u }

val String.parsed
    get() = ((splitToSequence('\n').map { it.toUInt() } + 0u)
        .sorted()
        .windowed(2) { (a1, a2) -> a2 - a1 } + 3u)

val smallInput = """16
10
15
5
1
11
7
19
6
12
4"""
val biggerInput = """28
33
18
42
31
14
46
20
48
47
24
23
49
45
19
38
39
11
1
32
25
35
8
17
7
9
4
2
34
10
3"""
val realInput = """149
87
67
45
76
29
107
88
4
11
118
160
20
115
130
91
144
152
33
94
53
148
138
47
104
121
112
116
99
105
34
14
44
137
52
2
65
141
140
86
84
81
124
62
15
68
147
27
106
28
69
163
97
111
162
17
159
122
156
127
46
35
128
123
48
38
129
161
3
24
60
58
155
22
55
75
16
8
78
134
30
61
72
54
41
1
59
101
10
85
139
9
98
21
108
117
131
66
23
77
7
100
51"""
