package mainGame.GameStateManager;

import mainGame.GameField;
import mainGame.GameTile.TileGrid;

import static helpers.Level.load;

public class GameStage {
    //liệt kê các chế độ của game
    public static enum GameState {
        MAINMENU, GAME, EDITOR, GAMEOVER, GAMEWIN
    }

    public static GameState gameCondition = GameState.MAINMENU;
    public static MainMenu mainMenu;
    public static GameField game;
    public static Editor editor;
    public static GameOver gameOver;
    public static GameWin gameWin;

    public static TileGrid map = load("map1");

    public static void update() {
        switch (gameCondition) {
            case MAINMENU:
                if (mainMenu == null)
                    mainMenu = new MainMenu();
                mainMenu.update();
                break;
            case GAME:
                if (game == null)
                    game = new GameField(map);
                game.update();
                break;
            case EDITOR:
                if (editor == null)
                    editor = new Editor();
                editor.update();
                break;
            case GAMEOVER:
                if (gameOver == null)
                    gameOver = new GameOver();
                gameOver.update();
                break;
            case GAMEWIN:
                if (gameWin == null)
                    gameWin = new GameWin();
                gameWin.update();
                break;
        }
    }

    public static void setGameCondition(GameState newState)  {
        gameCondition = newState;
    }
}
