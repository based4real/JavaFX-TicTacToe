package tictactoe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    public Button GameWorldPvP;
    public Button GameWorldPvE;
    public ImageView imageMainMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image("/images/MainMenu.png");
        imageMainMenu.setImage(image);
    }
    public void onPvPBtnClick(ActionEvent event) throws IOException {
        Parent PvP = FXMLLoader.load(getClass().getResource("/views/TicTacViewPvP.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Player Vs Player");

        Scene scene = new Scene(PvP);
        stage.setScene(scene);
        stage.show();
    }

    public void onPvEBtnClick(ActionEvent event) throws IOException {
        Parent PvE = FXMLLoader.load(getClass().getResource("/views/TicTacViewPvAI.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Player Vs AI");

        Scene scene = new Scene(PvE);
        stage.setScene(scene);
        stage.show();
    }

    public void onSettingsBtnClick(ActionEvent event) throws IOException {
        Parent settings = FXMLLoader.load(getClass().getResource("/views/SettingsMenu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Settings");

        Scene scene = new Scene(settings);
        stage.setScene(scene);
        stage.show();
    }

    public void onExitBtnClick(ActionEvent event) {
        System.exit(0);
    }

}
