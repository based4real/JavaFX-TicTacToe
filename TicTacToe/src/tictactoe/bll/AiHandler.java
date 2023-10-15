package tictactoe.bll;

import com.sun.security.jgss.GSSUtil;

import java.util.Arrays;
import java.util.Random;

public class AiHandler {
    // Opret et board som vi bruger til at gå udfra
    private int[][]board = new int[3][3];
    private int[][]simulationBoard = new int[3][3];

    private final static int PLAYER_BOT = 1;
    private final static int PLAYER_ENEMY = 2;

    private int boardSize = board.length;
    private int winningMove = boardSize - 1;

    private boolean isValidMove(int c, int r, int player) {
        return board[c][r] == 0;
    }

    public void setMove(int c, int r, int player) {
        if (isValidMove(c, r, player))
            board[c][r] = player;
    }

    /**
     * Creates a random position on the board in order to play
     * from the beginning or further in the game.
     *
     * @return array with vertical and horizontal play.
     * this method will 0, 0.
     */
    private int[] getAIRandomMove() {
        for (int i = 0; i < boardSize*boardSize; i++) {
            Random ranCol = new Random();
            Random ranRow = new Random();

            int vertical = ranCol.nextInt(boardSize);
            int horizontal = ranRow.nextInt(boardSize);

            if (isValidMove(vertical, horizontal, PLAYER_BOT))
                return new int[] {vertical, horizontal};
        }

        return new int[]{0, 0};
    }

    //https://stackoverflow.com/a/13527997
    private static void arrayCopy(int[][] aSource, int[][] aDestination) {
        for (int i = 0; i < aSource.length; i++)
            System.arraycopy(aSource[i], 0, aDestination[i], 0, aSource[i].length);
    }

    /**
     * Simulating horizontal play in order to check if
     * we can put a play on that cell.
     *
     * @return array with possible solution to counter the enemy attack
     * this method will always return a random position on board.
     */
    private int[] simulateHorizontal(int vertical, int player) {
        arrayCopy(board, simulationBoard);

        int count = 0;
        for (int i = 0; i < simulationBoard.length; i++) {
            if (simulationBoard[vertical][i] == 0) {
                simulationBoard[vertical][i] = player;
                count++;
            }

            if (count == 1)
                return new int[]{vertical, i};
        }

        return getAIRandomMove();
    }

    /**
     * Simulating vertical play in order to check if
     * we can put a play on that cell.
     *
     * @return array with possible solution to counter the enemy attack
     * this method will always return a random position on board.
     */
    private int[] simulateVertical(int horizontal, int player) {
        arrayCopy(board, simulationBoard);

        int count = 0;
        for (int i = 0; i < simulationBoard.length; i++) {
            if (simulationBoard[i][horizontal] == 0) {
                simulationBoard[i][horizontal] = player;
                count++;
            }

            if (count == 1)
                return new int[]{i, horizontal};
        }
        return getAIRandomMove();
    }

    /**
     * Simulating three ways of diagonal in order to check if
     * we can put a play on that cell.
     *
     * @return array with possible solution to counter the enemy attack
     * this method will always return a random position on board.
     */
    private int[] simulateDiagonal(int way, int player) {
        arrayCopy(board, simulationBoard);

        int count = 0;
        for (int i = 0; i < simulationBoard.length; i++) {
            if (way == 1) {
                if (simulationBoard[i][i] == 0) {
                    simulationBoard[i][i] = player;
                    count++;
                }

                if (count == 1)
                    return new int[]{i, i};
            } else {
                int offset = simulationBoard.length - 1 - i;
                if (simulationBoard[i][offset] == 0) {
                    simulationBoard[i][offset] = player;
                    count++;

                    if (count == 1)
                        return new int[]{i, offset};
                }

                if (simulationBoard[offset][i] == 0) {
                    simulationBoard[offset][i] = player;
                    count++;

                    if (count == 1)
                        return new int[]{offset, i};
                }
            }

        }
        return getAIRandomMove();
    }

    /**
     * Attempts to make a valid move on the board using simulation and counting
     * location from the enemy player in order to counter the play.
     *
     * @return array with vertical and horizontal move made from the simulation
     * this method will always return a random position on board.
     */
    private int[] blockEnemy()
    {
        int horizontal = 0, vertical = 0, diagonal1 = 0, diagonal2 = 0;

        for (int i = 0; i < boardSize; i++) {
            horizontal = 0;
            vertical = 0;

            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == PLAYER_ENEMY)
                    horizontal++;

                if (board[j][i] == PLAYER_ENEMY)
                    vertical++;

                if (board[i][j] == PLAYER_BOT)
                    horizontal--;

                if (board[j][i] == PLAYER_BOT) {
                    vertical--;
                }
            }

            if (board[i][i] == PLAYER_ENEMY) {
                diagonal1++;
            }

            if (board[i][boardSize - 1 - i] == PLAYER_ENEMY)
                diagonal2++;

            if (board[i][i] == PLAYER_BOT)
                diagonal1--;

            if (board[i][boardSize - 1 - i] == PLAYER_BOT)
                diagonal2--;

            if (horizontal == winningMove)
                return simulateHorizontal(i, PLAYER_ENEMY);


            if (vertical == winningMove)
                return simulateVertical(i, PLAYER_ENEMY);
        }

        /* Put udenfor loop, ellers vil den ikke tage sidste række med i f.eks.
         * Bemærk X placering.
         *   2 | 2 | 1
         *  ---+---+---
         *   1 | 2 | 2
         *  ---+---+---
         *   0 | 0 | X
         *
         */
        if (diagonal1 == winningMove || diagonal2 == winningMove)
            return simulateDiagonal(diagonal1 == winningMove ? 1 : 0, PLAYER_ENEMY);

        return getAIRandomMove();
    }

    /**
     * Attempts to make a valid move on the board using simulation and counting
     * location from the bot player in order to try to win.
     *
     * @return array with vertical and horizontal move made from the simulation
     * this method will always return a random position on board.
     */
    private int[] botWinPos()
    {
        int horizontal = 0, vertical = 0, diagonal1 = 0, diagonal2 = 0;

        for (int i = 0; i < boardSize; i++) {
            horizontal = 0;
            vertical = 0;

            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == PLAYER_BOT)
                    horizontal++;

                if (board[j][i] == PLAYER_BOT)
                    vertical++;

                if (board[i][j] == PLAYER_ENEMY)
                    horizontal--;

                if (board[j][i] == PLAYER_ENEMY) {
                    vertical--;
                }
            }

            if (board[i][i] == PLAYER_BOT) {
                diagonal1++;
            }

            if (board[i][boardSize - 1 - i] == PLAYER_BOT)
                diagonal2++;

            if (board[i][i] == PLAYER_ENEMY)
                diagonal1--;

            if (board[i][boardSize - 1 - i] == PLAYER_ENEMY)
                diagonal2--;

            if (horizontal == winningMove)
                return simulateHorizontal(i, PLAYER_BOT);

            if (vertical == winningMove)
                return simulateVertical(i, PLAYER_BOT);
        }

        if (diagonal1 == winningMove || diagonal2 == winningMove)
            return simulateDiagonal(diagonal1 == winningMove ? 1 : 0, PLAYER_BOT);

        return new int[] {-1, -1};
    }


    public void reset() {
        Arrays.stream(board).forEach(element -> Arrays.fill(element, 0));
    }

    public int[] getThinking() {
        int[] botPos = botWinPos();

        return botPos[0] == -1 ? blockEnemy() : botPos;
    }
}
