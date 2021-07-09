package mainGame.Enemy;

import mainGame.GameTile.TileGrid;

public class SmallerEnemy extends Enemy {
    public SmallerEnemy(int x, int y, TileGrid grid) {
        super(x, y, grid);
        this.setTexture("enemy3");
        this.setSpeed(200);
        this.setHealth(60);
    }
}
