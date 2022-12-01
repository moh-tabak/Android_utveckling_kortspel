package com.homework.cardgame


class Card(suit :CardSuits , value :Int) {
    public var suit :CardSuits
    public var value :Int

    init {
        this.suit=suit
        this.value=value
    }
}

enum class CardSuits(numerical :Int){
    CLUBS(1){
         override fun getNumerical() = 1
         },
    HEARTS(2){
        override fun getNumerical() = 2
    },
    DIAMONDS(3){
        override fun getNumerical() = 3
    },
    SPADES(4){
        override fun getNumerical() = 4
    };

    abstract fun getNumerical() :Int
}