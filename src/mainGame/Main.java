package mainGame;

import Audio.AudioPlayer;
import helpers.TimeManager;
import mainGame.GameStateManager.GameStage;
import org.lwjgl.opengl.Display;
import static helpers.Drawer.*;

public class Main {
    private AudioPlayer music;
    public Main() {
        beginSession();
        music = new AudioPlayer("/res/Music/backgound.mp3");
        music.play();

        while (!Display.isCloseRequested()){
            TimeManager.update();
            GameStage.update();
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
        music.stop();
    }

    public static void main(String[] args) {
        new Main();
    }
}
