import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
class WeiTest {
    @Test
    fun testEntourage()
    {
        var caoCao: CaoCao = CaoCao(Lord())
        GeneralManager.generals.add(caoCao)
        val nonLordFactory = NonLordFactory(caoCao, caoCao.player as Lord)
        val weiOnlyNonLordFactory =WeiOnlyNonLordFactory(nonLordFactory,caoCao)
        for(i in 0 until 3)
        {
            GeneralManager.generals.add(weiOnlyNonLordFactory.createRandomGeneral(weiOnlyNonLordFactory.createPlayer(i+2)))
        }
        Assert.assertTrue(caoCao.entourage())
    }
}

class WeiOnlyNonLordFactory(val nonLordFactory: NonLordFactory, val caoCao: CaoCao): NonLordFactory(caoCao as WeiGeneral, caoCao.player as Lord)
{

    override fun createRandomGeneral(player: Player): General {
        var general:General? = null
        while (true)
        {
            general = nonLordFactory.createRandomGeneral(player)
            if(general is WeiGeneral)
            {
                return general
            }
            else
            {
                println(general.name + " is discarded as he/she is not a Wei.")
            }
        }
    }
}