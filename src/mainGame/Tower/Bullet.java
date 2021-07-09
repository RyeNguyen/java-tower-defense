package mainGame.Tower;

import mainGame.Enemy.Enemy;
import org.newdawn.slick.opengl.Texture;

import static helpers.Drawer.*;
import static helpers.TimeManager.Delta;

public abstract class Bullet implements BulletManager {
    private Texture texture; //texture của đạn
    private float   x, y, speed, xDirect, yDirect, angle; //toạ độ, tốc độ, hướng di chuyển và góc của đạn
    private int     damage, width, height; //dam, chiều dài chiều rộng của đạn
    private Enemy   target; //mục tiêu
    private boolean alive; //kiểm tra mục tiêu đã chết chưa.

    public Bullet(BulletType type, Enemy target, float x, float y, int width, int height) {
        this.texture = type.texture;
        this.x =       x;
        this.y =       y;
        this.width =   width;
        this.height =  height;
        this.speed =   type.speed;
        this.damage =  type.damage;
        this.target =  target;
        this.alive =   true;
        this.xDirect = 0;
        this.yDirect = 0;
        this.angle =   calculateAngle();
        calculateDirection(); //tính toán hướng đi của đạn
    }

    public void calculateDirection() {
        float totalAllowedMovement = 1.0f;
        //khoảng cách từ đạn đến enemy
        //+TILE_SIZE / 2 để đạn bắt đầu ở vị trí góc phải dưới của tháp, trừ đi TILE_SIZE / 4 để dịch sang giữa
        float xToTarget = Math.abs(target.getX() - x - TILE / 4 + TILE / 2);
        float yToTarget = Math.abs(target.getY() - y - TILE / 4 + TILE / 2);
        xDirect = xToTarget / (xToTarget + yToTarget);
        yDirect = totalAllowedMovement - xDirect;
        if (target.getX() < x) xDirect *= -1;
        if (target.getY() < y) yDirect *= -1;
    }

    //tính góc để đạn xoay theo enemy
    public float calculateAngle() {
        double temp = Math.atan2(target.getY() - y, target.getX() - x);
        return (float) Math.toDegrees(temp) - 90;
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

    //gây dam cho enemy
    public void power() {
        if (target.isAlive()) {
            target.takingHit(damage);
            alive = false;
        }
    }

    public void setAlive(boolean status) {
        alive = status;
    }

    public void update() {
        if (alive) {
            x += xDirect * speed * Delta();
            y += yDirect * speed * Delta();
            if (isCollided(x, y, width, height, target.getX(), target.getY(), target.getWidth(), target.getHeight())) {
                power();
            }
            draw();
        }
    }

    //vẽ đạn
    public void draw() {
        drawRotation (texture, x, y, 32, 32, angle);
    }
}