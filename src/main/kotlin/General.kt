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

class XuChu(player:Player): General("Xu Chu",player) {
    override var maxHP = 3

}


class SimaYi(player:Player): General("Sima Yi",player) {
    override var maxHP = 3

}

class XiahouDun(player:Player): General("Xiahou Dun",player) {
    override var maxHP = 3

}


class GuanYu {
    val maximumHP = 4
}

class GuanYuAdapter(private val guanYu: GuanYu,player: Player) : Player, General("Guan Yu", player) {
    override var maxHP = guanYu.maximumHP
}