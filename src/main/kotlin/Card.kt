typealias TimeSpellCard = () -> Unit

// Command function generator
typealias CommandGenerator = (Player) -> () -> Unit

val Acedia:  CommandGenerator = { player ->
    {
        if(player is General)
        {
            player.acediaAction()
        }
    }
}


//val targetPlayer = // ...
//val attackCard = AttackCard("Attack", "1", "Spade")
//val attackAction = attackCard.attack(targetPlayer)
//attackAction()

open class AttackCard(name: String, num: String, suit: String, var attacker: General?) : Card(name, num, suit) {
    open var attackRange = 1
    override val execute: CommandGenerator = { target ->
        {
            if(attacker == null)
            {
                println("Attacker is null")
            }
            else if(target is General && attacker?.distance?.get(target.name) == null)
            {
                println("Target is null")
            }
            else if (target is General && attacker?.distance?.get(target.name)!! <= attackRange) {
                println("Attack is within the range")
                attacker?.let { target.beingAttacked(attacker!!) }
                attacker?.removeOneAttackCard(this)

            } else {
                println("Attack is not in range")
            }
        }
    }
    fun changeAttacker(general: General?)
    {
        attacker = general
    }
}

// val duelAction = Duel(sourcePlayer)
// duelAction(targetPlayer)
// Command function generator for Duel

class DuelCard(name: String, num: String, suit: String, private var source: General?) : Card(name, num, suit) {
    override val execute: CommandGenerator = { target ->
        {
            if (target is General) {

                source?.challenge(target)
            }


        }
    }

    open fun changeSource(general: General) {
        source=general
    }
}

// how to use:
// val dismantleAction = Dismantle(sourcePlayer)
// dismantleAction(targetPlayer)


class DismantleCard(name: String, num: String, suit: String) : Card(name, num, suit) {
    override val execute: CommandGenerator = { target ->
        {
            if (target is General) {
                target.dismantleCard()
            }
        }
    }
}

//val player = // ...
//val eightTrigramsCard = EightTrigramsCard("Eight Trigrams", "1", "Spade")
//val eightTrigramsAction = eightTrigramsCard.execute(player)
//eightTrigramsAction()

// ARMOR Card
class EightTrigrams(name: String, num: String, suit: String) : Card(name, num, suit) {
    override val execute: CommandGenerator = { player ->
        {
            if (player is General) {
                player.dodge(suit)
            }
        }
    }
}



abstract class Card(val name: String, val num: String,public val suit: String) {
    // Capital letter for every suits
    val suitList: List<String> = listOf("Diamond", "Club", "Heart", "Spade")
    open val execute: CommandGenerator = { { } }

}