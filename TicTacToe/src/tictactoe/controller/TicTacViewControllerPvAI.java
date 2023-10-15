/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tictactoe.bll.AiHandler;
import tictactoe.bll.GameBoard;
import tictactoe.bll.IGameModel;
import tictactoe.bll.PlayerHandler;

/**
 *
 * @author Stegger
 */
public class TicTacViewControllerPvAI implements Initializable
{
    public ImageView winImg;
    @FXML
    private Label scoreBoard;
    @FXML
    private Button btn1, btn2,btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    @FXML
    private Label lblPlayer;

    @FXML
    private GridPane gridPane;

    private static final String CLICKED_BUTTON_STYLE = "-fx-text-fill: white;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-text-fill: grey;";

    private static final String NORMAL_BORDER_COLOUR = "-fx-border-color: #26292e";
    private IGameModel game;
    private AiHandler ai;

    ArrayList<String> clickedButtons = new ArrayList<String>();
    Button[][] buttonsLocation = new Button[3][3];

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        ai = new AiHandler();

        // Ringe kode men er tom for ideer klokken 22 efter 5 timers kodning
        buttonsLocation[0][0] = btn1;
        buttonsLocation[1][0] = btn2;
        buttonsLocation[2][0] = btn3;

        buttonsLocation[0][1] = btn4;
        buttonsLocation[1][1] = btn5;
        buttonsLocation[2][1] = btn6;

        buttonsLocation[0][2] = btn7;
        buttonsLocation[1][2] = btn8;
        buttonsLocation[2][2] = btn9;

        game = new GameBoard();
        setPlayer();
    }

    private void selectButton(int col, int row) {
        Button btn = buttonsLocation[col][row];
        String buttonCss = PlayerHandler.getThemeCSS(1);
        clickedButtons.add(btn.getId());

        if (PlayerHandler.getSymbol(1) != null)
            btn.setText(PlayerHandler.getSymbol(1).toString());
        else
            btn.setText("X");

        if (!buttonCss.equals("none"))
            btn.setStyle(buttonCss);
    }

    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        try
        {
            Integer row = GridPane.getRowIndex((Node) event.getSource());
            Integer col = GridPane.getColumnIndex((Node) event.getSource());
            int r = (row == null) ? 0 : row;
            int c = (col == null) ? 0 : col;
            int player = game.getNextPlayer();

            Button btn = (Button) event.getSource();
            String xOrO = player == 1 ? "X" : "O";
            if (PlayerHandler.getSymbol(game.getNextPlayer()) != null) {
                xOrO = player == 1 ? PlayerHandler.getSymbol(1).toString() : PlayerHandler.getSymbol(2).toString();
            }
            // Effekt til at vise "0" i ens felt
            clickedButtons.add(btn.getId());
            btn.setStyle(CLICKED_BUTTON_STYLE);

            if (game.play(c, r)) {
                ai.setMove(r, c, 2);

                int[] botResult = ai.getThinking();
                r = botResult[0];
                c = botResult[1];
                if (game.play(c, r)) {
                    ai.setMove(r, c, 1);
                    selectButton(c, r);
                }

                btn.setText(xOrO);
                String buttonCss = PlayerHandler.getThemeCSS(player);
                if (!buttonCss.equals("none"))
                    btn.setStyle(buttonCss);
                setPlayer();

                if (game.isGameOver())
                    displayWinner(game.getWinner());

            }

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void handleNewGame(ActionEvent event)
    {
        game.newGame();
        setPlayer();
        clearBoard();
        ai.reset();
    }

    private void setPlayer()
    {
       lblPlayer.setText("You are playing vs. AI!");
    }

    private void displayWinner(int winner)
    {
        String message = "";
        switch (winner)
        {
            case -1:
                message = "It's a draw :-(";
                break;
            default:
                PlayerHandler.setWin(winner);
                updateScoreBoard();

                String name = winner == 1 ? "AI" : "You";
                message = name + " win!!!";
                Image winEffect = PlayerHandler.getWinEffects(game.getNextPlayer());
                if(winEffect != null)
                    winImg.setImage(winEffect);
                break;
        }
        lblPlayer.setText(message);
    }

    private void updateScoreBoard() {
        scoreBoard.setText(PlayerHandler.getTextScore());
    }
    
    private void clearBoard()
    {
        clickedButtons.clear();
        for(Node n : gridPane.getChildren())
        {
            Button btn = (Button) n;
            btn.setText("");
            btn.setStyle(NORMAL_BORDER_COLOUR);
        }
    }
    @FXML
    public void onMainMenuBtnClick(ActionEvent event) throws IOException {
        PlayerHandler.resetWins();

        Parent MainMenu = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Tic Tac Toe");

        Scene scene = new Scene(MainMenu);
        stage.setScene(scene);
        stage.show();
    }

    public void buttonHover(MouseEvent event) {
        if(game.isGameOver())
            return;

        Button btn = (Button) event.getSource();
        int player = game.getNextPlayer();

        for (String n : clickedButtons) {
            if (btn.getId().equals(n)) {
                return;
            }
        }

        btn.setStyle(HOVERED_BUTTON_STYLE);
        if (PlayerHandler.getSymbol(player) != null)
            btn.setText(PlayerHandler.getSymbol(player).toString());
        else
            btn.setText(player == 1 ? "X" : "O");
    }

    public void buttonStopHover(MouseEvent event) {
        if(game.isGameOver())
            return;

        Button btn = (Button) event.getSource();

        for (String n : clickedButtons) {
            if (btn.getId().equals(n)) {
                return;
            }
        }

        btn.setStyle(CLICKED_BUTTON_STYLE);
        btn.setText("");
    }
}
