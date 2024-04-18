package Generals

import AttackCard
import General
import Lord
import Player

abstract class ShuGeneral(name: String, player: Player) : General(name, player) {
    var nextInChain: ShuGeneral? = null

    fun handleRequest(lord: General): Boolean {
        if (player.shouldHelpLord() && hasAttackCard()) {
            println("$name helps Liu Bei with an attack card")
            val attackCard = allCards.find { it is AttackCard } as? AttackCard
            if(attackCard!=null)
            {
                removeOneAttackCard(attackCard)
                lord.allCards.add(attackCard)
            }
            return true

        } else {
            println("$name is not a Loyalist.")
            return nextInChain?.handleRequest(lord) ?: false
        }
    }


}

class LiuBei(player: Player): ShuGeneral("Liu Bei", player) {
    override var maxHP = 5
    override var kingdom: String = "Shu"
    override var gender: String? = "male"
    override fun hasAttackCard(): Boolean {
        var flag = super.hasAttackCard()

        if(!flag)
        {
            println("Liu Bei does not have an attack card")
            println("[Aggression] Liu Bei activates Lord Skill Aggression.")
            flag = handleRequest(this)

        }
        return flag
    }
}

class MaChao(player: Player): ShuGeneral("Ma Chao",player) {
    override var maxHP = 3
    override var gender: String? = "male"
    override fun preparationPhrase() {
        println("[Horse Riding] Ma Chao uses Horse Riding.")
        super.preparationPhrase()
        attackRange++
    }

    override fun finalPhrase() {
        super.finalPhrase()
        attackRange++
    }

}
class HuangYueying(player: Player): ShuGeneral("Huang Yueying",player) {
    override var maxHP = 3
    override var gender: String? = "female"

}
class ZhaoYun(player: Player): ShuGeneral("Zhao Yun",player) {

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
        }
        else if(hasAttackCard())
        {
            println("[Bravery] Zhao Yun uses Bravery.")
            val attackCard:AttackCard = allCards.find { it is AttackCard } as AttackCard
            if(attackCard!=null)
            {
                removeOneAttackCard(attackCard)
            }
        }
        else {
            currentHP -= 1
            if (!isKilled(attacker)) {
                println("No dodge card, damage taken. Current HP: $currentHP")
                if (player is Lord) {
                    player.notifyObservers(false)
                }
            } else {
                println(name + "'s Current HP less that or equal to zero")
            }


//            return false
        }
    }

    override fun challenge(target:General): Boolean {
        while (target.beingChallenged(this)) {
            if (this.hasAttackCard() ) {
                // remove attack card
                if( this.useAttackCard(target))
                {
                }

            }
            else if(this.hasDodgeCard())
            {
                println("[Bravery] Zhao Yun uses Bravery.")
                if( this.useAttackCard(target))
                {
                    //TODO remove dodge card
                }
            }
            else {
                // quit loop
                println(name + " loses the challenge and loses 1 HP.")
                currentHP--

                isKilled(target)
                return false
            }
        }

        return true

    }



}





class ZhugeLiang(player: Player): ShuGeneral("Zhuge Liang",player) {
    override var maxHP = 3
    override var gender: String? = "male"
    override fun beingChallenged(attacker: General): Boolean {
        if(allCards.size == 0)
        {
            println("[Empty City Strategy] Zhuge Liang uses Empty City Strategy.")
            return true
        }
        else
        {
            return super.beingChallenged(attacker)
        }
    }

}

class ZhengFei(player: Player): ShuGeneral("Zheng Fei",player) {
    override var maxHP = 3
    override var gender: String? = "male"
}

class GuanYu() {
    var maximumHP  = 3


}
class GuanYuAdapter(private val guanYu: GuanYu, player: Player): ShuGeneral("Guan Yu",player) {
    override var maxHP = guanYu.maximumHP
    override var gender: String? = "male"
    override fun hasAttackCard(): Boolean {

        if (super.hasAttackCard() == false)
        {
            for (card in allCards)
            {
                if(card.suit.equals("Heart") || card.suit.equals("Heart"))
                {
                    println("[God of War] Guan Yu uses God of War.")
                    allCards.remove(card)
                    return true
                }
            }
        }
        return false
    }

}
