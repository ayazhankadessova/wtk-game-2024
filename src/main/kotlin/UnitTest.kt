import equipments.*

//import Generals.CaoCao
//import org.junit.Assert.assertFalse
//import org.junit.Assert.assertTrue
//import org.junit.Before
//import org.junit.Test
////
////class WeiTest {
////    @Test
////    fun testEntourage()
////    {
////        var caoCao: CaoCao = CaoCao(Lord())
////        GeneralManager.list.add(caoCao)
////        val nonLordFactory = NonLordFactory(caoCao, caoCao.player as Lord)
////        val weiOnlyNonLordFactory =WeiOnlyNonLordFactory(nonLordFactory,caoCao)
////        for(i in 0 until 3)
////        {
////            GeneralManager.list.add(weiOnlyNonLordFactory.createRandomGeneral(weiOnlyNonLordFactory.createPlayer(i+2)))
////        }
////        assertTrue(caoCao.entourage())
////    }
////}
////
////
////class WeiOnlyNonLordFactory(val nonLordFactory: NonLordFactory, val caoCao: CaoCao): NonLordFactory(caoCao as WeiGeneral, caoCao.player as Lord)
////{
////
////    override fun createRandomGeneral(player: Player): General {
////        var general:General? = null
////        while (true)
////        {
////            general = nonLordFactory.createRandomGeneral(player)
////            if(general is WeiGeneral)
////            {
////                return general
////            }
////            else
////            {
////                println(general.name + " is discarded as he/she is not a Wei.")
////            }
////        }
////    }
////}
//
//class CardManagerTest {
//    lateinit var general: `General.kt`
//    lateinit var card1: Card
//    lateinit var card2: Card
//
//    @Before
//    fun setup() {
//        // Initialize the General and Cards
//        var caoCao: CaoCao = CaoCao(Lord())
//        var card1: Card = Acedia()
//        var card2: Card = Acedia()
//
//        // Reset the CardManager
//        CardManager.originalCards.clear()
//        CardManager.discarded.clear()
//    }
//
//    @Test
//    fun testDiscard() {
//        CardManager.discard(card1)
//        assertTrue(CardManager.discarded.contains(card1))
//    }
//
//    @Test
//    fun testDraw() {
//        CardManager.originalCards.add(card1)
//        CardManager.originalCards.add(card2)
//        CardManager.draw(general)
//
//        // Verify that the cards were removed from the originalCards
//        assertFalse(CardManager.originalCards.contains(card1))
//        assertFalse(CardManager.originalCards.contains(card2))
//
//        // Verify that the cards were added to the general's listCards
//        Mockito.verify(general.listCards).addAll(listOf(card1, card2))
//    }
//
//    @Test
//    fun testDrawWithShuffle() {
//        CardManager.discarded.add(card1)
//        CardManager.discarded.add(card2)
//        CardManager.draw(general)
//
//        // Verify that the cards were removed from the discarded
//        assertFalse(CardManager.discarded.contains(card1))
//        assertFalse(CardManager.discarded.contains(card2))
//
//        // Verify that the cards were added to the general's listCards
//        Mockito.verify(general.listCards).addAll(listOf(card1, card2))
//    }
//}
//fun main(args: Array<String>)
//{
////    val weiTest = WeiTest()
////    weiTest.testEntourage()
//
//    val CardManagerTest = CardManagerTest()
//    CardManagerTest.setup()
//    CardManagerTest.testDiscard()
//}

fun main() {
    GeneralManager.createGenerals(4)

    println("Total number of generals: " + GeneralManager.getGeneralCount())
    println()

    println("Testing +1 Range Card...")

    GeneralManager.list[0].toolCards.add(ShadowRunner())
    ShadowRunner().range(GeneralManager.list[0])
    println("${GeneralManager.list[0].name} has added " +
            "${GeneralManager.list[0].toolCards[0]?.name} to his/her equipment deck")
    println("${GeneralManager.list[0].name} has an attack range of ${GeneralManager.list[0].attackRange}")
    println()

    println("Testing -1 Range Card...")
    GeneralManager.list[1].toolCards.add(FerghanaHorse())
    FerghanaHorse().range(GeneralManager.list[1])
    println("${GeneralManager.list[1].name} has added " +
            "${GeneralManager.list[1].toolCards[0]?.name} to his/her equipment deck")
    println("${GeneralManager.list[1].name} has an distance of ${GeneralManager.list[1].distance[GeneralManager.list[1].name]}")
}