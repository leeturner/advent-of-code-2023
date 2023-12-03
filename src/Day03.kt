typealias Point = Pair<Int, Int>

fun main() {

  class Num(val value: Int)

  fun buildSchematic(grid: List<String>): Map<Point, Any> {
    return buildMap {
      grid.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
          when {
            // if we already have a point at this location, do nothing because we have already 
            // processed this point, and we don't want to overwrite it
            containsKey(Point(x, y)) -> {}
            char.isDigit() -> {
              // find the full number
              val numberString = line.substring(x).takeWhile { it.isDigit() }
              val number = Num(numberString.toInt())
              // add the number to the schematic for each point that it covers
              (0..numberString.lastIndex).forEach { i ->
                put(Point(x + i, y), number)
              }
            }
            // put the character in the schematic as we will need this in the future
            char != '.' -> put(Point(x, y), char)
          }
        }
      }
    }
  }

  fun Map<Point, Any>.adjacent(point: Point): List<Any> {
    // this is pretty horrible but will have to do for now.
    return listOfNotNull(
      get(Point(point.first - 1, point.second - 1)),
      get(Point(point.first, point.second - 1)),
      get(Point(point.first + 1, point.second - 1)),
      get(Point(point.first - 1, point.second)),
      get(Point(point.first + 1, point.second)),
      get(Point(point.first - 1, point.second + 1)),
      get(Point(point.first, point.second + 1)),
      get(Point(point.first + 1, point.second + 1)),
    )
  }

  fun part1(input: List<String>): Int {
    val schematic: Map<Point, Any> = buildSchematic(input)
    val withAdjacent = schematic.entries
      .mapNotNull { (point, value) ->
        if (value is Num && schematic.adjacent(point).any { it is Char }) value else null
      }
      .toSet()
    return withAdjacent.sumOf { it.value }
  }

  fun part2(input: List<String>): Int {
    val schematic: Map<Point, Any> = buildSchematic(input)
    return schematic.entries
      .filter { it.value == '*' }
      .map { schematic.adjacent(it.key).filterIsInstance<Num>().toSet().map { it.value } }
      .filter { it.size == 2 }.sumOf { it.reduce { acc, i -> acc * i } }
  }

  part1(readInput("Day03_test")).apply {
    println(this)
    check(this == 4361)
  }

  part2(readInput("Day03_test")).apply {
    println(this)
    check(this == 467835)
  }

  val input = readInput("Day03")
  part1(input).println()
  part2(input).println()
}
