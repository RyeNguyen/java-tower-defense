package mainGame.GameTile;

import org.newdawn.slick.opengl.Texture;

import static helpers.Drawer.*;

public class Tile {
    private float    x, y;
    private int      width, height;
    private Texture  texture;
    private TileType type;
    private boolean  captured;

    //khai báo 1 ô
    public Tile (float x, float y, int height, int width, TileType type){
        this.x =                      x;
        this.y =                      y;
        this.height =                 height;
        this.width =                  width;
        this.type =                   type;
        this.texture =                quickLoad (type.textureName);
        if (type.buidable) captured = false;
        else captured =               true;
    }

    //vẽ ô
    public void draw() {
        drawTexture(texture, x, y, width, height);
    }

    public float getX() { return x; }

    public int getPlaceX() {
        return (int) (x / TILE);
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public int getPlaceY() {
        return (int) (y / TILE);
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public boolean getCaptured() {
        return captured;
    }

    public void setCaptured(boolean captured) {
        this.captured = captured;
    }
}
