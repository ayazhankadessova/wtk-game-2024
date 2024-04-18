//interface Command {
//    fun execute()
//}

typealias Command = () -> Unit

// Command function generator
typealias CommandGenerator = (Player) -> () -> Unit

val Acedia:  CommandGenerator = { player ->
    {
        if(player is General)
        {
            player.acediaAction()

        }
    }
}

//class Acedia(private val receiver: General) :Command {
//    override fun execute() {
//
//        receiver.acediaAction()
//    }
//}