package mainGame.Tower;

import mainGame.Enemy.Enemy;
import mainGame.GameTile.Tile;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Drawer.drawRotation;
import static helpers.Drawer.drawTexture;
import static helpers.TimeManager.Delta;

public abstract class Tower implements TowerManager {
    private float                       x, y, firingTime, firingSpeed, angle; //toạ độ, tgian từ lần bắn trước, tgian bắn và góc quay
    private int                         width, height, range, cost; //chiều dàu, rộng, tầm ngắm và giá thành
    public Enemy                        target; //mục tiêu cần bắn
    private Texture[]                   textures; //texture tháp
    private CopyOnWriteArrayList<Enemy> enemies; //list enemy
    public ArrayList<Bullet>            bullets; //list đạn
    private boolean                     targeted; //biến ktra đã ngắm trúng hay chưa
    public TowerType                    type; //kiểu tháp

    public Tower(TowerType type, Tile towerTile, CopyOnWriteArrayList<Enemy> enemies) {
        this.type =        type;
        this.textures =    type.textures;
        this.range =       type.range;
        this.cost =        type.cost;
        this.x =           towerTile.getX();
        this.y =           towerTile.getY();
        this.width =       towerTile.getWidth();
        this.height =      towerTile.getHeight();
        this.enemies =     enemies;
        this.targeted =    false;
        this.firingTime =  0f;
        this.bullets =     new ArrayList<Bullet>();
        this.firingSpeed = type.firingSpeed;
        this.angle =       0f;
    }

    public Enemy lockOnTarget() {
        Enemy closest = null; //enemy gần nhất
        float closestDistance = 1000;
        for (Enemy e: enemies) {
            //nếu enemy ở trong tầm ngắm và khoảng cách enemy trong 1000 và máu ẩn của enemy vẫn còn
            if (isInRange(e) && findDistance(e) < closestDistance && e.getCheck() > 0) {
                closestDistance = findDistance(e);
                closest = e;
            }
        }
        if (closest != null)
            targeted = true;
        return closest;
    }

    //kiểm tra enemy có trong tầm ngắm không
    public boolean isInRange(Enemy e) {
        float distanceX = Math.abs(e.getX() - x);
        float distanceY = Math.abs(e.getY() - y);
        if (distanceX < range && distanceY < range) return true;
        return false;
    }

    //tính khaorng cách giữa tháp và enemy
    public float findDistance(Enemy e) {
        float distanceX = Math.abs(e.getX() - x);
        float distanceY = Math.abs(e.getY() - y);
        return distanceX + distanceY;
    }

    //xoay súng theo enemy sử dụng toạ độ cực
    public float calculateAngle() {
        double temp = Math.atan2(target.getY() - y, target.getX() - x);
        return (float) Math.toDegrees(temp) - 90;
    }

    public abstract void shoot(Enemy target);

    public void updateEnemy(CopyOnWriteArrayList<Enemy> l) {
        enemies = l;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    public Enemy getTarget() { return target; }

    public int getCost() { return cost; }

    @Override
    public void update() {
        //nếu chưa ngắm trúng enemy hoặc máu ẩn của enemy đã hết thì tìm mục tiêu mới
        if (!targeted || target.getCheck() < 0 || (Math.abs(target.getX() - x) > range && Math.abs(target.getY() - y) > range)) {
            target = lockOnTarget();
        } else if (Math.abs(target.getX() - x) <= range && Math.abs(target.getY() - y) <= range) {
            angle = calculateAngle();
            if (firingTime >= firingSpeed) {
                shoot(target);
                firingTime = 0;
            }
        }

        //nếu không có mục tiêu hoặc mục tiêu đã chết thì tìm mục tiêu khác
        if (target == null || target.isAlive() == false) targeted = false;

        firingTime += Delta();
        for (Bullet b : bullets) {
                b.update();
        }
        draw();
    }

    @Override
    public void draw() {
        //vẽ bệ
        drawTexture(textures[0], x, y, width, height);
        if (textures.length > 1) {
            //vẽ súng xoay được
            for (int i = 1; i < textures.length; i++)
                drawRotation(textures[i], x, y, width, height, angle);
        }
    }
}
