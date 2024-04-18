package Generals

import AttackCard
import CardManager
import DuelCard
import General
import GeneralManager
import Lord
import Player

abstract class NeutralGeneral(name: String, player: Player) : General(name, player) {

}

class YuanShu(player: Player): NeutralGeneral("Yuan Shu",player) {
    override var maxHP = 3
    override var gender: String? = "male"
}
class LvBu(player: Player): NeutralGeneral("Lv Bu",player) {
    override var maxHP = 3
    override var gender: String? = "male"
}

class HuaXiong(player: Player): NeutralGeneral("Hua Xiong",player) {
    override var maxHP = 3
    override var gender: String? = "male"

    override fun beingAttacked(attacker: General) {
        println(name + " being attacked.")
        if (hasDodgeCard()) {
            println(name + " dodged attack by spending a dodge card.")

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

            println("[Triumphant] Hua Xiong uses Triumphant.")

            // Finding attack card from the top of discard pile
            var attackCard: AttackCard? = null

            attackCard = CardManager.discarded.find { it is AttackCard } as AttackCard?

            if (attackCard != null ) {
                // Generate a random number between 0 (inclusive) and 2 (exclusive)
                println("Attack Card has red suit")
                val randomNumber = (0 until 2).random()

                if (randomNumber == 0) {
                    // 50% chance to recover 1 point of health
                    println("Attacker " + attacker.name + " recover 1 HP.")
                    attacker.currentHP += 1
                } else {
                    // 50% chance to draw 1 card
                    println("Attacker " + attacker.name + " draws one card.")
                    CardManager.draw(attacker, 1)
                }
            }
            if (isKilled(attacker)) {
                println(name + "'s Current HP less that or equal to zero")

            }
        }
    }
}
class HuaTuo(player: Player): NeutralGeneral("Hua Tuo",player) {
    override var maxHP = 3
    override var gender: String? = "male"
    override fun playPhase() {
        super.playPhase()
        if(Math.random()<0.5)
        {
            val card = allCards.take(1)
            if(card.isEmpty()) return
            removeCard(card[0])
            var general = GeneralManager.list.filter { it != this }.random()

            println("[Medical Practice] " + name + " heals " + general.name )

            general.currentHP++
        }
    }
}
class DiaoChan(player: Player): NeutralGeneral("Diao Chan",player) {
    override var maxHP = 3
    override var gender: String? = "female"

    override fun playPhase() {
        super.playPhase()

        val duelCard: DuelCard? = allCards.find { it is DuelCard } as? DuelCard

        if (duelCard != null) {

            println("[Alienation] Select two male players to Duel Each Other")

            // Filter the list for male players
            val malePlayers = GeneralManager.list.filter { it.gender == "male" }

            // Ensure there are at least 2 male players
            if (malePlayers.size < 2) {
                println("Not enough male players for a duel")
                return
            }

            // Randomly select 2 male players
            val playersToDuel = malePlayers.shuffled().take(2)
            println("Duel: " + playersToDuel[0].name + " VS. " + playersToDuel[1].name)


            // Execute the duel action for each selected player
            duelCard.changeSource(playersToDuel[0])

            val duelAction = duelCard.execute(playersToDuel[1])
            duelAction()
            removeCard(duelCard)
        }
    }

    //Beauty Outshining the Moon
    override fun finalPhrase() {
//        numOfCards++
        CardManager.draw(this,1)
        println("[Beauty Outshining the Moon] " + name + " now has " + allCards.size + " card(s)." )
    }

}

