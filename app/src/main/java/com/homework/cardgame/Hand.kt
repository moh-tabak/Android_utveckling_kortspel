package com.homework.cardgame

class Hand {
    public var cards = arrayListOf<Card>()

    public fun organize(){
        cards = ArrayList(cards.sortedWith(compareBy<Card> { it.suit.getNumerical() }.thenBy { it.value }))
    }

    public fun add(card :Card){
        cards.add(card)
    }

    public fun addCollection(cardCollection :ArrayList<Card>){
        cards.addAll(cardCollection)
    }

    public fun remove(cardIndex :Int):Card{
        var result = cards[cardIndex]
        cards.removeAt(cardIndex)
        return result
    }
}