abstract class Strategy(val general: General) {

    open fun playNextCard() {
        attack()
    }

    abstract fun attack()
}

open class LoyalistStrategy(general: General) : Strategy(general) {
    override fun attack() {
        if (general.hasAttackCard()) {
            for (target in GeneralManager.generals) {
                if (target.player is Rebel) {
                    println(general.name + " spends a card to attack a rebel, " + target.name )
                    general.numOfCards--
                    println(general.name + " now has " + general.numOfCards + " cards.")
                    target.beingAttacked()
                    break
                }
            }
        }
    }
}

class RebelStrategy(general: General) : Strategy(general) {
    override fun attack() {
        if (general.hasAttackCard()) {
            for (target in GeneralManager.generals) {
                if (target.player is Lord) {
                    println(general.name + " spends a card to attack the Lord, " + target.name )
                    general.numOfCards--
                    println(general.name + " now has " + general.numOfCards + " cards.")
                    target.beingAttacked()
                    break
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

interface State {
    fun playNextCard()
}

class UnhealthyState(private var strategy: LiuBeiStrategy) : State {
    override fun playNextCard() {

        if (strategy.general.numOfCards >= 2) {
            strategy.general.numOfCards = strategy.general.numOfCards - 2
            strategy.general.currentHP +=1
            println("[Benevolence] Liu Bei gives away two cards and recovers 1 HP, now his HP is " + strategy.general.currentHP + ".")
        }
        if (strategy.shouldChangeToHealthy()) {
            strategy.state = HealthyState(strategy)
            println(strategy.general.name + " is now Healthy.")
        }
    }
}

class HealthyState(private val strategy: LiuBeiStrategy) : State {
    override fun playNextCard()  {
        // Activate Benevolence skill

        println("Healthy State!!")
        strategy.attack()
        if (strategy.shouldChangeToUnhealthy()) {
            strategy.state = UnhealthyState(strategy)
            println(strategy.general.name + " is now Unhealthy.")
        }
    }
}