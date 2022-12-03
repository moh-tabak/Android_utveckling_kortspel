package com.homework.cardgame

class FileIndex {
    fun getCardRes(card :Card) :Int{
         when(card.suit){
            CardSuits.CLUBS  -> {
                return when(card.value){
                    1 -> R.drawable.clubs_1
                    2 -> R.drawable.clubs_2
                    3 -> R.drawable.clubs_3
                    4 -> R.drawable.clubs_4
                    5 -> R.drawable.clubs_5
                    6 -> R.drawable.clubs_6
                    7 -> R.drawable.clubs_7
                    8 -> R.drawable.clubs_8
                    9 -> R.drawable.clubs_9
                    10 -> R.drawable.clubs_10
                    11 -> R.drawable.clubs_11
                    12 -> R.drawable.clubs_12
                    13 -> R.drawable.clubs_13
                    else -> {throw Exception("card resource doesn't exist.")}
                }
            }
            CardSuits.SPADES -> {
                return when(card.value){
                    1 -> R.drawable.spades_1
                    2 -> R.drawable.spades_2
                    3 -> R.drawable.spades_3
                    4 -> R.drawable.spades_4
                    5 -> R.drawable.spades_5
                    6 -> R.drawable.spades_6
                    7 -> R.drawable.spades_7
                    8 -> R.drawable.spades_8
                    9 -> R.drawable.spades_9
                    10 -> R.drawable.spades_10
                    11 -> R.drawable.spades_11
                    12 -> R.drawable.spades_12
                    13 -> R.drawable.spades_13
                    else -> {throw Exception("card resource doesn't exist.")}
                }
            }
            CardSuits.HEARTS  -> {
                   throw Exception("card resource doesn't exist.")
            }
            CardSuits.DIAMONDS -> {
                throw Exception("card resource doesn't exist.")
            }
        }
    }
}