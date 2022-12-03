package com.homework.cardgame


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class GameActivity : AppCompatActivity() {

    private lateinit var playerLayout :LinearLayout
    private lateinit var opponentLayout :LinearLayout
    private lateinit var tableLayout :TableLayout
    private lateinit var clubsStack :LinearLayout
    private lateinit var spadesStack: LinearLayout
    private lateinit var btnPass :Button

    private lateinit var playerHand : Hand
    private lateinit var opponentHand : Hand
    private lateinit var table : Table
    private var deck = Deck(arrayOf(CardSuits.CLUBS, CardSuits.SPADES))
    private val fileIndex = FileIndex()

    private var playerTurn :Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        playerLayout = findViewById(R.id.player_layout)
        opponentLayout = findViewById(R.id.opponent_layout)
        tableLayout = findViewById(R.id.table_layout)
        clubsStack = tableLayout.findViewById(R.id.clubs_stack)
        spadesStack = tableLayout.findViewById(R.id.spades_stack)
        btnPass = findViewById(R.id.btn_pass)
        btnPass.setOnClickListener{
            endTurn()
            btnPass.visibility = View.INVISIBLE
        }

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
        opponentHand.organize()
        renderOpponentHand()
        playerTurn = true
        if (getPlayableCards(playerHand).isEmpty()) {
            endTurn()
        }
    }

    private fun getPlayableCards(hand: Hand) :ArrayList<Card>{
        val result = arrayListOf<Card>()
        for(card in hand.cards){
            if(table.isCardPlayable(card))
                result.add(card)
        }
        return result
    }

    private fun onCardClick(viewId :Int){
        if(playerTurn && table.isCardPlayable(playerHand.cards[viewId])){
            table.addCard(playerHand.remove(viewId))
            renderPlayerHand()
            renderTable()
            endTurn()
        }
    }

    private fun endTurn(){
        val textView : TextView = findViewById(R.id.turn_text)
        if(isThereWinner()){
            //match ended
            playerLayout.removeAllViews()
            tableLayout.removeAllViews()
            return
        }

        if(!playerTurn){
            textView.text="Your Turn"
            playerTurn =true
            if (getPlayableCards(playerHand).isEmpty()) {
                //Show Pass button
                btnPass.visibility = View.VISIBLE
            }
        } else {
        //AI's turn
            textView.text = "AI's Turn"
            playerTurn = false
            aiPlay()
        }
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

    private fun aiPlay(){
        var playableCards = getPlayableCards(opponentHand)
        if (playableCards.isEmpty()) {
            endTurn()
        } else {
            val randomIndex = Random.nextInt(playableCards.count())
            table.addCard(opponentHand.remove(opponentHand.cards.indexOf(playableCards[randomIndex])))
            renderOpponentHand()
            renderTable()
            endTurn()
        }
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
                onCardClick(it.id)
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
        var tenCardMarginTopShift = 0
        var lowCardMarginTopShift = 125
        if(stack.highestValue != 10){
            tenCardMarginTopShift = 125
            lowCardMarginTopShift = 160
            renderCard(Card(stack.suit,stack.highestValue),stackLayout,cardStartIndex)
            stackLayout.findViewById<ImageView>(cardStartIndex).layoutParams = setCardMargins(5,5,5,5)
        }
        renderCard(Card(stack.suit,10),stackLayout,cardStartIndex+1)
        stackLayout.findViewById<ImageView>(cardStartIndex + 1).layoutParams = setCardMargins(5,5 - tenCardMarginTopShift,5,5)
        if(stack.lowestValue != 10){
            renderCard(Card(stack.suit,stack.lowestValue),stackLayout,cardStartIndex+2)
            stackLayout.findViewById<ImageView>(cardStartIndex + 2).layoutParams = setCardMargins(5,5 - lowCardMarginTopShift,5,5)
        }
    }

    private fun setCardMargins(left:Int,top:Int,right:Int,bottom:Int) :LinearLayout.LayoutParams {
        val params = LinearLayout.LayoutParams(120,160)
        params.setMargins(left,top,right,bottom)
        return params
    }

    private fun showPlayAgainDialog(message :String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val viewGroup = findViewById<ViewGroup>(androidx.appcompat.R.id.content)
        val dialogView: View =
            LayoutInflater.from(this).inflate(R.layout.play_again_dialog, viewGroup, false)
        builder.setView(dialogView)
        dialogView.findViewById<TextView>(R.id.play_again_message)?.text = message

        val alertDialog: AlertDialog = builder.create()
        dialogView.findViewById<Button>(R.id.btn_play_again)?.setOnClickListener{
            alertDialog.hide()
            startNewGame()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}