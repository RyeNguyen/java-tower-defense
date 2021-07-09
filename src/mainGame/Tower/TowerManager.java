package mainGame.Tower;

import mainGame.Enemy.Enemy;
import mainGame.GameEntity;

import java.util.concurrent.CopyOnWriteArrayList;

public interface TowerManager extends GameEntity {
    public Enemy lockOnTarget();
    public boolean isInRange(Enemy e);
    public float findDistance(Enemy e);
    public float calculateAngle();
    public abstract void shoot(Enemy target);
    public void updateEnemy(CopyOnWriteArrayList<Enemy> l);
    public float getX();

    public float getY();

    public int getWidth();

    public int getHeight();

    public void setX(float x);

    public void setY(float y);

    public void setWidth(int width);

    public void setHeight(int height);

    public Enemy getTarget();

    public int getCost();

    public void update();

    public void draw();
}
