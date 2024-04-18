interface State {
    fun playNextCard()
}

class UnhealthyState(private var strategy: LiuBeiStrategy) : State {
    override fun playNextCard() {

        if (strategy.general.allCards.size >= 2) {
            strategy.general.removeCard(strategy.general.allCards.random())
            strategy.general.removeCard(strategy.general.allCards.random())

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
        for (target in GeneralManager.list) {
            if (target.player is Rebel) {

                if (strategy.general.hasAttackCard() && strategy.general.useAttackCard(target)) {
                    println(strategy.general.name + " spends a card to attack a rebel, " + target.name )
                    println(strategy.general.name + " now has " +strategy. general.allCards.size + " cards.")
                    target.beingAttacked(GeneralManager.list[0])
                    break
                }
            }
        }
        if (strategy.shouldChangeToUnhealthy()) {
            strategy.state = UnhealthyState(strategy)
            println(strategy.general.name + " is now Unhealthy.")
        }
    }
}