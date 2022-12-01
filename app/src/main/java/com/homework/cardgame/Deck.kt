package com.homework.cardgame

class Deck(suits: Array<CardSuits>) {
    public var cards = arrayListOf<Card>()
    public var suits = arrayOf<CardSuits>()

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
        var i=0
        var pointer=0
        val result = ArrayList<ArrayList<Card>>()
        while (i<numberOfPlayers){
            pointer = i*splitSize
            result.add(ArrayList(cards.subList(pointer, pointer + splitSize -1)))
            i++
        }
        return result
    }
}