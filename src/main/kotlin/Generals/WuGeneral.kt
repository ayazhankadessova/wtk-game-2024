package Generals

import CardManager
import General
import GeneralManager
import Lord
import Player
import kotlin.random.Random

abstract class WuGeneral(name: String, player: Player) : General(name, player) {
    var nextInChain: WuGeneral? = null
    fun handleRequest(): Boolean {
        if (player.shouldHelpLord() && hasPeachCard()) {
            println("$name helps Sun Quan with an peach card")
            removePeachCard()
            return true
        } else {
            println("$name is not a Loyalist.")
            return nextInChain?.handleRequest() ?: false
        }
    }
}

class SunQuan(player: Player): WuGeneral("Sun Quan", player) {
    override var maxHP = 5
    override var gender: String? = "male"
    override fun beingChallenged(attacker : General):Boolean {
        var flag = super.beingChallenged(attacker)
        if(!isKilled(attacker))
        {

            if(!flag)
            {
                println("[Rescue] Sun Quan activates Lord Skill Rescue.")
                flag = handleRequest()
                if(flag)
                {
                    currentHP++
                }
            }

        }
        return flag

    }
}
class ZhouYu(player: Player): General("Zhou Yu",player) {
    override var maxHP = 3
    override var gender: String? = "male"
    override fun drawPhrase() {
//        numOfCards+=3
        CardManager.draw(this as General,3)

        println("[Heroism] " + name + " draws 3 cards and now has " + allCards.size + " card(s).")

    }

}

class SunShangxiang(player: Player): WuGeneral("Sun Shangxiang", player) {
    override var maxHP = 5
    override var gender: String? = "female"
}
class LvMeng(player: Player): WuGeneral("Lv Meng", player) {
    override var maxHP = 5
    override var gender: String? = "male"
}
class LuXun(player: Player): WuGeneral("Lu Xun", player) {
    override var maxHP = 5
    override var gender: String? = "male"
    override fun finalPhrase() {
        super.finalPhrase()
        if(allCards.size == 0)
        {
            println("[Coalition] " + name + " uses Coalition")
            CardManager.draw(this,1)

        }
    }
}
class HuangGai(player: Player): WuGeneral("Huang Gai", player) {
    override var maxHP = 5
    override var gender: String? = "male"
    override fun playPhase() {
        super.playPhase()
        if(currentHP > 1)
        {
            if (Math.random() < 0.5)
            {
                println("[Sacrifice] " + name + " uses Sacrifice")

                currentHP--
                if(!isKilled(this)) {
                    println("Died during sacrifice.")
                } else {
                    CardManager.draw(this,2)
                }

            }
        }
    }
}
class GangNing(player: Player): WuGeneral("Gang Ning", player) {
    override var maxHP = 5
    override var gender: String? = "male"
}
class DaQiao(player: Player): WuGeneral("Da Qiao", player) {
    override var maxHP = 5
    override var gender: String? = "female"
    override fun beingAttacked(attacker: General) {
        println("$name being attacked.")
        val random = Random.nextDouble()
        if (random <= 0.4) {
            println("[Deflection] $name uses Deflection")
            val potentialDeflectors = GeneralManager.list.filter { it != this }

            var deflector: General = potentialDeflectors.random()
            if (deflector == null || distance[deflector.name] == null) {
                println("$name could not deflect, being attacked.")
                processAttack(attacker)
            } else {
                deflector.beingAttacked(attacker)
            }
        } else {
            processAttack(attacker)
        }
    }

    private fun processAttack(attacker: General) {
        if (hasDodgeCard()) {
            println("$name dodged attack by spending a dodge card.")
            if (player is Lord) {
                player.notifyObservers(true)
            }
        } else {
            currentHP -= 1
            if (!isKilled(attacker)) {
                println("$name Remains in the game.")
            } else {
                println("$name's Current HP less that or equal to zero")
            }
        }
    }
}
