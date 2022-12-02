package com.homework.cardgame

enum class CardSuits(){
    CLUBS{
         override fun getNumerical() = 1
         },
    HEARTS{
        override fun getNumerical() = 2
    },
    DIAMONDS{
        override fun getNumerical() = 3
    },
    SPADES{
        override fun getNumerical() = 4
    };

    abstract fun getNumerical() :Int
}