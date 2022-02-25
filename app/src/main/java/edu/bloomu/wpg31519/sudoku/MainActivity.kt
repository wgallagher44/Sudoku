package edu.bloomu.wpg31519.sudoku

import Sudoku
import android.graphics.Color
import android.graphics.Color.BLUE
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/**
 * Creates and android app for the game of sudoku where the user trys to fill in the
 * board with numbers. But the constraint is you can't have the same number in the same
 * row or column and in the same 3x3 grid.
 */
class MainActivity : AppCompatActivity() {
    val ROW = 9
    val COL = 9
    var x = 0
    var y = 0
    var board = arrayOf(
        intArrayOf(0, 2, 6, 0, 0, 0, 0, 0, 4),
        intArrayOf(1, 0, 5, 0, 0, 9, 6, 2, 0),
        intArrayOf(9, 0, 0, 0, 6, 7, 0, 0, 0),
        intArrayOf(0, 0, 3, 1, 0, 8, 2, 0, 0),
        intArrayOf(0, 0, 2, 0, 0, 0, 1, 8, 7),
        intArrayOf(7, 1, 8, 6, 5, 0, 4, 3, 9),
        intArrayOf(3, 0, 0, 4, 0, 0, 0, 7, 0),
        intArrayOf(0, 4, 0, 7, 0, 0, 8, 5, 1),
        intArrayOf(0, 5, 7, 0, 0, 1, 0, 0, 2)
    )
    private val gridButtons = Array(ROW) { arrayOfNulls<Button>(COL) }
    private val playButtons = arrayOfNulls<Button>(9)
    var game = Sudoku(board)
    var gameBoard = game.getBoard()
//    var enterNumber = TextView(this)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Set layout parameters for table rows.
        val tableLayout = findViewById<TableLayout>(R.id.table_layout)


        val tableRowParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT
        )

        // Calculate size of each square so that the board occupies 80% of the screen.
        val displayWidth = resources.displayMetrics.widthPixels
        val displayHeight = resources.displayMetrics.heightPixels

        val n = kotlin.math.min(displayWidth, displayHeight)
        val k = kotlin.math.max(ROW,COL)
        val buttonSize = (8 * n / 10) / k

        // Create table rows and add them to the table for the sudoku board
        for (i in 0 until ROW) {
            val tableRow = TableRow(this)

            tableRow.layoutParams = tableRowParams

            // Add a button to each cell of this row.
            for (j in 0 until COL) {


                gridButtons[i][j] = Button(this)
                gridButtons[i][j]!!.width = buttonSize
                gridButtons[i][j]!!.height = buttonSize
                gridButtons[i][j]!!.textSize = 32f
                if(i >= 3  && i < 6 && j >=0 && j < 3  ){
                    gridButtons[i][j]!!.
                    setBackgroundResource(R.drawable.buttonboard1)
                }else if (j >= 6 && i >= 3 && i < 6) gridButtons[i][j]!!.
                setBackgroundResource(R.drawable.buttonboard1)
                else if (i >= 0 && i < 3 && j >= 3 && j < 6) gridButtons[i][j]!!.
                setBackgroundResource(R.drawable.buttonboard1)
                else if (i >= 6  && j >= 3 && j < 6)gridButtons[i][j]!!.
                setBackgroundResource(R.drawable.buttonboard1)




                tableRow.addView(gridButtons[i][j])
                gridButtons[i][j]!!.setOnClickListener(this::selectedBox)


            }
            tableLayout.addView(tableRow)
        }
        drawBoard()

    }


    /**
     * It gets the value from the edit text field and places it into the
     * sudkuo board if it is a valid move.
     */
     private fun selectedBox(view: View) {
         var number = findViewById<EditText>(R.id.editTextNumber).text.toString()
            if (number == ""){
                number = "0"
            }

        for (i in 0 until ROW) {
            for (j in 0 until COL) {
                if (view == gridButtons[i][j]) {
                    x = i
                    y = j
                    when{
                        number.toInt() == 0 -> gameBoard[i][j] = 0
                        !game.move(i,j,number.toInt()) ->Toast.makeText(applicationContext
                            ,"Invalid Move Try Again",Toast.LENGTH_LONG).show()
                        else -> game.move(i,j,number.toInt())
                    }

                }

            }
            drawBoard()




        }
    }

    /**
     * Solves the Sudoku board when the button is pressed
     */
     fun solve(view: View) {
         game.solve(0,0)
         drawBoard()
    }

    /**
     * Deletes at a specified clicked location
     */
    fun delete(view: View) {
        gameBoard[x][y] = 0
        drawBoard()
    }

    /**
     * Updates the board with the new symbols
     */
    private fun drawBoard(){
        for (i in 0 until ROW){
            for(j in 0 until COL){
                gridButtons[i][j]?.text =
                    when{
                        gameBoard[i][j] == 0 -> " "
                        else-> gameBoard[i][j].toString()
                    }
            }
        }
    }


}