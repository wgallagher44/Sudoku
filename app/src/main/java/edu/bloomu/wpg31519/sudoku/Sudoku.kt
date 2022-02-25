import java.util.concurrent.ThreadLocalRandom

/**
 * Backend for the game of sudoku
 * @author Will Gallagher
 */
class Sudoku {
    private var board: Array<IntArray>

    /**
     * Initializes the game board  of sudoku according to the array entered
     */
    constructor(board: Array<IntArray>) {
        this.board = board

    }

    /**
     * Places a number into a specified row and column if it is a valid move else it
     * returns false
     */
    fun move(row:Int,col:Int,value:Int):Boolean{
        if (isValid(row,col,value)){
            board[row][col]= value
            return true
        }
        return false
    }

    /**
     * Gets the game board
     */
    fun getBoard(): Array<IntArray> {
        return board
    }




    /**
     * Prints out the current 9x9 Sudoku board
     */
    fun printBoard() {
        for (i in board.indices) {
            if (i % 3 == 0 && i != 0) print("--------------------------\n")
            for (j in 0 until board[0].size) {
                if (j % 3 == 0) {
                    print(" | " + board[i][j])
                } else {
                    print(" " + board[i][j])
                }
            }
            println(" |")
        }
    }
    fun delete(row: Int,col :Int){
        board[row][col] = 0
    }


    /**
     * returns the coordinates of an empty cell in the sudoku board indicated by a zero.
     * The return value is the x and y coordinate as an array of integers.
     */
    fun isEmpty(row: Int, col: Int): Boolean {
        return board[row][col] == 0
        // it out of the range values on the board
//        for (int i = 1; i < board.length; i++) {
//            for (int j = 1; j < board[0].length; j++) {
//                if (board[i][j] == 0) {
//                    return true;
//                }
//            }
//
//        }
    }

    /**
     * Returns true if the sudoku board is solvable false otherwise
     */
    fun solvable(): Boolean {
        val original = board
        if (solve(0, 0)) {
            board = original
            return true
        }
        return false
    }

    /**
     * Returns true if there is a number greater than zero in a specified position
     */
    fun isNotEmpty(row: Int, col: Int): Boolean {
        return row < 0 && col < 0
    }

    /**
     * returns true if the number you are trying to insert is valid in the game a sudoku
     * where you can't have the same number in the same row or column or in
     * the corresponding 3x3 grid.
     *
     * @param row     is the x position where you are trying to insert the number into
     * @param col     is the y postion where you are trying to insert the number into
     * @param value is the number you are trying to insert into the sudoku board
     * @return
     */
    fun isValid(row: Int, col: Int, value: Int): Boolean {
        for (i in board.indices) {
            if (board[row][i] == value) return false
        }
        for (i in 0 until board[0].size) {
            if (board[i][col] == value) return false
        }
        val boxX = row / 3
        val boxY = col / 3
        for (i in boxX * 3 until boxX * 3 + 3) {
            for (j in boxY * 3 until boxY * 3 + 3) {
                if (board[i][j] == value) return false
            }
        }
        return true
    }

    /**
     * Solves the sudoku board
     */
    fun solve(row: Int, col: Int): Boolean {
        var row = row
        var col = col
        val N = 9
        if (row == N - 1 && col == N) return true


        if (col == N) {
            row++
            col = 0
        }


        if (board[row][col] != 0) return solve(row, col + 1)
        for (num in 1..9) {


            if (isValid(row, col, num)) {


                board[row][col] = num


                if (solve(row, col + 1)) return true
            }
            board[row][col] = 0
        }
        return false
    }



}