package mainGame.Enemy;

import mainGame.GameEntity;
import mainGame.GameTile.Tile;
import mainGame.GameTile.TileGrid;
import mainGame.Road.TurningPoint;
import org.newdawn.slick.opengl.Texture;

public interface Threat extends GameEntity {
    public void takingHit(int amount);
    public void die();
    public void update();
    public void endOfMap();
    public boolean turningPointReached();
    public void populateTurningPointList();
    public TurningPoint findNextTurningPoint (Tile start, int[] direc);
    public int[] findNextDirection(Tile start);
    public void draw();
    public void reduceCheck(float amount);

    public float getCheck();

    public int getWidth();

    public void setWidth(int width);

    public int getHeight();

    public void setHeight(int height);

    public float getHealth();

    public void setHealth(int health);

    public float getSpeed();

    public void setSpeed(float speed);

    public float getX();

    public void setX(float x);

    public float getY();

    public void setY(float y);

    public Tile geteTile();

    public Texture getTexture();

    public void setTexture(Texture texture);

    public TileGrid getTileGrid();

    public boolean isAlive();

    public void setTexture(String name);
}
