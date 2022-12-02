package com.homework.cardgame

class FileIndex {
    fun getCardFileName(card :Card) :String{
        return when(card.suit){
            CardSuits.CLUBS  -> {
                "@drawable/clubs_${card.value}"
            }
            CardSuits.HEARTS  -> {
                "@drawable/hearts_${card.value}"
            }
            CardSuits.SPADES -> {
                "@drawable/spades_${card.value}"
            }
            CardSuits.DIAMONDS -> {
                "@drawable/spades_${card.value}"
            }
        }
    }
}