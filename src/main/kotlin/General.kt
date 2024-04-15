abstract class General(name: String, val player: Player) : Player by player {
    open var maxHP: Int = 4

    init {
        println("General $name created.")
        }
}

class LiuBei(player: Player) : General("Liu Bei", player) {
    override var maxHP: Int = 5
}

class CaoCao(player: Player) : General("Cao Cao", player) {
    override var maxHP: Int = 5
}

class SunQuan(player: Player) : General("Sun Quan", player) {
    override var maxHP: Int = 5
}