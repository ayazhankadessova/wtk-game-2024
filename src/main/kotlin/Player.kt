interface Player {
    var currentHP: Int
    var identity: String

}

class Lord() : Player {
    override var currentHP = 0
    override var identity = "Lord"
}

class Loyalist() : Player {
    override var currentHP = 0
    override var identity = "Lord"
}

class Spy() : Player {
    override var currentHP = 0
    override var identity = "Spy"
}

class Rebel() : Player {
    override var currentHP = 0
    override var identity = "Rebel"
}