import equipments.EquipmentCard
import kotlin.random.Random
import kotlin.system.exitProcess

abstract class General(val name: String, val player: Player):Player by player {

    var dodgeAuto: Boolean = false
    open var gender: String? = null
    var strategy: Strategy? = null
    open var maxHP:Int = 4
    // NEW / TODO:
    var allCards: MutableList<Card> = mutableListOf()
    var timeSpellCards: MutableList<TimeSpellCard> = mutableListOf()
    //    var rangeCard: RangeCard? = null
    open var distance: HashMap<String,Int> = HashMap()//new
    var skipPlay = false
    open var attackRange: Int = 1
    open var kingdom: String = ""
    // New, max times of attack card can use for each round
    var attackCards:Int = 0

    // holds current card
    open var listCard: MutableList<Card> = mutableListOf()
    // plays current equipment card -> max = 2
    var toolCards: MutableList<EquipmentCard?> = mutableListOf()



    init {
        println("General $name created.")
        strategy = when (player) {
            is Lord, is Loyalist, is Spy -> LoyalistStrategy(this)
//            is LiuBei -> LiuBeiStrategy(this)
            else -> RebelStrategy(this)
        }
    }

    // CurrentHP = 0
    // Unless has Peach
    fun isKilled(attacker: General) : Boolean {
        if (currentHP == 0 || currentHP <0) {

            println("$name is killed. Player's Identity: ${player?.identity}")
            // iterate thru cards and discard
            val iterator = allCards.iterator()
            while (iterator.hasNext()) {
                val card = iterator.next()
                iterator.remove()
                CardManager.discarded.add(card)
            }
            // Remove player only if they are killed
            GeneralManager.list.remove(this)

            if (player.identity == "Rebel") {
                CardManager.draw(attacker, 3)
            }
            else if (player.identity == "Loyalist" && attacker.identity == "Lord") {
                attacker.allCards.clear()
            }

            println("Check if Game is over ...")
            if (GeneralManager.isGameOver()) {
                exitProcess(0)
            }

            return true
        }
        return false
    }


    fun dodge(suit: String) {

        println(name + " is checking AutoDodge.")

        // if red suit -> dodged automatically
        if ((suit == "Hearts") or (suit == "Diamond")) {
            println(name + " activated autoDodge by RED card.")
            dodgeAuto = true

        } else {
            println(" Could not activate autoDodge")

        }
    }

    // Duel Card

    open fun challenge(target: General): Boolean {

        // may play attack card or not
        while (target.beingChallenged(this)) {
            if (this.hasAttackCard() && this.useAttackCard(target)) {
                // remove attack card
                println("[AttackCard] used")
                val attackCard:AttackCard = allCards.find { it is AttackCard } as AttackCard
                if(attackCard!=null)
                {
                    removeOneAttackCard(attackCard)
                }
//                target.beingAttacked(this)
            } else {
                println(name + " loses the challenge and loses 1 HP.")
                currentHP--

                isKilled(target)
                return false
            }
        }
        return true
    }

    open fun beingChallenged(attacker: General): Boolean {
        if (this.hasAttackCard() && this.useAttackCard(attacker)) {

            println(name + " is attacking.")

            return true
        } else {
            println(name + " loses the challenge and loses 1 HP.")
            currentHP--

            isKilled(attacker)
            return false
        }
    }

    fun hasDismantleCard(): Boolean {
        for (card: Card in allCards) {
            if (card is DismantleCard) {
                return true
            }
        }
        return false
    }

//    fun hasEightTriCard(): Boolean {
//        for (card: Card in allCards) {
//            if (card is EightTrigrams) {
//                return true
//            }
//        }
//        return false
//    }


    open fun beingAttacked(attacker: General) {

        println(name + " being attacked.")

        if (dodgeAuto) {
            println("[Eight Trigrams] autododge worked.")
            dodgeAuto=false

        } else if (hasDodgeCard()) {
            println(name + " dodged attack by spending a dodge card.")

            if (player is Lord) {
                player.notifyObservers(true)
            }
            // Determine whether the Lord dodged the attack
//            return true
        } else if (hasDismantleCard()) {

            // Find DismantleCard from allCards
            val dismantleCard = allCards.find { it is DismantleCard }

            // If a DismantleCard is found, execute it and remove it from allCards
            if (dismantleCard is DismantleCard) {
                val dismantleAction = dismantleCard.execute(attacker)
                dismantleAction()

                println(" [DISMANTLE] : $name dismantled ${attacker.name}")

                removeCard(dismantleCard)
            }

        } else {
            currentHP -= 1

            if (!isKilled(attacker)) {
                println("No dodge card, no dismantle card, damage taken. Current HP: $currentHP")
                if (player is Lord) {
                    player.notifyObservers(false)
                }
            } else {
                println(name + "'s Current HP less that or equal to zero")
            }

        }
    }

    fun removeCard(card: Card) {
        val cardToDelete = allCards.find { it == card }
        if (cardToDelete != null) {
            println("$name removed the card")
            allCards.remove(cardToDelete)
            CardManager.discard(cardToDelete)
        }
    }

    fun removeOneAttackCard(card: AttackCard) {
        //val attackCardToDelete = allCards.find { it is AttackCard }
        //if (attackCardToDelete != null) {
        if(card != null){
            println("$name removed the AttackCard")
            allCards.remove(card)
            card.changeAttacker(null)
            CardManager.discard(card)
        }
    }

    // Dismantle Card

    fun dismantleCard() {
        if (allCards.isNotEmpty()) {
            val randomIndex = Random.nextInt(allCards.size)
            println(name + " lost one card because of Dismantle.")
            allCards.removeAt(randomIndex)
        }
    }

    fun playNextCard() {

        strategy?.playNextCard()
    }
    // allow clients to set command to the invoker
    fun setCommand(timeSpellCard:TimeSpellCard)
    {
        timeSpellCards.add(timeSpellCard)
        println(name + " being placed the TimeSpell Card card.")
    }


    // new
    // TODO: call it somewhere


    fun acediaAction()
    {
        println(name + " judging the Acedia spell.")

        if(Math.random() < 0.25)
        {
            println(name + " dodged the Acedia spell.")
        }
        else
        {
            println(name + " can't dodge the Acedia spell. Skipping one round of play")
            skipPlay = true
        }
    }


    fun useAttackCard(attacker: General): Boolean {
        val attackCard = allCards.find { it is AttackCard } as? AttackCard

        // If an AttackCard is found, execute it
        if (attackCard != null && attackCard is AttackCard) {
            println("[Attack Card] " + name + " uses Attack Card against " + attacker.name)

            attackCard.execute(attacker) // Assuming list[7] is the target

            return true
        }

        return false
    }

    fun executeCommand() {
        for (i in timeSpellCards) {
            i.invoke()
        }


        for (i in 0 until allCards.size) {
            val card = allCards[i]
            if (card is AttackCard) {
                // Get a list of all players except this one
                val otherPlayers = GeneralManager.list.filter { it != this } // Assuming GeneralManager has a list of players

                // If there are other players, choose a random one and execute the attack
                if (otherPlayers.isNotEmpty()) {
                    val randomPlayer = otherPlayers.random()

                    val attackAction = card.execute(randomPlayer)
                    attackAction()

                    removeOneAttackCard(card) // Assuming this is a function to remove an AttackCard
                    break
                }
            }
            else if (card is EightTrigrams) {
                println(" [Eight Trigrams] : $name")

                val eightTrigramsAction = card.execute(this)
                eightTrigramsAction()

                removeCard(card)
                break // Break the loop as the list has been modified
            }
        }

        timeSpellCards.clear()
    }

    fun calculateDistance()
    {
        var index: Int = 0
        for(i in 0 until GeneralManager.list.size)// finds the index for this instance
        {
            if(GeneralManager.list[i].name.equals(name))
            {
                index = i
                break
            }
        }
        for(i in index + 1 until GeneralManager.list.size) // calcalates distance to the right
        {
            distance[GeneralManager.list[i].name]= i - index
        }

        for(i in index - 1 downTo  0) // calcalates distance to the left
        {
            distance[GeneralManager.list[i].name]=  index - i
        }
        var currentDistance: Int = GeneralManager.list.size - index

        for(i in 0 until index) // rechecks the distance to the left
        {
            var temp = distance[GeneralManager.list[i].name]
            if(temp != null && currentDistance < temp)
            {
                distance[GeneralManager.list[i].name] = currentDistance
            }
            currentDistance++

        }
        currentDistance = index + 1
        for(i in GeneralManager.list.size - 1 downTo index + 1)
        {
            var temp = distance[GeneralManager.list[i].name]
            if(temp != null && currentDistance < temp)
            {
                distance[GeneralManager.list[i].name] = currentDistance

            }
            currentDistance++

        }


    }

    fun playTurn()
    {
        preparationPhrase()
        judgementPhrase()
        drawPhrase()
        if (!skipPlay) {
            playPhase()
        }
//        playPhase()
        discardPhrase()
        finalPhrase()
    }
    open fun preparationPhrase()
    {
        calculateDistance()
    }

    open fun judgementPhrase()
    {

        executeCommand()

    }

    open fun drawPhrase()
    {
        val drawnCards = CardManager.draw(this, 2)

        for (card in drawnCards) {
            if (card is DuelCard) {
                card.changeSource(this)
            }
            if (card is AttackCard) {
                card.changeAttacker(this)
            }
        }
        println(name + " draws" + drawnCards.size+ " cards and now has " + allCards.size + " card(s).")
    }
    open fun playPhase()
    {
        println(name + " enters the Play Phase")
        playNextCard()
    }
    open fun discardPhrase() {
        println("$name has ${allCards.size} card(s), current HP is $currentHP.")
        var discarded = 0

        // If there are more cards than currentHP, discard the excess cards
        while (allCards.size > currentHP && allCards.size > 0) {
            val cardToDiscard = allCards.random() // Select a random card to discard
            removeCard(cardToDiscard)
            discarded+=1
        }

        println("$name discards $discarded card(s), now has ${allCards.size} card(s).")
    }
    open fun finalPhrase()
    {

    }

    open fun hasDodgeCard(): Boolean {
        if (allCards.size <= 0) {
            return false  // No cards to check
        }

        for (i in 1..allCards.size ) {
            val randNum = Math.random()
            if (randNum < 0.03) {
                return true  // Found a dodge card
            }
        }

        return false  // No dodge card found
    }


    // check if has attack card
    open fun hasAttackCard(): Boolean {
        return allCards.any { it is AttackCard }
    }

    //
    fun increaseDistance()
    {
        var index:Int = 0
        for(i in 0 until GeneralManager.list.size)
        {
            if(GeneralManager.list[i].name.equals(this.name))
            {
                index = i
                break
            }
        }
        for(i in 0 until GeneralManager.list.size)
        {
            if(i == index)
                continue
            var dis: Int? = GeneralManager.list[i].getDistance(name)
            if (dis != null) {
                GeneralManager.list[i].setDistance(name,dis + 1)
            }
        }
    }
    fun getDistance(name:String): Int? {
        return distance[name]
    }
    fun setDistance(name:String, dis:Int) {
         distance[name] = dis
    }
    fun increaseRange()
    {
        attackRange++
    }

    fun hasPeachCard() :Boolean
    {

        //TODO implement logic
        return Math.random() > 0.7
    }
    //TODO implement logic
    fun removePeachCard()
    {

    }


}


























