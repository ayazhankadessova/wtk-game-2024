interface Subject {
    // for observer pattern
    fun addObserver(observer: Observer)
    fun removeObserver(observer: Observer)
    fun notifyObservers(dodged: Boolean)
}