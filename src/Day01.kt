fun main() {

  fun List<String>.solve(): Int {
    return this.sumOf { line ->
      val first = line.first { it.isDigit() }
      val last = line.last { it.isDigit() }
      "$first$last".toInt()
    }
  }

  fun part1(input: List<String>): Int {
    return input.solve()
  }

  val replacements = listOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9,
  )

  fun part2(input: List<String>): Int {
    return input.map { replacements.fold(it) { line, (key, value) -> line.replace(key, "$key$value$key") } }.solve()
  }

  val testInputPart1 = readInput("Day01Part1_test")
  println(part1(testInputPart1))
  check(part1(testInputPart1) == 142)

  val testInputPart2 = readInput("Day01Part2_test")
  println(part2(testInputPart2))
  check(part2(testInputPart2) == 281)

  val input = readInput("Day01")
  part1(input).println()
  part2(input).println()
}
