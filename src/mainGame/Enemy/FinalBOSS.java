package mainGame.Enemy;

import mainGame.GameTile.TileGrid;

public class FinalBOSS extends Enemy {
    public FinalBOSS(int x, int y, TileGrid grid) {
        super(x, y, grid);
        super.setTexture("enemyfinalboss");
        super.setSpeed(20);
        super.setHealth(5000);
    }
}
