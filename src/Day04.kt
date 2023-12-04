fun main() {

  data class Card(val winningNumbers: List<Int>, val numbersInHand: List<Int>) {
    fun winners(): List<Int> = winningNumbers.filter { numbersInHand.contains(it) }
    fun hasWinners(): Boolean = winners().isNotEmpty()
  }

  fun List<String>.toCards() = this
    .map { card -> card.dropWhile { char -> char != ':' }.drop(2).trim() }
    .map { card -> card.split('|').map { it.trim() } }
    .map { numbers ->
      Card(
        numbers[0].split(' ').filter { it.isNotBlank() }.map { it.toInt() },
        numbers[1].split(' ').filter { it.isNotBlank() }.map { it.toInt() })
    }

  fun part1(input: List<String>): Int {
    return input.toCards()
      .filter { it.hasWinners() }
      .map { it.winners() }
      .map { it.foldIndexed(0) { index, acc, _ -> if (index == 0) 1 else acc * 2 } }
      .sumOf { it }
  }

  fun part2(input: List<String>): Int {
    // generate our initial set of cards
    val cardSet: MutableMap<Int, MutableList<Card>> = buildMap {
      input.toCards().forEachIndexed { index, card ->
        put(index + 1, mutableListOf(card))
      }
    }.toMutableMap()

    // loop to determine how many winners we have and what cards we need to add
    // Note to self: this is a horrible way to do this, should have probably mapped the one
    // card to a count of the number of that specific card we have, then looped over that
    cardSet.keys.forEach { cardId ->
      val cards = cardSet[cardId] ?: listOf()
      cards.forEach { card ->
        if (card.hasWinners()) {
          val winners = card.winners()
          for (i in 1..winners.lastIndex + 1) {
            val cardList = cardSet[cardId + i] ?: mutableListOf()
            cardList.add(cardList.first().copy())
            cardSet[cardId + i] = cardList
          }
        }
      }
    }

    return cardSet.values.flatten().count()
  }

  part1(readInput("Day04_test")).apply {
    println(this)
    check(this == 13)
  }

  part2(readInput("Day04_test")).apply {
    println(this)
    check(this == 30)
  }

  val input = readInput("Day04")
  part1(input).println()
  part2(input).println()
}
