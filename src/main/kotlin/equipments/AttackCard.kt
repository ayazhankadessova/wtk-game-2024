package equipments

import AttackCard
import Card
import General
import GeneralManager

/*
abstract class AttackCard(name: String, num: String, suit: String) : EquipmentCard(name, num, suit) {
    // Number of card default = 1
    abstract var attackRange: Int

    init {
        println("Player has placed the attack card '$name' on his equipment deck ")
    }
}
*/

open class ZhuGe_Crossbow(name: String, num: String, suit: String, attacker:General?) : AttackCard(name, num, suit,attacker) {
    // 2 cards -> 2 classes {(), ()}
    override var attackRange = 1

    fun noLimitKills(general: General) {
        general.attackCards = 100
    }

}

class ZhuGe_Crossbow1 : ZhuGe_Crossbow("ZhuGe Crossbow", "A", "Club",null) {

}

class ZhuGe_Crossbow2 : ZhuGe_Crossbow("ZhuGe Crossbow", "A", "Diamond",null) {

}

class YinYangSwords : AttackCard("Yin Yang Swords", "2", "Spade",null) {
    override var attackRange = 1

    fun attackGender(general: General) {
        // If gender of the character attacked is different, attacked player can choose
        // 1. Discard 1 of the target player's on-hand cards.
        // 2. The attacking person can draw cards from the deck.

        // check whether the attacked player is of different gender
        val gender: String? = general.gender

        if(general.gender != gender) {
            general.listCard.removeAt(0)
            general.listCard.add(CardManager.originalCards.random())
        }

    }


}

class KiRinBow : AttackCard("KiRin Bow", "5", "Heart",null) {
    override var attackRange = 5

    // attacking player can choose to discard the rangeCard of the attacked person
    fun discardRangeCard(general: General) {
        // If attacked player has range card -> Discard
        // Else -> nothing happen
        if (general.toolCards.size > 0) {
            for (i in 0 until 2) {
                if (general.toolCards[i] is RangeCard) {
                    general.toolCards[i] = null
                    break
                }
            }
        }
    }
}

class RockCleavingAxe : AttackCard("Rock Cleaving Axe", "5", "Diamond",null) {
    override var attackRange = 3

    fun forceDamage(general: General) {
        // if kill card is evaded, player can discard 2 cards
        // including on hand and equipment to undergo force damage

    }
}

class BlueSteelBlade : AttackCard("Blue Steel Blade", "6", "Spade",null) {
    override var attackRange = 2

    fun armorInvalid() {
        // neglect the armor during the attack

    }
}

class GreenDragonBlade : AttackCard("Green Dragon Blade", "5", "Spade",null) {
    override var attackRange = 3

    fun killAgain() {
        // kill is evaded -> Can use another kill to the same target
        // i.e. at most 2 kills at the same time
    }
}

class SerpentSpear : AttackCard("Serpent Spear", "Q", "Spade",null) {
    override var attackRange = 3

    fun twoCardsOneKill(general: General) {
        // discard 2 on-hand cards as a kill
        // to make it simple, discard 2 cards randomly.
        if (general.listCard.size >= 2) {
            general.listCard.removeAt(0)
            general.listCard.removeAt(1)
        }
    }
}

class SkyPiercingHalberd : AttackCard("Sky Piercing Halberd", "Q", "Diamond",null) {
    override var attackRange = 3

    fun lastKill(general: General) {
        // if the attack card played is the last on-hand card of the player,
        // can kill at most 3 players by using 1 attack card within attack range

        //check whether the player is holding one attack card only
        var attackCardNumber = 0
        for (i in 0 until general.listCard.size) {
            if (general.listCard[i] is AttackCard) {
                attackCardNumber++
            }
        }
        //if the player is holding one attack card only, can target at most 3 players
        val attackList = mutableListOf<General?>()
        var count = 0
        if (attackCardNumber == 1) {
            while (true) {
                val name = GeneralManager.list.random()
                for (i in 0 until attackList.size) {
                    if (name.name == attackList[i]?.name) {
                        break
                    }
                }
                attackList.add(name)
                general.beingAttacked(name)
                count++

                if (count == 3) {
                    break
                }
            }
        }
    }
}

