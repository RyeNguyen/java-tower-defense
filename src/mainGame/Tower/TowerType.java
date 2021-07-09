package mainGame.Tower;

import org.newdawn.slick.opengl.Texture;

import static helpers.Drawer.*;

//kiểu liệt kê, liệt kê các loại tháp
public enum TowerType {

    NormalTower(new Texture[]{quickLoad("towernormalbase"), quickLoad("towernormalgun")}, BulletType.NormalBullet, 0, 200, 3, 50),
    SniperTower(new Texture[]{quickLoad("towersniperbase"), quickLoad("towersnipergun")}, BulletType.SniperBullet, 0, 350, 5, 100),
    ParalizedTower(new Texture[]{quickLoad("towerzapbase"), quickLoad("towerzapgun")}, BulletType.ParalizedBullet, 0, 300, 0, 300),
    MachineGun(new Texture[]{quickLoad("towermachinegunbase"), quickLoad("towermachinegungun")}, BulletType.MachineBullet, 0, 150, 1, 200);

    Texture[]  textures;
    BulletType bulletType;
    int        damage, range, cost;
    float      firingSpeed;

    TowerType(Texture[] textures, BulletType bulletType, int damage, int range, float firingSpeed, int cost) {
        this.textures =    textures;
        this.bulletType =  bulletType;
        this.damage =      damage;
        this.range =       range;
        this.cost =        cost;
        this.firingSpeed = firingSpeed;
    }
}
