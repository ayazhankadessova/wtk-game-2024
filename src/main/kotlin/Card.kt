interface Command {
    fun execute()
}

class Acedia(private val receiver: General) :Command {
    override fun execute() {

        receiver.acediaAction()
    }
}