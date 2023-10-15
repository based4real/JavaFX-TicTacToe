/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.bll;

import java.util.Arrays;

/**
 *
 * @author Stegger
 */
public class GameBoard implements IGameModel
{

    public boolean switchPlayer;
    private boolean draw;

    private int winner, turns;

    int[][] cachedBoard = new int[3][3];

    /**
     * Returns 1 for player 1, 2 for player 2.
     *
     * @return int Id of the next player.
     */
    public int getNextPlayer()
    {
        return switchPlayer ? 1 : 2;
    }

    /**
     * Attempts to let the current player play at the given coordinates. It the
     * attempt is succesfull the current player has ended his turn and it is the
     * next players turn.
     *
     * @param col column to place a marker in.
     * @param row row to place a marker in.
     * @return true if the move is accepted, otherwise false. If gameOver == true
     * this method will always return false.
     */
    public boolean play(int col, int row)
    {
        // Er der placeret på denne lokation? eller er spillet ovre?
        if (cachedBoard[row][col] != 0 || isGameOver())
            return false;

        // Indsæt spiller ID i multidimensional array for at kunne
        // holder øje med træk
        cachedBoard[row][col] = this.getNextPlayer();

        if (!isGameOver()) {
            switchPlayer = !switchPlayer;
            turns++;
        }

        return true;
    }


    public boolean isGameOver() {
        /*         [i][0] [i][1]  [i][2] -- erstat 0, 1, 2 med j i loop.
         * [j][0]    0   |   1   |   2
         *         ------+-----+-----
         * [j][1]    3   |   4   |   5
         *         -----+-----+-----
         * [j][2]    6   |   7   |   8
         */

        int horizontal = 0, vertical = 0, diagonal1 = 0, diagonal2 = 0;
        int player = this.getNextPlayer();

        for (int i = 0; i < cachedBoard.length; i++) {
            horizontal = 0; // Sætter den horizontale række til 0.
            vertical = 0;   // Sætter den vertikale rækker til 0.

            for (int j = 0; j < cachedBoard.length; j++) {
                if (turns >= cachedBoard.length * cachedBoard.length) {
                    this.draw = true;
                    return true;
                }

                if (cachedBoard[j][i] == player)
                    horizontal++;

                if (cachedBoard[i][j] == player)
                    vertical++;
            }

            if (cachedBoard[i][i] == player)
                diagonal1++;

            if (cachedBoard[i][cachedBoard.length - 1 - i] == player)
                diagonal2++;

            if (horizontal == cachedBoard.length || vertical == cachedBoard.length) {
                return true; // Player has won either horizontally or vertically
            }

            if (diagonal1 == cachedBoard.length || diagonal2 == cachedBoard.length) {
                return true;
            }
        }
        return false;
    }


    /**
     * Gets the id of the winner, -1 if its a draw.
     *
     * @return int id of winner, or -1 if draw.
     */
    public int getWinner()
    {
        return draw ? -1 : this.getNextPlayer();
    }

    /**
     * Resets the game to a new game state.
     */
    public void newGame()
    {
        Arrays.stream(cachedBoard).forEach(element -> Arrays.fill(element, 0));
        draw = false;
        turns = 0;
        switchPlayer = false;
        //switchPlayer = !switchPlayer;
    }

}
