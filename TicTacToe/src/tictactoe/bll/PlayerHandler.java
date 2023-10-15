package tictactoe.bll;

import javafx.scene.image.Image;

import java.util.Objects;

public class PlayerHandler {
    private static int playerId, p1Wins, p2Wins;
    private static String p1Name, p1Theme, p1WinEffect;
    private static String p2Name, p2Symbol, p2Theme, p2WinEffect;


    private static Object p1Symbol;

    public static String getName(int id)
    {
        return id == 1 ? p1Name : p2Name;
    }

    public static Object getSymbol(int id)
    {
        return id == 1 ? p1Symbol : p2Symbol;
    }

    public static String getTheme(int id)
    {
        return id == 1 ? p1Theme : p2Theme;
    }

    public static String getWinEffect(int id)
    {
        return id == 1 ? p1WinEffect : p2WinEffect;
    }

    public static void p1SetName(String name) {
        p1Name = name;
    }

    public static void p2SetName(String name) {
        p2Name = name;
    }

    public static void p1SetSymbol(Object symbol) {
        p1Symbol = symbol;
    }

    public static void p2SetSymbol(String symbol) {
        p2Symbol = symbol;
    }

    public static void p1Settheme(String theme) {
        p1Theme = theme;
    }

    public static void p2Settheme(String theme) {
        p2Theme = theme;
    }

    public static void p1SetwinEffect(String winEffect) {
        p1WinEffect = winEffect;
    }

    public static void p2SetwinEffect(String winEffect) {
        p2WinEffect = winEffect;
    }

    public static String getThemeCSS(int player) {
        if (getTheme(player) != null) {
            if (Objects.equals(getTheme(player), "Off"))
                return "-fx-border-color: #26292e;";

            if (Objects.equals(getTheme(player), "White"))
                return "-fx-border-color: #51555c";

            if (Objects.equals(getTheme(player), "Red"))
                return "-fx-border-color: #422020";

            if (Objects.equals(getTheme(player), "Blue"))
                return "-fx-border-color: #202b42";

            if (Objects.equals(getTheme(player), "Yellow"))
                return "-fx-border-color: #3d4220";

        }
        return "none";
    }
    public static Image getWinEffects(int player) {
        if (getWinEffect(player)!= null) {
            if (Objects.equals(getWinEffect(player), "Off"))
                return null;

            if (Objects.equals(getWinEffect(player), "Crown"))
                return new Image("/images/Crown.png");

            if (Objects.equals(getWinEffect(player), "Cake"))
                return new Image("/images/Cake.png");

            if (Objects.equals(getWinEffect(player), "Trophy"))
                return new Image ("/images/Trophy.png");

        }
        return null;
    }

    public static void resetWins() {
        p1Wins = 0;
        p2Wins = 0;
    }

    public static void setWin(int player) {
        if (player == 1)
            p1Wins = p1Wins + 1;
        else
            p2Wins = p2Wins + 1;
    }

    public static String getTextScore() {
        return p1Wins + " : " + p2Wins;
    }

}
