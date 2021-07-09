package mainGame.Tower;

import mainGame.Enemy.Enemy;
import mainGame.GameEntity;

public interface BulletManager extends GameEntity {
    public void calculateDirection();
    public float calculateAngle();

    public float getX();

    public float getY();

    public int getWidth();

    public int getHeight();

    public void setX(float x);

    public void setY(float y);

    public void setWidth(int width);

    public void setHeight(int height);

    public Enemy getTarget();

    public void power();

    public void setAlive(boolean status);

    public void update();

    public void draw();
}
