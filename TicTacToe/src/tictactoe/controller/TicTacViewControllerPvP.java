/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.controller;

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
import tictactoe.bll.GameBoard;
import tictactoe.bll.IGameModel;
import tictactoe.bll.PlayerHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 *
 * @author Stegger
 */
public class TicTacViewControllerPvP implements Initializable
{
    public ImageView winImg;

    @FXML
    private Label scoreBoard;
    @FXML
    private Stage stage;
    @FXML
    private Label lblPlayer;
    @FXML
    private GridPane gridPane;

    private static final String CLICKED_BUTTON_STYLE = "-fx-text-fill: white;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-text-fill: grey;";
    private static final String NORMAL_BORDER_COLOUR = "-fx-border-color: #26292e";

    ArrayList<String> clickedButtons = new ArrayList<String>();


    private static final String TXT_PLAYER = "Player: ";
    private IGameModel game;

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
            if (game.play(c, r)) {
                Button btn = (Button) event.getSource();
                String xOrO = player == 1 ? "X" : "O";
                if (PlayerHandler.getSymbol(game.getNextPlayer()) != null) {
                    xOrO = player == 1 ? PlayerHandler.getSymbol(1).toString() : PlayerHandler.getSymbol(2).toString();
                }
                btn.setText(xOrO);
                String buttonCss = PlayerHandler.getThemeCSS(player);
                setPlayer();

                // Effekt til at vise "0" i ens felt
                clickedButtons.add(btn.getId());
                btn.setStyle(CLICKED_BUTTON_STYLE);
                if (!buttonCss.equals("none"))
                    btn.setStyle(buttonCss);

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
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        game = new GameBoard();
        setPlayer();
    }

    private void setPlayer()
    {
        if(PlayerHandler.getName(game.getNextPlayer()) == null)
            lblPlayer.setText("Turn: " + TXT_PLAYER + game.getNextPlayer());
        else {lblPlayer.setText("Turn: " + PlayerHandler.getName(game.getNextPlayer())); }
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

                message = "Player " + winner + " wins!!!";
                if(PlayerHandler.getName(game.getNextPlayer()) == null){
                    message = "Player " + winner + " wins!!!";
                }
                else {
                    message = PlayerHandler.getName(game.getNextPlayer()) + " wins!!!";
                    Image winEffect = PlayerHandler.getWinEffects(game.getNextPlayer());
                    if(winEffect != null)
                        winImg.setImage(winEffect);
                }
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
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Tic Tac Toe");

        Scene scene = new Scene(MainMenu);
        stage.setScene(scene);
        stage.show();
    }

    public void buttonHover(MouseEvent event) {
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