package com.homework.cardgame

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity() {

    private lateinit var playerLayout :LinearLayout
    private lateinit var opponentLayout :LinearLayout
    private lateinit var tableLayout :TableLayout
    private lateinit var clubsStack :LinearLayout
    private lateinit var spadesStack: LinearLayout

    private lateinit var playerHand : Hand
    private lateinit var opponentHand : Hand
    private lateinit var table : Table
    private var deck = Deck(arrayOf(CardSuits.CLUBS, CardSuits.SPADES))
    private val fileIndex = FileIndex()

    private var whoseTurn = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        playerLayout = findViewById(R.id.player_layout)
        opponentLayout = findViewById(R.id.opponent_layout)
        tableLayout = findViewById(R.id.table_layout)
        clubsStack = tableLayout.findViewById(R.id.clubs_stack)
        spadesStack = tableLayout.findViewById(R.id.spades_stack)

        startNewGame()
    }

    private fun startNewGame(){
        playerHand = Hand()
        opponentHand = Hand()
        table = Table()
        deck.shuffle()
        val cardCollections = deck.split(2)
        playerHand.addCollection(cardCollections[0])
        playerHand.organize()
        renderPlayerHand()
        opponentHand.addCollection(cardCollections[1])
        renderOpponentHand()
    }

    private fun tryPlayCard(viewId :Int){
        if(whoseTurn==1 && table.isCardPlayable(playerHand.cards[viewId])){
            table.addCard(playerHand.remove(viewId))
            renderPlayerHand()
            renderTable()
        }
    }

    private fun turnEnd(){
        if(isThereWinner()){
            playerLayout.removeAllViews()
            tableLayout.removeAllViews()
            return
        }
        if(whoseTurn == 2)
            whoseTurn = 1
        else
            whoseTurn++
    }

    private fun isThereWinner() : Boolean{
        if(playerHand.cards.isEmpty()){
            showPlayAgainDialog("You won!")
            return true
        }
        if(opponentHand.cards.isEmpty()){
            showPlayAgainDialog("You lost!")
            return true
        }
        return false
    }

    private fun renderPlayerHand(){
        playerLayout.removeAllViews()
        var id  = 0
        for(card in playerHand.cards){
            renderCard(card,playerLayout,id)
            //If it's not the last card to the left, translate the cards to the left
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

    private fun renderTable(){
        table.getStacks().forEach{
            when (it.suit) {
                CardSuits.CLUBS -> renderStack(clubsStack, it,110)
                CardSuits.SPADES -> renderStack(spadesStack, it, 210)
                CardSuits.HEARTS -> TODO()
                CardSuits.DIAMONDS -> TODO()
            }
        }
    }

    private fun renderCard(card: Card, layout : LinearLayout, id :Int){
        val cardView = ImageView(baseContext)
        cardView.id = id
        cardView.setImageResource(fileIndex.getCardRes(card))
        layout.addView(cardView)
    }

    private fun renderStack(stackLayout :LinearLayout, stack :Stack , cardStartIndex :Int){
        stackLayout.removeAllViews()
        var tenCardTopShift = 0
        var lowCardTopShift = 125
        if(stack.highestValue != 10){
            tenCardTopShift = 125
            lowCardTopShift = 160
            renderCard(Card(CardSuits.CLUBS,stack.highestValue),stackLayout,cardStartIndex)
            stackLayout.findViewById<ImageView>(cardStartIndex).layoutParams = setCardMargins(5,5,5,5)
        }
        renderCard(Card(CardSuits.CLUBS,10),stackLayout,cardStartIndex+1)
        stackLayout.findViewById<ImageView>(cardStartIndex + 1).layoutParams = setCardMargins(5,5 - tenCardTopShift,5,5)
        if(stack.lowestValue != 10){
        renderCard(Card(CardSuits.CLUBS,stack.lowestValue),stackLayout,cardStartIndex+2)
        stackLayout.findViewById<ImageView>(cardStartIndex + 2).layoutParams = setCardMargins(5,5 - lowCardTopShift,5,5)
        }
    }


    private fun setCardMargins(left:Int,top:Int,right:Int,bottom:Int) :LinearLayout.LayoutParams {
        val params = LinearLayout.LayoutParams(120,160)
        params.setMargins(left,top,right,bottom)
        return params
    }

    private fun showPlayAgainDialog(message :String){
        val dialog = Dialog(baseContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.play_again_dialog_layout)
        val body : TextView = dialog.findViewById(R.id.play_again_message)
        body.text = message
        val btnPlayAgain :Button= dialog.findViewById(R.id.btn_play_again)
        btnPlayAgain.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}