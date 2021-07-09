package mainGame.Tower;

import mainGame.Enemy.Enemy;
import mainGame.GameTile.Tile;

import java.util.concurrent.CopyOnWriteArrayList;

public class MachineGun extends Tower {
    public MachineGun(TowerType type, Tile towerTile, CopyOnWriteArrayList<Enemy> enemies) {
        super (type, towerTile, enemies);
    }

    @Override
    public void shoot(Enemy target) {
        super.bullets.add(new BasicBullet(super.type.bulletType, super.target, super.getX(), super.getY(), 32, 32));
        super.target.reduceCheck(super.type.bulletType.damage);
    }
}
