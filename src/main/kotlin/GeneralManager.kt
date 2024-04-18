import equipments.*

object GeneralManager {
    val list: MutableList<General> = mutableListOf()

    private val lordFactory = LordFactory()

    fun getGeneralCount(): Int {
        return list.size
    }

    fun createGenerals(nonLords: Int) {
        val lord = lordFactory.createRandomGeneral(lordFactory.createPlayer(1))
//        lord.setStrategy(RebelStrategy())
        list.add(lord)
        var nonLordFactory: NonLordFactory

        nonLordFactory = NonLordFactory(lord, lord.player as Lord)


        for (i in 0 until nonLords) {
            list.add(nonLordFactory.createRandomGeneral(nonLordFactory.createPlayer(i + 2)))
        }
    }

    fun isGameOver(): Boolean {
        val livingPlayerRoles = list.map { it.identity }

        // The Lord is dead: The Spy will win if he/she is the only survivor. Otherwise, the Rebel wins.
        if (!livingPlayerRoles.contains("Lord")) {

            if (livingPlayerRoles.contains("Spy") && livingPlayerRoles.size == 1) {
                println("Spy wins!")
                return true
            } else {
                println("Rebel Wins!")
                return true
            }

        } else {
            println("Unsatisfied: The Lord is dead: The Spy will win if he/she is the only survivor. Otherwise, the Rebel wins. \n Check next condition... ")

        }

        // All Rebels and Spies are dead: The Lord and the Loyalists win.
        if (!livingPlayerRoles.contains("Rebel") && !livingPlayerRoles.contains("Spy")) {

            println("Lord and Loyalists win!")
            return true
        } else {
            println("Unsatisfied: All Rebels and Spies are dead: The Lord and the Loyalists win. ")
        }

        println("Game is not over yet!")

        // If none of the above conditions are met, the game is not over.
        return false
    }

    fun gameStart() {

        CardManager.createDeck()

        list[3].setCommand(Acedia(list[3]))

        var turn: Int = 1
        println("Turn $turn")

        for (general in GeneralManager.list.toList()) {
            general.playTurn()
        }
        println("Turn $turn over")
        println()
        turn++
        println("Turn $turn. Spy reveals themselves!")

        list[3].strategy = RebelStrategy(list[3])
        println("Now they have Rebel Strategy!")

        for (general in GeneralManager.list.toList()) {

            general.playTurn()

        }
        println("Turn $turn over")
        println()
        turn++

        while (!isGameOver()) {
            println("Turn $turn")

            for (general in GeneralManager.list.toList()) {
                general.playTurn()
            }
            println("Turn $turn over")
            println()
            turn++

        }



        list[0].beingAttacked(list[1])


    }
}

// NEW
object CardManager {
    val originalCards: MutableList<Card> = mutableListOf()
    val discarded: MutableList<Card> = mutableListOf()

    fun createDeck() {
        val suits = arrayOf("Spade", "Heart", "Club", "Diamond")
        val numbers = arrayOf("8", "9", "10", "J")

        // Add more AttackCards
        for (number in numbers) {
            for (suit in suits) {
                originalCards.add(AttackCard("Attack Card", number, suit, null))
            }
        }

        // Add a smaller number of DuelCards, DismantleCards, and EightTrigrams
        for (number in arrayOf("A", "2", "3", "4", "7")) {
            for (suit in suits) {
                originalCards.add(DuelCard("Duel", number, suit, null))
            }
        }

        for (number in arrayOf("5", "6")) {
            for (suit in suits) {
                originalCards.add(EightTrigrams("Eight Trigrams", number, suit))
            }
        }

        for (number in arrayOf("Q", "K")) {
            for (suit in suits) {
                originalCards.add(DismantleCard("Dismantle", number, suit))
            }
        }

        // Add more RangeCards
        originalCards.add(ShadowRunner())
        originalCards.add(FerghanaHorse())
        originalCards.add(HexMark())
        originalCards.add(RedHare())
        originalCards.add(FlyingLightning())
        originalCards.add(VioletStallion())

        // Shuffle the deck
        originalCards.shuffle()
    }

    // Function to discard
    fun discard(card: Card) {
        originalCards.remove(card)
        discarded.add(card)

    }

    fun draw(general: General, numberOfCards: Int): List<Card> {
        println(originalCards.size)
        println(discarded.size)
        // If there are less than 2 cards in the original deck, shuffle the discarded cards back into the deck
        if (originalCards.size < 2) {
            println("Getting cards from discarded")
            discarded.shuffle()
            originalCards.addAll(discarded)
            discarded.clear()
        }

        // Give the top 2 cards to the general

        // TODO: check take
        val drawnCards = originalCards.take(numberOfCards)
        for(card in drawnCards)
        {
            if(card is AttackCard)
            {
                card.changeAttacker(general)
            }
        }
        originalCards.removeAll(drawnCards)
        general.allCards.addAll(drawnCards)

        return drawnCards
    }
}
