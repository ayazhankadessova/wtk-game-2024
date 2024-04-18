abstract class Strategy(val general: General) {

    open fun playNextCard() {
        attack()
    }

    abstract fun attack()
}

open class LoyalistStrategy(general: General) : Strategy(general) {
    override fun attack() {
        if (general.hasAttackCard()) {
            for (target in GeneralManager.list) {
                if (target.player is Rebel) {

                    if (general.hasAttackCard() && general.useAttackCard(target)) {
                        println(general.name + " spends a card to attack a rebel, " + target.name )
                        println(general.name + " now has " +general.allCards.size + " cards.")
                        target.beingAttacked(general)
                        break
                    }
                }
            }
        }
    }
}

class RebelStrategy(general: General) : Strategy(general) {
    override fun attack() {
        if (general.hasAttackCard()) {
            for (target in GeneralManager.list) {
                if (target.player is Lord) {
                    if (general.hasAttackCard() && general.useAttackCard(target)) {
                        println(general.name + " spends a card to attack a rebel, " + target.name )
//                strategy.general.removeOneAttackCard()
                        println(general.name + " now has " +general.allCards.size + " cards.")
                        target.beingAttacked(general)
                        break
                    }

                }
            }
        }
    }
}

class LiuBeiStrategy(general: General) : LoyalistStrategy(general) {

    var state : State
   init {
       if (general.currentHP < 2) {
           state = UnhealthyState(this)
       } else {
           state = HealthyState(this)
       }
   }

    override fun playNextCard() = state.playNextCard()

    fun shouldChangeToUnhealthy(): Boolean {

        if (general.currentHP < 2) {
            return true
        }
        return false

    }

    fun shouldChangeToHealthy(): Boolean {

        if (general.currentHP >= 2) {
            return true
        }
        return false
    }
}