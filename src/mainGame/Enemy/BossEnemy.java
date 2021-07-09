package mainGame.Enemy;

import mainGame.GameTile.TileGrid;

public class BossEnemy extends Enemy {
    public BossEnemy(int x, int y, TileGrid grid) {
        super(x, y, grid);
        super.setTexture("enemyboss");
        super.setSpeed(30);
        super.setHealth(1000);
    }
}
