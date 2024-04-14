object GeneralManager {
    private val generals: MutableList<General> = mutableListOf()

    fun addGeneral(general: General) {
        generals.add(general)
    }

    fun removeGeneral(general: General) {
        generals.remove(general)
    }

    fun getGeneralCount(): Int {
        return generals.size
    }
}

fun main() {
    val general1 = General("LIU Bei")
    val general2 = General("CAO Cao")
    val general3 = General("SUN Quan")

    GeneralManager.addGeneral(general1)
    GeneralManager.addGeneral(general2)
    GeneralManager.addGeneral(general3)

    val generalCount = GeneralManager.getGeneralCount()
    println("Number of generals: $generalCount")
}