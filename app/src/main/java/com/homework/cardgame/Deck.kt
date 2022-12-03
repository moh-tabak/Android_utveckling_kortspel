package com.homework.cardgame

class Deck(suits: Array<CardSuits>) {
    var cards = arrayListOf<Card>()
    var suits = arrayOf<CardSuits>()

    init {
        this.suits = suits
        suits.forEach {
            var i = 1
            while (i <= 13){
                cards.add(Card(it,i))
                i++
            }
        }
    }

    public fun shuffle(){
        cards.shuffle()
    }

    public fun split(numberOfPlayers :Int) : ArrayList<ArrayList<Card>>{
        val splitSize :Int = cards.count()/numberOfPlayers
        var pointer=0
        val result = ArrayList<ArrayList<Card>>()
        for (i in 0 until numberOfPlayers){
            pointer = i * splitSize
            result.add(ArrayList(cards.subList(pointer, pointer + splitSize)))
        }
        cards.removeAll(cards.toSet())
        return result
    }
}