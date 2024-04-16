abstract class GeneralFactory {
    abstract fun createRandomGeneral(player: Player):General
    abstract fun createPlayer(index:Int):Player
}

class LordFactory: GeneralFactory() {
    private val lords = mutableListOf("Liu Bei" , "Cao Cao", "Sun Quan" )
//        private val lords = mutableListOf("Cao Cao")

    override fun createRandomGeneral(player: Player): General {
        val name = lords.random()
        lords.remove(name)
        var general = when (name) {
            "Liu Bei" -> {
                LiuBei(createPlayer(1))
            }
            "Cao Cao" -> CaoCao(createPlayer(1))
            "Sun Quan" -> SunQuan(createPlayer(1))
            else -> throw IllegalArgumentException("Invalid Name")
        }
        general.currentHP = general.maxHP
        println("$name, a ${general.identity}, has ${general.currentHP} health point(s).")
        return general
    }

    override fun createPlayer(index: Int): Player {
        return Lord()
    }
}

open class NonLordFactory(private val lord: Lord) : GeneralFactory()
{

    private val nonLords = mutableListOf("Xu Chu", "Sima Yi", "Xiahou Dun", "Guan Yu", "Zhou Yu", "Diao Chan")
    override fun createRandomGeneral(player: Player):General {
        val name = nonLords.random()
        nonLords.remove(name)
        var general = when (name)
        {
            "Xu Chu" -> XuChu(player)
            "Sima Yi" -> SimaYi(player)
            "Xiahou Dun" -> XiahouDun(player)
            "Guan Yu" -> GuanYuAdapter(GuanYu(), player)
            "Zhou Yu" -> ZhouYu(player)
            "Diao Chan" -> DiaoChan(player)
            else -> throw IllegalArgumentException("Invalid Name")
        }
        general.currentHP = general.maxHP
        println(name + " ," +" a "+ general.identity +", has " + general.currentHP + " health point(s).")

        return general

    }
    override fun createPlayer(index: Int): Player {

        var player = when (index)
        {
            2 -> Loyalist()
            3 -> Rebel()
            4 -> Spy()
            5 -> Rebel()
            6 -> Rebel()
            7 -> Loyalist()
            8 -> Rebel()
            9 -> Loyalist()
            10 -> Spy()
            else -> throw IllegalArgumentException("Invalid Index")
        }

        return player
    }
}