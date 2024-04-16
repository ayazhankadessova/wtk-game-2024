abstract class General(val name: String, val player: Player) : Player by player {
    open var maxHP: Int = 4

    var numOfCards:Int = 4
    var skipPlay = false

    init {
        println("General $name created.")
        }

    fun playTurn()
    {
        preparationPhrase()
        judgementPhrase()
        drawPhrase()
        if (!skipPlay) {
            playPhrase()
        }
//        playPhrase()
        discardPhrase()
        finalPhrase()
    }

    open fun preparationPhrase() {}

    open fun judgementPhrase()
    {

//        executeCommand()

    }

    open fun drawPhrase()
    {
        numOfCards+=2
        println(name + " draws 2 cards and now has " + numOfCards + " card(s).")
    }
    open fun playPhrase()
    {
        println(name + " enters the Play Phase")
//        playNextCard()
    }
    open fun discardPhrase()
    {
        println(name + " has " + numOfCards + " card(s), current HP is " + currentHP + "." )
        val discarded:Int = numOfCards - currentHP

        numOfCards = currentHP
        println(name + " discards " + discarded + " card(s), now has " + numOfCards + " card(s).")

    }

    open fun finalPhrase()
    {

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