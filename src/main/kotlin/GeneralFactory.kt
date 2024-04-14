abstract class GeneralFactory {
    abstract fun createRandomGeneral(): General
}

class LordFactory : GeneralFactory() {
    private val lords = listOf("LiuBei", "CaoCao", "SunQuan").toMutableList()

    override fun createRandomGeneral(): General {
        if (lords.isEmpty()) throw IllegalStateException("No more lords to generate")
        val lordName = lords.random()
        lords.remove(lordName)
        return when (lordName) {
            "LiuBei" -> LiuBei()
            "CaoCao" -> CaoCao()
            "SunQuan" -> SunQuan()
            else -> throw IllegalArgumentException("Invalid lord name")
        }.apply { currentHP = maxHP }
    }
}

class NonLord: General() {
    override var maxHP: Int = 5
}class NonLordFactory : GeneralFactory() {
    override fun createRandomGeneral(): General {
        return NonLord().apply { currentHP = maxHP }
    }
}

fun main() {
    val lordFactory = LordFactory()
    val nonLordFactory = NonLordFactory()

    repeat(3) {
        val lord = lordFactory.createRandomGeneral()
        GeneralManager.addGeneral(lord)
    }

    repeat(3) {
        val nonLord = nonLordFactory.createRandomGeneral()
        GeneralManager.addGeneral(nonLord)
    }

    println("Total number of generals: ${GeneralManager.getGeneralCount()}")
}