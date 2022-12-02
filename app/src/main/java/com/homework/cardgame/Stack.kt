package com.homework.cardgame

class Stack(suit :CardSuits, highestValue :Int, lowestValue :Int) {
    var suit :CardSuits
    var highestValue :Int
    var lowestValue :Int

    init {
        this.suit = suit
        this.highestValue = highestValue
        this.lowestValue = lowestValue
    }
}