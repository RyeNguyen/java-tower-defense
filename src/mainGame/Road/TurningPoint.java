package mainGame.Road;

import mainGame.GameTile.Tile;

public class TurningPoint {
    //là kiểu của các ô đóng vai trò là điểm rẽ
    public Tile tile; //kiểu ô
    private int directionX, directionY; //vị trí của ô

    public TurningPoint(Tile tile, int directionX, int directionY) {
        this.tile =       tile;
        this.directionX = directionX;
        this.directionY = directionY;
    }

    public Tile getTile() {
        return tile;
    }

    public int getDirectionX() {
        return directionX;
    }

    public int getDirectionY() {
        return directionY;
    }
}
