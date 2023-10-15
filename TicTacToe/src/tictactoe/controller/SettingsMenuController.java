package tictactoe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tictactoe.bll.PlayerHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsMenuController implements Initializable {
    @FXML
    public Label lblSettingsSaved;
    @FXML
    private TextField player1Name, player2Name;
    @FXML
    private ComboBox player1Symbol, player2Symbol, player1Theme, player2Theme, player1WinEffect, player2WinEffect;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        if (PlayerHandler.getName(1) == null || PlayerHandler.getName(2) == null)
            saveSettings();
        else
            restoreSettings();
    }

    @FXML
    private void onBackBtnClick(ActionEvent event) throws IOException {
        Parent MainMenu = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Tic Tac Toe");

        Scene scene = new Scene(MainMenu);
        stage.setScene(scene);
        stage.show();
    }

    public void onSaveSettingsBtnClick(ActionEvent event) {
        saveSettings();
        lblSettingsSaved.setText("Your settings have been saved");
    }

    private void restoreSettings() {
        player1Name.setText(PlayerHandler.getName(1));
        player1Theme.setValue(PlayerHandler.getTheme(1));
        player1Symbol.setValue(PlayerHandler.getSymbol(1));
        player1WinEffect.setValue(PlayerHandler.getWinEffect(1));
        player2Name.setText(PlayerHandler.getName(2));
        player2Theme.setValue(PlayerHandler.getTheme(2));
        player2Symbol.setValue(PlayerHandler.getSymbol(2));
        player2WinEffect.setValue(PlayerHandler.getWinEffect(2));
    }

    private void saveSettings() {
        PlayerHandler.p1SetName(player1Name.getText());
        PlayerHandler.p1Settheme(player1Theme.getValue().toString());
        PlayerHandler.p1SetSymbol(player1Symbol.getValue().toString());
        PlayerHandler.p1SetwinEffect(player1WinEffect.getValue().toString());
        PlayerHandler.p2SetName(player2Name.getText());
        PlayerHandler.p2Settheme(player2Theme.getValue().toString());
        PlayerHandler.p2SetSymbol(player2Symbol.getValue().toString());
        PlayerHandler.p2SetwinEffect(player2WinEffect.getValue().toString());
    }
}
