package com.homework.cardgame


class Table {
    private var stacks = arrayListOf<Stack>()

    fun getStacks() :ArrayList<Stack>{
        return stacks
    }

    fun addCard(card: Card){
        if(card.value == 10){
            stacks.add(Stack(card.suit,10,10))
        }else{
            val stack = getStack(card.suit)
            if(card.value < 10){
                stacks[stacks.indexOf(stack)].lowestValue --
            }
            if(card.value >10){
                stacks[stacks.indexOf(stack)].highestValue ++
            }
        }
    }

    fun isCardPlayable(card :Card) :Boolean{
        val stack = getStack(card.suit)
        if(stack == null) {
            //Stack doesn't exist on the table
            if (card.value == 10)
                return true
        }else{
            //Stack already exists
            if(card.value < 10){
                if(card.value == (stack.lowestValue - 1))
                    return true
            }
            if(card.value > 10){
                if(card.value == stack.highestValue + 1) {
                    return true
                }
            }
        }
        return false
    }

    private fun getStack(suit: CardSuits) :Stack?{
        return stacks.firstOrNull{ it.suit === suit }
    }

    public fun clear() {
        stacks = arrayListOf<Stack>()
    }
}