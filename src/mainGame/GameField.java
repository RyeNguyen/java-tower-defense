package mainGame;

import UI.UserInterface;
import UI.UserInterface.Menu;
import mainGame.Enemy.*;
import mainGame.GameStateManager.GameStage;
import mainGame.GameTile.TileGrid;
import mainGame.GameTile.TileType;
import mainGame.Road.Target;
import mainGame.Tower.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import static helpers.Drawer.drawTexture;
import static helpers.Drawer.quickLoad;

//game class sẽ lo mọi thứ động trên màn hình như các wave của enemy, tương tác giữa người dùng để đặt tháp các kiểu
public class GameField {
    private TileGrid                      grid; //bản đồ
    private PlayerControllerAndProperties playerControllerAndProperties; //biến dùng để tương tác với người chơi
    private Target                        moreWaves; //quản lý wave
    private UserInterface                 gameInterface; //quản lý giao diện trong game
    private Menu                          pickTowerMenu; //menu chọn tháp
    private Texture                       metal; //hình nền chọn tháp
    private Enemy[]                       enemyType; //kiểu enemy
    private int                           x, y; //toạ độ xuất phát của enemy
    private Texture                       greenTree, redTree;

    public GameField(TileGrid grid) {
        this.grid = grid;
        this.x = 0;
        this.y = 0;
        //enemy sẽ tự động xuất phát tại ô đường
        for (int i = 0; i < grid.getTileWide(); i++) {
            if (grid.map[i][0].getType().equals(TileType.Route)) {
                y = 0;
                x = i;
            }
        }
        for (int i = 0; i < grid.getTileWide(); i++) {
            if (grid.map[i][13].getType().equals(TileType.Route)) {
                y = 13;
                x = i;
            }
        }
        for (int i = 0; i < grid.getTileHigh(); i++) {
            if (grid.map[0][i].getType().equals(TileType.Route)) {
                x = 0;
                y = i;
            }
        }
        enemyType = new Enemy[5];
        enemyType[0] = new NormalEnemy(x, y, grid);
        enemyType[1] = new TankerEnemy(x, y, grid);
        enemyType[2] = new SmallerEnemy(x, y, grid);
        enemyType[3] = new BossEnemy(x, y, grid);
        enemyType[4] = new FinalBOSS(x, y, grid);
        moreWaves = new Target(enemyType, 2, 20, grid);
        playerControllerAndProperties = new PlayerControllerAndProperties(grid, moreWaves);
        playerControllerAndProperties.setting();
        this.metal = quickLoad("metal");
        this.greenTree = quickLoad("tree");
        this.redTree = quickLoad("tree2");
        setTheUI();
    }

    private void setTheUI() {
        gameInterface = new UserInterface();
        gameInterface.createMenu("pickTower", 1280, 150, 320, 900,1, 0); //tạo menu ở bên phải sân chơi
        //thêm các nút chọn tháp
        pickTowerMenu = gameInterface.getMenu("pickTower");
        pickTowerMenu.quickAccess("BasicTower", "towernormal");
        pickTowerMenu.quickAccess("SniperTower", "towersniper");
        pickTowerMenu.quickAccess("MachineGun", "towermachinegun");
        pickTowerMenu.quickAccess("ParalizedTower","towerparalized");
    }



    private void updateUI() {
        gameInterface.draw();
        gameInterface.drawText(1420, 220, "50 $");
        gameInterface.drawText(1420, 360, "100 $");
        gameInterface.drawText(1420, 480, "200 $");
        gameInterface.drawText(1420, 600, "300 $");
        gameInterface.drawText(1380, 700, "Lives: " + PlayerControllerAndProperties.lives);
        gameInterface.drawText(1380, 750, "Money: " + PlayerControllerAndProperties.currency + " $");
        gameInterface.drawText(1380, 800, "Wave: " + moreWaves.getWaveNumber() + " / 20");
        gameInterface.drawText(0, 0, "Press ESC to return to the main menu, P to pause the game");
        drawTexture(greenTree, 320, 440, 150, 150);
        drawTexture(greenTree, 340, 420, 200, 200);
        drawTexture(greenTree, 500, 200, 200,   200);
        drawTexture(redTree, 1000, 500, 200, 200);
        drawTexture(redTree, 1090, 550, 200, 200);
        drawTexture(redTree, 960, 660, 200, 200);
        drawTexture(redTree, 1100, 200, 200, 200);
        drawTexture(greenTree, 5, 660, 200, 200);
        drawTexture(greenTree, 210, 670, 200, 200);
        drawTexture(greenTree, 670, 690, 200, 200);
        if (Mouse.next()) {
            boolean clicked = Mouse.isButtonDown(0);
            if (clicked) {
                if (pickTowerMenu.isButtonClicked("SniperTower"))
                    playerControllerAndProperties.picker(new SniperTower(TowerType.SniperTower, grid.getTile(0, 0), moreWaves.getCurrentWave().getEnemyList()));
                if (pickTowerMenu.isButtonClicked("BasicTower"))
                    playerControllerAndProperties.picker(new BasicTower(TowerType.NormalTower, grid.getTile(0, 0), moreWaves.getCurrentWave().getEnemyList()));
                if (pickTowerMenu.isButtonClicked("MachineGun"))
                    playerControllerAndProperties.picker(new MachineGun(TowerType.MachineGun, grid.getTile(0, 0), moreWaves.getCurrentWave().getEnemyList()));
                if (pickTowerMenu.isButtonClicked("ParalizedTower"))
                    playerControllerAndProperties.picker(new ParalizedTower(TowerType.ParalizedTower, grid.getTile(0, 0), moreWaves.getCurrentWave().getEnemyList()));
            }
        }
    }

    public void update() {
        drawTexture(metal, 1280, 0, 320, 900); //vẽ menu chọn tháp
        grid.draw(); //vẽ map
        moreWaves.update(); //vẽ wave
        playerControllerAndProperties.update();
        updateUI();
        //nếu nhấn esc thì quay lại main menu và restart lại game
        if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE && Keyboard.getEventKeyState()) {
            GameStage.setGameCondition(GameStage.GameState.MAINMENU);
            drawTexture(metal, 1280, 0, 320, 900); //vẽ menu chọn tháp
            grid.draw(); //vẽ map
            moreWaves.update(); //vẽ wave
            playerControllerAndProperties.update();
            updateUI();
        }
    }
}