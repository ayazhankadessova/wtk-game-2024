interface Observer {
    fun update(dodged: Boolean)
}

interface Subject {
    fun addObserver(observer: Observer)
    fun removeObserver(observer: Observer)
    fun notifyObservers(dodged: Boolean)
}