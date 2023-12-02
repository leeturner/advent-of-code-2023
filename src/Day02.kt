import kotlin.math.max

fun main() {

  data class Game(val gameId: Int, val subsets: List<String>) {
    val colours = subsets.map { subset ->
      subset.trim().split(", ").map {
        val (count, colour) = it.split(' ')
        Pair(count.toInt(), Colour.of(colour))
      }
    }

    fun isValid(): Boolean {
      return colours.all { subset ->
        subset.all { pair -> pair.first <= pair.second.maxAllowed }
      }
    }

    fun power(): Int {
      val maxCubes = buildMap {
        colours.forEach { subset ->
          subset.forEach { pair ->
            val (count, colour) = pair
            val currentMax = getOrDefault(colour, 0)
            put(colour, max(currentMax, count))
          }
        }
      }
      return maxCubes.values.fold(1) { acc, i -> acc * i }
    }
  }

  fun List<String>.toGames() = this.map { game -> game.dropWhile { char -> char != ':' }.drop(2) }
    .mapIndexed { index, game -> Pair(index + 1, game.split(';')) }
    .map { (gameId, subsets) -> Game(gameId, subsets) }

  fun part1(input: List<String>): Int {
    return input.toGames().filter { it.isValid() }.sumOf { it.gameId }
  }

  fun part2(input: List<String>): Int {
    return input.toGames().sumOf { it.power() }
  }

  val testInputPart1 = readInput("Day02_test")
  println(part1(testInputPart1))
  check(part1(testInputPart1) == 8)

  val testInputPart2 = readInput("Day02_test")
  println(part2(testInputPart2))
  check(part2(testInputPart2) == 2286)

  val input = readInput("Day02")
  part1(input).println()
  part2(input).println()
}

enum class Colour(val maxAllowed: Int) {
  RED(12), BLUE(14), GREEN(13);

  companion object {
    fun of(colour: String): Colour {
      return when (colour) {
        "red" -> RED
        "blue" -> BLUE
        "green" -> GREEN
        else -> throw IllegalArgumentException("Unknown colour: $colour")
      }
    }
  }
}