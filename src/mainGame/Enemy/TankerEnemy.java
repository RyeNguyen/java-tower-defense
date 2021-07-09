package mainGame.Enemy;

import mainGame.GameTile.TileGrid;

public class TankerEnemy extends Enemy {
    public TankerEnemy(int x, int y, TileGrid grid) {
        super(x, y, grid);
        this.setTexture("enemy2");
        super.setSpeed(50);
        super.setHealth(200);

    }

}
