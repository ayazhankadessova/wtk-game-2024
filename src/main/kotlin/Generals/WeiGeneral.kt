package Generals

import AttackCard
import Card
import CardManager
import General
import GeneralManager
import Lord
import Player

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
class CaoCao(player: Player): WeiGeneral("Cao Cao", player) {
    override var maxHP = 5
    override var gender: String? = "male"
    override var kingdom: String = "Shu"
    override fun beingAttacked(attacker: General) {
        println(name + " being attacked.")
        println("[Entourage] Cao Cao activates Lord Skill Entourage.")
        if (!handleRequest()) {
            if (hasDodgeCard()) {
                removeCard(allCards.random())
                println("new numOfCards: " + allCards.size)
                if (player is Lord) {
                    player.notifyObservers(true)
                }
            } else {
                currentHP -= 1
                if (!isKilled(attacker)) {
                    println("No dodge card, damage taken. Current HP: $currentHP")
                    if (player is Lord) {
                        player.notifyObservers(false)
                    }
                } else {
                    println(name + "'s Current HP less that or equal to zero")
                }
            }
        } else {
            println("Attacked Dodged. Current HP: $currentHP")
            if (player is Lord) {
                player.notifyObservers(true)
            }
        }
    }

    fun entourage():Boolean
    {
        println("[Entourage] Cao Cao activates Lord Skill Entourage.")
        return handleRequest()
    }
}

class ZhenJi(player: Player): WeiGeneral("Zhen Ji",player) {
    override var maxHP = 3
    override var gender: String? = "female"
    override fun beingAttacked(attacker: General) {
        if (hasDodgeCard()) {
            println(name + " dodged attack by spending a dodge card.")

            if (player is Lord) {
                player.notifyObservers(true)
            }
            // Determine whether the Lord dodged the attack
//            return true
        }
        else {
            for(card in allCards)
            {
                if (card.suit.equals("Spade") || card.suit.equals("Club"))
                {
                    println("[Empress Dowager] Zhen Ji uses Empress Dowager to dodge a card")
                    allCards.remove(card)
                    return
                }

            }
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
}


class XuChu(player: Player): WeiGeneral("Xu Chu",player) {
    override var maxHP = 3
    override var gender: String? = "male"
    override fun drawPhrase() {
        println("[Bared Bodied] Xu Chu uses Bared Bodied")
        val drawnCards = CardManager.draw(this,1)
        println(name + " draws" + drawnCards.size+ " cards and now has " + allCards.size + " card(s).")
    }

    override fun hasAttackCard(): Boolean {
        return true
    }

}

class ZhangLiao(player: Player): WeiGeneral("Zhang Liao",player) {
    override var maxHP = 3
    override var gender: String? = "male"
    override fun drawPhrase() {
        println("[Incursion] Zhang Liao uses Incursion")

//        numOfCards+=2
        var firstGeneral = GeneralManager.list.random()
        while (firstGeneral.name.equals(name))
        {
            firstGeneral = GeneralManager.list.random()
        }
        var secondGeneral = GeneralManager.list.random()
        while (secondGeneral.name.equals(firstGeneral.name) && secondGeneral.name.equals(name))
        {
            secondGeneral = GeneralManager.list.random()
        }
        var card1 = firstGeneral.allCards.take(1).toMutableList()
        var card2 = secondGeneral.allCards.take(1).toMutableList()
        if(card1.isNotEmpty()){
            println(name + " takes a card from " + firstGeneral.name)
            if(card1[0] is AttackCard)
            {
                var card:AttackCard = card1[0] as AttackCard
                card.changeAttacker(this)
                card1[0] = card
            }
            allCards.add(card1[0])


        }
        else
        {
            println(firstGeneral.name + " does not have any cards")

        }
        if(card2.isNotEmpty()) {
            allCards.add(card2[0])

            println(name + " takes a card from " + secondGeneral.name)
            if(card2[0] is AttackCard)
            {
                var card:AttackCard = card2[0] as AttackCard
                card.changeAttacker(this)
                card2[0] = card
            }
            allCards.add(card2[0])


        }
        else
        {
            println(secondGeneral.name + " does not have any cards")

        }

    }

}
class XiahouDun(player: Player): WeiGeneral("Xiahou Dun",player) {
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

            if (!isKilled(attacker)) {
                println("No dodge card, no dismantle card, damage taken. Current HP: $currentHP")
                println("[Stauchness] Xiahou Dun uses Stauchness")
                if (player is Lord) {
                    player.notifyObservers(false)
                }

                var card = CardManager.draw(this,1)

                if (card == null || card.isEmpty()) {
                    println("CARD IS NULL")
                    return
                }
                if (card[0].suit.equals("Heart"))
                {
                    if(Math.random() < 0.5)
                    {
                        var card = attacker.allCards.take(2)

                    }
                    else
                    {
                        attacker.currentHP--
                        attacker.isKilled(this)
                    }
                }
            } else {
                println(name + "'s Current HP less that or equal to zero")
            }

//            return false
        }
    }

}

class SimaYi(player: Player): WeiGeneral("Sima Yi",player) {
    override var maxHP = 3
    override var gender: String? = "female"
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
            if (!isKilled(attacker)) {
                println("No dodge card, damage taken. Current HP: $currentHP")
                println("[Retaliation] Sima Yi uses Retaliation")

                if (player is Lord) {
                    player.notifyObservers(false)
                }
                var card = attacker.allCards.take(1)
                if (card.isNotEmpty() && card[0].suit.equals("Heart"))
                {
                    if(Math.random() < 0.5)
                    {
                        var card = attacker.allCards.take(1)
                        allCards.add(card[0])
                    }
                    else
                    {
                        attacker.currentHP--
                        attacker.isKilled(this)
                    }
                }
            } else {
                println(name + "'s Current HP less that or equal to zero")
            }




//            return false
        }
    }
}

class GuoJia(player: Player): WeiGeneral("Guo Jia",player) {
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
            if(!isKilled(attacker))
            {

                println("[Last Strategy] Guo Jia uses Last Strategy")
                if (player is Lord) {
                    player.notifyObservers(false)
                }
                var card: List<Card> = CardManager.draw(this,1)


                println("CARD: " + card)

                if (GeneralManager.list.size == 0) {
                    println("NO Generals")
                    return
                }
                if(card.isNotEmpty()) {
                    GeneralManager.list.random().allCards.add(card[0])

                }
                card = CardManager.draw(this, 1)
                if(card.isNotEmpty()) {
                    GeneralManager.list.random().allCards.add(card[0])

                }
            }
//            return false
        }
    }
}

