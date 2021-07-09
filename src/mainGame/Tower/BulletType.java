package mainGame.Tower;

import org.newdawn.slick.opengl.Texture;

import static helpers.Drawer.*;

public enum BulletType {

    NormalBullet(quickLoad("towernormalbullet"), 50, 2000),
    SniperBullet(quickLoad("towersniperbullet"), 120, 2000),
    ParalizedBullet(quickLoad("towerzapbullet"), 0, 1000),
    MachineBullet(quickLoad("towermachinegunbullet"), 30, 2000);

    Texture texture;
    int damage;
    float speed;

    BulletType(Texture texture, int damage, float speed) {
        this.texture = texture;
        this.damage =  damage;
        this.speed =   speed;
    }
}
