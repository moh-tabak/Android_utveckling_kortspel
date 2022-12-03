package com.homework.cardgame

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity() {

    private lateinit var playerLayout :LinearLayout
    private lateinit var opponentLayout :LinearLayout
    private lateinit var tableLayout :LinearLayout

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

        deck.shuffle()
        val cardCollections = deck.split(2)
        playerHand.addCollection(cardCollections[0])
        playerHand.organize()
        renderPlayerHand()
        opponentHand.addCollection(cardCollections[1])
    }
    private fun renderPlayerHand(){
        var id :Int = 0
        for(card in playerHand.cards){
            renderCard(card,playerLayout,id)
            val params = LinearLayout.LayoutParams(120,160)
            //Shift the cards' margin to the left, if it's not the last card to the left
            if(id==playerHand.cards.count() - 1)
                params.setMargins(5,5,5,5)
            else
                params.setMargins(-70,5,5,5)
            findViewById<ImageView>(id).layoutParams = params
            findViewById<ImageView>(id).setOnClickListener {
                tryPlayCard(it.id)
            }
            id++
        }
    }

    private fun renderTable(){
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
}