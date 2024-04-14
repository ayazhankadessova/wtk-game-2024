object GeneralManager {
    private val generals: MutableList<General> = mutableListOf()
    private val lordFactory = LordFactory()
    private val nonLordFactory = NonLordFactory()

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

    fun createGenerals(lords: Int, nonLords: Int) {
        repeat(lords) {
            val lord = lordFactory.createRandomGeneral()
            addGeneral(lord)
        }
        repeat(nonLords) {
            val nonLord = nonLordFactory.createRandomGeneral()
            addGeneral(nonLord)
        }
    }
}

fun main() {
    GeneralManager.createGenerals(3, 3)
    println("Total number of generals: ${GeneralManager.getGeneralCount()}")
}