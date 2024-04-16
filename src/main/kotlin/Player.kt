interface Player {
    var currentHP: Int
    var identity: String

    fun shouldHelpLord() : Boolean

}

class Lord() : Player {
    override var currentHP = 0
    override var identity = "Lord"
    override fun shouldHelpLord(): Boolean {
        return false
    }
}

class Loyalist() : Player {
    override var currentHP = 0
    override var identity = "Lord"

    override fun shouldHelpLord(): Boolean {
        return true
    }
}

class Spy() : Player {
    override var currentHP = 0
    override var identity = "Spy"

    override fun shouldHelpLord(): Boolean {
        return false
    }
}

class Rebel() : Player {
    override var currentHP = 0
    override var identity = "Rebel"

    override fun shouldHelpLord(): Boolean {
        return false
    }
}