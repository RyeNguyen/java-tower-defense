package mainGame.GameStateManager;

import UI.UserInterface;
import mainGame.GameField;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;
import static mainGame.GameStateManager.GameStage.*;
import static helpers.Drawer.*;

public class GameOver {
    private Texture background;
    private UserInterface userInterface;

    public GameOver() {
        background = quickLoad("gameover");
        userInterface = new UserInterface();
    }

    public void draw() {
        userInterface.drawText(1, 1, "Press R to restart your game, ESC to return to main menu");
    }

    public void update() {
        drawTexture(background, 0, 0, WIDTH, HEIGHT);
        draw();
        while (Keyboard.next()) {
            if (Keyboard.getEventKey() == Keyboard.KEY_R) {
                GameStage.setGameCondition(GameStage.GameState.GAME);
                game = new GameField(map);
                game.update();
            }
            if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                GameStage.setGameCondition(GameStage.GameState.MAINMENU);
                mainMenu = new MainMenu();
                mainMenu.update();
                game = new GameField(map);
                game.update();
            }
        }
    }
}
