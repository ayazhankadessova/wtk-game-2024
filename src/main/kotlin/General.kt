abstract class General {
    open var maxHP: Int = 4
    var currentHP: Int = 0
}

class LiuBei : General() {
    override var maxHP: Int = 5
}

class CaoCao : General() {
    override var maxHP: Int = 5
}

class SunQuan : General() {
    override var maxHP: Int = 5
}