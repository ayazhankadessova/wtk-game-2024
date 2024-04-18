package equipments

import General

abstract class RangeCard(name: String, num: String, suit: String): EquipmentCard(name, num, suit) {
    val range: Int = 1

    abstract fun range(target: General)

}

abstract class Plus1(name: String, num: String, suit: String): RangeCard(name, num, suit) {
    // @2 -1 card:
    // this can increase the range between players by 1.
    // general.atttackRange += 1
    // declare general as a variable in the class
    override fun range(general: General) {
        general.attackRange += 1
    }


}

class ShadowRunner: Plus1("Shadow Runner", "5", "Spade") {

}

class HexMark: Plus1("Hex Mark", "5", "Club") {

}

class FlyingLightning: Plus1("Flying Lightning", "K", "Heart") {

}



abstract class Minus1(name: String, num: String, suit: String): RangeCard(name, num, suit) {
    // @2 +1 card
    // Function: +1 range between players
    override fun range(general: General) {
        general.distance[general.name] = general.attackRange + 1
    }

}

class FerghanaHorse: Minus1("Ferghana Horse", "K", "Spade") {

}

class RedHare: Minus1("Red Hare", "5", "Heart") {

}

class VioletStallion: Minus1("Violet Stallion", "K", "Diamond") {

}