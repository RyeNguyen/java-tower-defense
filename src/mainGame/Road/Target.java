package mainGame.Road;

import mainGame.Enemy.Enemy;
import mainGame.GameStateManager.GameStage;
import mainGame.GameTile.TileGrid;

import static mainGame.GameStateManager.GameStage.GameState;

public class Target {
    private float     waveTime, enemiesTime; //thời gian từ lần spawn trước và thời gian giữa các enemy
    private int       enemiesInWave; //số lượng enemy một wave
    private Enemy[]   enemyType; //kiểu enemy
    private Spawner   thisWave; //wave hiện tại
    private TileGrid  grid; //bản đồ
    public static int waveCount; //số lượng wave

    public Target(Enemy[] enemyType, float timeBetweenEnemies, int enemiesPerWave, TileGrid grid) {
        this.enemyType =     enemyType;
        this.enemiesTime =   timeBetweenEnemies;
        this.enemiesInWave = enemiesPerWave;
        this.waveTime =      0;
        this.waveCount =     0;
        this.thisWave =      null;
        this.grid =          grid;
        newWave(); //wave đầu
    }

    public void update() {
        //vẽ wave chừng nào vẫn còn enemy
        if (!thisWave.isWaveCompleted()) {
            thisWave.update();
        }
        //nếu không thì tạo wave mới, bắt đầu wave tiếp theo
        else newWave();
    }

    /*public void newWave() {
        thisWave = new Spawner(enemyType, enemiesTime, enemiesInWave); //tạo một wave
        waveCount++; //số lượng wave tăng lên 1, nễu bằng 21 thì thoát game, người chơi thắng
        if (waveCount == 21) {
            GameStage.setGameCondition(GameState.GAMEWIN);
        }
        enemiesInWave += 3; //mỗi lượt thêm 3 enemy
    }*/

    public void newWave() {
        thisWave = new Spawner(enemyType, enemiesTime, enemiesInWave);
        waveCount++;
        if (waveCount == 2) {
            GameStage.setGameCondition(GameState.GAMEWIN);
        }
    }

    public Spawner getCurrentWave() {return thisWave;}

    public int getWaveNumber() { return waveCount; }
}
