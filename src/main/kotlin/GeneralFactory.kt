abstract class GeneralFactory {
    abstract fun createRandomGeneral(player: Player):General
    abstract fun createPlayer(index:Int):Player
}
