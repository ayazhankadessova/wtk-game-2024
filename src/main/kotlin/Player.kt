interface Player {
    var currentHP: Int
    var identity: String

    fun shouldHelpLord() : Boolean

}

class Lord() : Player, Subject {
    override var currentHP = 0
    override var identity = "Lord"
    override fun shouldHelpLord(): Boolean {
        return false
    }

    private val observers = mutableListOf<Observer>()

    override fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    override fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyObservers(dodged: Boolean) {
        for (observer in observers) {
            observer.update(dodged)
        }
    }
}

class Loyalist() : Player {
    override var currentHP = 0
    override var identity = "Lord"

    override fun shouldHelpLord(): Boolean {
        return true
    }
}

class Spy() : Player, Observer {
    override var currentHP = 0
    override var identity = "Spy"
    var riskLevel = 0

    override fun shouldHelpLord(): Boolean {
        // Determine whether to help the Lord based on the risk level
        return riskLevel > 5
    }

    override fun update(dodged: Boolean) {
        // Update risk level based on whether the Lord dodged the attack
        riskLevel = if (dodged) riskLevel + 5 else riskLevel +10
        println("Current risk level, info for observers: $riskLevel")
    }
}

class Rebel() : Player {
    override var currentHP = 0
    override var identity = "Rebel"

    override fun shouldHelpLord(): Boolean {
        return false
    }
}