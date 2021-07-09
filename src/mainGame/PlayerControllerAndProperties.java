package mainGame;

import helpers.TimeManager;
import mainGame.GameStateManager.GameStage;
import mainGame.GameTile.Tile;
import mainGame.GameTile.TileGrid;
import mainGame.GameTile.TileType;
import mainGame.Road.Target;
import mainGame.Tower.Tower;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

import static helpers.Drawer.*;

public class PlayerControllerAndProperties {
    private TileGrid         grid; //map
    private TileType[]       types; //kiểu ô
    private Target           moreWaves; //quản lý wave
    private ArrayList<Tower> towerList; //list tháp
    private boolean          leftClicked, rightClicked, holdClicked; //biến mô tả đang nhấn chuột trái, phải, hoặc giữ tháp khi chọn trong menu
    private Tower            temp; //tháp giả tạm thời
    public static int        currency, lives, check = 0; //tiền và mạng

    public PlayerControllerAndProperties(TileGrid grid, Target moreWaves){
        this.grid =         grid;
        this.types =        new TileType[5];
        this.types[0] =     TileType.FreshGrass;
        this.types[1] =     TileType.FadedGrass;
        this.types[2] =     TileType.Sand;
        this.types[3] =     TileType.Route;
        this.types[4] =     TileType.Water;
        this.moreWaves =    moreWaves;
        this.towerList =    new ArrayList<Tower>();
        this.leftClicked =  false;
        this.rightClicked = false;
        this.holdClicked =  false;
        this.temp =         null;
        currency =          0;
        lives =             0;
    }

    //tiền và mạng mặc định
    public void setting() {
        currency = 50000;
        lives = 1;
    }

    //trừ mạng, nếu mạng = 0 thì gameover, người chơi thua
    public static void setTheLives(int amount) {
        lives += amount;
        if (lives == 0) {
            GameStage.setGameCondition(GameStage.GameState.GAMEOVER);
        }
    }

    //kiểm tra nếu còn tiền thì trả về true
    public static boolean setTheMoney(int amount) {
        if (currency + amount >= 0) {
            currency += amount;
            return true;
        }
        return false;
    }

    public void update() {
        //nếu chưa đặt tháp thì vẽ tháp tạm thời theo con trỏ chuột
        if (holdClicked) {
            temp.setX(getMouse().getX());
            temp.setY(getMouse().getY());
            temp.draw();
        }
        //vẽ các tháp trong list
        for (Tower t: towerList) {
            t.update();
            t.draw();
            t.updateEnemy(moreWaves.getCurrentWave().getEnemyList());
        }

        //nhấn chuột trái -> đặt tháp, !leftClicked để tránh bị nhấn quá nhiều vì class TimeManager
        if (Mouse.isButtonDown(0) && !leftClicked) {
            placeTheTower();
        }

        if (Mouse.isButtonDown(1) && !rightClicked) {

        }

        leftClicked = Mouse.isButtonDown(0);
        rightClicked = Mouse.isButtonDown(1);

        while (Keyboard.next()) {
            //pause game
            if (Keyboard.getEventKey() == Keyboard.KEY_P && Keyboard.getEventKeyState()) {
                check++;
                if (check % 2 != 0) TimeManager.changeMultiplier(-1f);
                else TimeManager.changeMultiplier(1f);
            }
        }
    }

    private void placeTheTower() {
        Tile tile = getMouse(); //lấy toạ độ con trỏ
        //nếu chưa đặt tháp thì kiểm tra ô có đặt đc k và có đủ tiền k
        if (holdClicked)
            if (!tile.getCaptured() && setTheMoney(-temp.getCost())) {
                towerList.add(temp);
                tile.setCaptured(true);
                holdClicked = false;
                temp = null;
            }
    }

    //chọn tháp
    public void picker(Tower t) {
        temp = t;
        holdClicked = true;
    }

    //lấy toạ độ của chuột
    private Tile getMouse() {
        return grid.getTile(Mouse.getX() / TILE, (HEIGHT - Mouse.getY() - 1) / TILE);
    }
}
