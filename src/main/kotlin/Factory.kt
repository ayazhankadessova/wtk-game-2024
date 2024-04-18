import Generals.*

class LordFactory: GeneralFactory() {
    private val lords = mutableListOf("Liu Bei" , "Cao Cao", "Sun Quan" )
    //private val lords = mutableListOf("Sun Quan")

    //private val lords = mutableListOf("Liu Bei" , "Cao Cao" )

    override fun createRandomGeneral(player: Player): General {
        val name = lords.random()
        lords.remove(name)
        val general = when (name) {
            "Liu Bei" -> {
                val liuBei = LiuBei(createPlayer(1))
                liuBei.strategy = LiuBeiStrategy(liuBei)
                liuBei.currentHP = 1
                liuBei
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

open class NonLordFactory(private val generalLord: General, private val lord: Lord) : GeneralFactory()
{

//    private val nonLords = mutableListOf("Zhen Ji", "Xu Chu", "Sima Yi", "Diao Chan",
//        "Lv Bu", "Zhuge Liang", "Guan Yu", "Zheng Fei",
//        "Zhou Yu","Xiahou Dun","Zhang Liao", "Guo Jia",
//        "Yuan Shu","Hua Xiong","Hua Tuo","Sun Shangxiang",
//        "Lv Meng","Lu Xun","Huang Gai","Gang Ning",
//        "Da Qiao",)

    private val nonLords = mutableListOf("Zhen Ji", "Xu Chu", "Sima Yi", "Diao Chan",
        "Lv Bu", "Zhuge Liang", "Guan Yu", "Zheng Fei",
        "Zhou Yu","Xiahou Dun","Zhang Liao", "Guo Jia",
        "Yuan Shu","Hua Xiong", "Hua Tuo", "Sun Shangxiang", "Lv Meng", "Lu Xun", "Huang Gai", "Gang Ning",
        "Da Qiao", "Ma Chao","Huang Yueying","Zhao Yun")
    override fun createRandomGeneral(player: Player):General {
        val name = nonLords.random()
        nonLords.remove(name)
        var general = when (name)
        {
            "Zhen Ji" -> ZhenJi(player)
            "Xu Chu" -> XuChu(player)
            "Sima Yi" -> SimaYi(player)
            "Diao Chan" -> DiaoChan(player)
            "Lv Bu" -> LvBu(player)
            "Zhuge Liang" -> ZhugeLiang(player)
            "Guan Yu" -> GuanYuAdapter(GuanYu(), player)
            "Zheng Fei" -> ZhengFei(player)
            "Zhou Yu" -> ZhouYu(player)
            "Xiahou Dun" -> XiahouDun(player)
            "Zhang Liao" -> ZhangLiao(player)
            "Guo Jia" -> GuoJia(player)
            "Yuan Shu" -> YuanShu(player)
            "Hua Xiong" -> HuaXiong(player)
            "Hua Tuo" -> HuaTuo(player)
            "Sun Shangxiang" -> SunShangxiang(player)
            "Lv Meng" -> LvMeng(player)
            "Lu Xun" -> LuXun(player)
            "Huang Gai" -> HuangGai(player)
            "Gang Ning" -> GangNing(player)
            "Da Qiao" -> DaQiao(player)
            "Ma Chao" -> MaChao(player)
            "Huang Yueying"-> HuangYueying(player)
            "Zhao Yun" -> ZhaoYun(player)

            else -> throw IllegalArgumentException("Invalid Name")
        }
        general.currentHP = general.maxHP
        println(name + " ," +" a "+ general.identity +", has " + general.currentHP + " health point(s).")
        if (player is Spy) {
            lord.addObserver(player)
            println(general.name + " is observing lord.")
        }
        if (generalLord is WeiGeneral && general is WeiGeneral) {
            var current = generalLord as WeiGeneral
            while (current?.nextInChain != null) {
                current = current.nextInChain!!
            }
            current?.nextInChain = general
            println("${general.name} added to the Wei chain.")
        }
        if (generalLord is ShuGeneral && general is ShuGeneral) {
            var current = generalLord as ShuGeneral
            while (current?.nextInChain != null) {
                current = current.nextInChain!!
            }
            current?.nextInChain = general
            println("${general.name} added to the Shu chain.")
        }
        if (generalLord is WuGeneral && general is WuGeneral) {
            var current = generalLord as WuGeneral
            while (current?.nextInChain != null) {
                current = current.nextInChain!!
            }
            current?.nextInChain = general
            println("${general.name} added to the Wu chain.")
        }
        if(general.player is Rebel)
        {
            general.strategy = RebelStrategy(general)

        }
        else
        {
            general.strategy = LoyalistStrategy(general)

        }
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