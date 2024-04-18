abstract class General(val name: String, val player: Player) : Player by player {
    open var maxHP: Int = 4
    var strategy: Strategy? = null

    var numOfCards:Int = 4
    var skipPlay = false
    var cardsList: MutableList<Command> = mutableListOf()


    init {
        println("General $name created.")
        }

    fun playTurn()
    {
        preparationPhrase()
        judgementPhrase()
        drawPhrase()
        if (!skipPlay) {
            playPhrase()
        }
//        playPhrase()
        discardPhrase()
        finalPhrase()
    }

    open fun preparationPhrase() {}

    open fun judgementPhrase()
    {

        executeCommand()

    }

    open fun drawPhrase()
    {
        numOfCards+=2
        println(name + " draws 2 cards and now has " + numOfCards + " card(s).")
    }
    open fun playPhrase()
    {
        println(name + " enters the Play Phase")
        playNextCard()
    }

    fun playNextCard() {
//        if (numOfCards > 1 && hasAttackCard()) {
//            strategy?.playNextCard()
////            numOfCards--
//        }
        strategy?.playNextCard()
    }
    open fun discardPhrase()
    {
        println(name + " has " + numOfCards + " card(s), current HP is " + currentHP + "." )
        val discarded:Int = numOfCards - currentHP

        numOfCards = currentHP
        println(name + " discards " + discarded + " card(s), now has " + numOfCards + " card(s).")

    }

    open fun finalPhrase()
    {

    }

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

    fun executeCommand() {
        for (i in cardsList) {
//            if (i is Acedia) {
                i.invoke()
//            }

        }

        cardsList.clear()


    }

    fun setCommand(command: Command)
    {
        cardsList.add(command)
        println(name + " being placed the Acedia card.")
    }

    fun hasDodgeCard(): Boolean {
        if (numOfCards <= 0) {
            return false  // No cards to check
        }

        for (i in 1..numOfCards) {
            val randNum = Math.random()
            if (randNum < 0.15) {
                return true  // Found a dodge card
            }
        }

        return false  // No dodge card found
    }

    fun hasAttackCard(): Boolean {
        if (numOfCards <= 0) {
            return false  // No cards to check
        }

        for (i in 1..numOfCards) {
            val randNum = Math.random()
            if (randNum < 0.2) {
                return true  // Found a dodge card
            }
        }

        return false  // No dodge card found
    }

    open fun beingAttacked() {

        println(name + " being attacked.")
        if (hasDodgeCard()) {
            println(name + " dodged attack by spending a dodge card.")
//
            if (player is Lord) {
                player.notifyObservers(true)
            }
            // Determine whether the Lord dodged the attack
//            return true
        } else {
            currentHP -= 1
            println("No dodge card, damage taken. Current HP: $currentHP")
            if (player is Lord) {
                player.notifyObservers(false)
            }

//            return false
        }
    }

}

class LiuBei(player: Player) : General("Liu Bei", player) {
    override var maxHP: Int = 5
}

class CaoCao(player: Player) : WeiGeneral("Cao Cao", player) {
    override var maxHP: Int = 5

    override fun beingAttacked() {
        println(name + " being attacked.")
        println("[Entourage] Cao Cao activates Lord Skill Entourage.")
        if (!handleRequest()) {
            if (hasDodgeCard()) {
                println(name + " dodged attack by spending a dodge card. Old numOfCards: " + numOfCards)
                numOfCards-=1
                println("new numOfCards: " + numOfCards)
                if (player is Lord) {
                    player.notifyObservers(true)
                }
            } else {
                currentHP -= 1
                println("No dodge card, damage taken. Current HP: $currentHP")
                if (player is Lord) {
                    player.notifyObservers(false)
                }
            }
        } else {
            println("Attacked Dodged. Current HP: $currentHP")
//            if (player is Lord) {
//                player.notifyObservers(true)
//            }
        }
    }

    fun entourage():Boolean
    {
        println("[Entourage] Cao Cao activates Lord Skill Entourage.")
        return handleRequest()
    }
}

class SunQuan(player: Player) : General("Sun Quan", player) {
    override var maxHP: Int = 5
}

class XuChu(player:Player):  WeiGeneral("Xu Chu",player) {
    override var maxHP = 3

}


class SimaYi(player:Player): WeiGeneral("Sima Yi",player) {
    override var maxHP = 3

}

class XiahouDun(player:Player): WeiGeneral("Xiahou Dun",player) {
    override var maxHP = 3

}
class ZhouYu(player:Player): General("Zhou Yu",player) {
    override var maxHP = 3
    override fun drawPhrase() {
        numOfCards+=3
        println("[Heroism] " + name + " draws 3 cards and now has " + numOfCards + " card(s).")

    }

}

class DiaoChan(player:Player): General("Diao Chan",player) {
    override var maxHP = 3

    //Beauty Outshining the Moon
    override fun finalPhrase() {
        numOfCards++
        println("[Beauty Outshining the Moon] " + name + " now has " + numOfCards + " card(s)." )
    }

}


class GuanYu {
    val maximumHP = 4
}

class GuanYuAdapter(private val guanYu: GuanYu,player: Player) : Player, General("Guan Yu", player) {
    override var maxHP = guanYu.maximumHP
}

abstract class WeiGeneral(name: String, player: Player) : General(name, player) {
    var nextInChain: WeiGeneral? = null

    fun handleRequest(): Boolean {
        if (player.shouldHelpLord()) {
            println("$name helps Cao Cao dodge an attack.")
            return true
        } else {
            println("$name is not a Loyalist.")
            return nextInChain?.handleRequest() ?: false
        }
    }
}