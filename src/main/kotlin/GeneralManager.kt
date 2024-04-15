object GeneralManager {
    private val generals: MutableList<General> = mutableListOf()
    private val lordFactory = LordFactory()

    fun addGeneral(general: General) {
        generals.add(general)
        println("General ${general::class.simpleName} created.")
    }

    fun removeGeneral(general: General) {
        generals.remove(general)
    }

    fun getGeneralCount(): Int {
        return generals.size
    }

    fun createGenerals( nonLords:Int){
        val lord = lordFactory.createRandomGeneral(lordFactory.createPlayer(1))
//        lord.setStrategy(RebelStrategy())
        generals.add(lord)
        var  nonLordFactory: NonLordFactory = NonLordFactory(lord.player as Lord)

        for(i in 0 until nonLords)
        {
            generals.add(nonLordFactory.createRandomGeneral(nonLordFactory.createPlayer(i+2)))    }
    }
}

fun main() {
    GeneralManager.createGenerals(3)
    println("Total number of generals: ${GeneralManager.getGeneralCount()}")
}