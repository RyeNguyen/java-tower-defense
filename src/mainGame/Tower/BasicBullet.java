package mainGame.Tower;

import mainGame.Enemy.Enemy;

public class BasicBullet extends Bullet {
    public BasicBullet(BulletType type, Enemy target, float x, float y, int width, int height) {
        super(type, target, x, y, width, height);
    }

    @Override
    public void power() {
        super.power();
    }
}
