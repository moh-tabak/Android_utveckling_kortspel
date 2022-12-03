package com.homework.cardgame

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity() {

    private lateinit var playerLayout :LinearLayout
    private lateinit var opponentLayout :LinearLayout
    private lateinit var tableLayout :TableLayout
    private lateinit var clubsStack :LinearLayout
    private lateinit var spadesStack: LinearLayout

    private var playerHand = Hand()
    private var opponentHand = Hand()
    private var table = Table()
    private var deck = Deck(arrayOf(CardSuits.CLUBS, CardSuits.SPADES))
    private val fileIndex = FileIndex()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        playerLayout = findViewById(R.id.player_layout)
        opponentLayout = findViewById(R.id.opponent_layout)
        tableLayout = findViewById(R.id.table_layout)
        clubsStack = tableLayout.findViewById(R.id.clubs_stack)
        spadesStack = tableLayout.findViewById(R.id.spades_stack)

        deck.shuffle()
        val cardCollections = deck.split(2)
        playerHand.addCollection(cardCollections[0])
        playerHand.organize()
        renderPlayerHand()
        opponentHand.addCollection(cardCollections[1])
        renderOpponentHand()
    }
    private fun renderPlayerHand(){
        var id :Int = 0
        for(card in playerHand.cards){
            renderCard(card,playerLayout,id)
            //If it's not the last card to the left, shift the cards' margin to the left
            if(id==playerHand.cards.count() - 1)
                findViewById<ImageView>(id).layoutParams = setCardMargins(5,5,5,5)
            else
                findViewById<ImageView>(id).layoutParams = setCardMargins(-70,5,5,5)
            findViewById<ImageView>(id).setOnClickListener {
                tryPlayCard(it.id)
            }
            id++
        }
    }

    private fun renderOpponentHand(){
        findViewById<TextView>(R.id.opponent_card_count_text).text= opponentHand.cards.count().toString()
    }

    @SuppressLint("ResourceType")
    private fun renderTable(){
        table.getStacks().forEach{
            if(it.suit==CardSuits.CLUBS){
                renderCard(Card(CardSuits.CLUBS,it.highestValue),clubsStack,114)
                clubsStack.findViewById<ImageView>(114).layoutParams = setCardMargins(5,5,5,5)
                renderCard(Card(CardSuits.CLUBS,10),clubsStack,115)
                clubsStack.findViewById<ImageView>(115).layoutParams = setCardMargins(5,-90,5,5)
                renderCard(Card(CardSuits.CLUBS,it.lowestValue),clubsStack,116)
                clubsStack.findViewById<ImageView>(116).layoutParams = setCardMargins(5,-90,5,5)
            }
            if(it.suit==CardSuits.SPADES){
                renderCard(Card(CardSuits.SPADES,it.highestValue),spadesStack,214)
                spadesStack.findViewById<ImageView>(214).layoutParams = setCardMargins(5,5,5,5)
                renderCard(Card(CardSuits.SPADES,10),spadesStack,215)
                spadesStack.findViewById<ImageView>(215).layoutParams = setCardMargins(5,-90,5,5)
                renderCard(Card(CardSuits.SPADES,it.lowestValue),spadesStack,216)
                spadesStack.findViewById<ImageView>(216).layoutParams = setCardMargins(5,-90,5,5)
            }
        }
    }

    private fun renderCard(card: Card, layout : LinearLayout, id :Int){
        val cardView = ImageView(baseContext)
        cardView.id = id
        cardView.setImageResource(fileIndex.getCardRes(card))
        layout.addView(cardView)
    }

    private fun tryPlayCard(viewId :Int){
        Toast.makeText(baseContext,"card $viewId clicked",Toast.LENGTH_SHORT).show()
    }

    private fun setCardMargins(left:Int,top:Int,right:Int,bottom:Int) :LinearLayout.LayoutParams {
        val params = LinearLayout.LayoutParams(120,160)
            params.setMargins(left,top,right,bottom)
        return params
    }
}