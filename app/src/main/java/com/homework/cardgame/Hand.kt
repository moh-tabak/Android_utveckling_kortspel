package com.homework.cardgame

class Hand {
    public var cards = arrayListOf<Card>()

    public fun organize(){
        cards.sortedWith(compareBy<Card> { it.suit.getNumerical() }.thenBy { it.value })
    }

    public fun Add(card :Card){

    }

    public fun Remove(cardIndex :Int):Card{
        return Card(CardSuits.CLUBS,0)
    }
}