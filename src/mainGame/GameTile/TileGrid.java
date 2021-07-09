package mainGame.GameTile;

import static helpers.Drawer.TILE;

public class TileGrid {
    public      Tile[][] map;
    private int tileWide, tileHigh;

    //vẽ map dưới dạng các ô vuông cỏ nhỏ 64x64, chiều rộng 20, chiều cao 15
    public TileGrid() {
        this.tileWide = 20;
        this.tileHigh = 15;
        map = new Tile[tileWide][tileHigh];
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                map[i][j] = new Tile(i * TILE, j * TILE, TILE, TILE, TileType.FreshGrass);
            }
        }
    }

    //vẽ map, nếu map[i][j] = 0 vẽ cỏ, = 1 vẽ đường, = 2 vẽ nước
    public TileGrid(int[][] newMap){
        this.tileWide = newMap[0].length;
        this.tileHigh = newMap.length;
        map = new Tile[tileWide][tileHigh];
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                switch (newMap[j][i]) {
                    case 0:
                        map[i][j] = new Tile(i * TILE, j * TILE, TILE, TILE, TileType.FreshGrass);
                        break;
                    case 1:
                        map[i][j] = new Tile(i * TILE, j * TILE, TILE, TILE, TileType.Route);
                        break;
                    case 2:
                        map[i][j] = new Tile(i * TILE, j * TILE, TILE, TILE, TileType.Water);
                        break;
                }
            }
        }
    }

    //thay đổi texture một ô
    public void setTile (int coordX, int coordY, TileType type){
        map[coordX][coordY] = new Tile(coordX * TILE, coordY * TILE, TILE, TILE, type);
    }

    public Tile getTile (int placeX, int placeY) {
        //nếu toạ độ của ô vẫn còn nằm trong frame thì trả về toạ dộ của ô, không thì trả về null
        if (placeX < tileWide && placeY < tileHigh && placeX > -1 && placeY > -1)
            return map[placeX][placeY];
        else
            return new Tile(0, 0, 0, 0, TileType.NULL);
    }

    public int getTileWide() {
        return tileWide;
    }

    public void setTileWide(int tileWide) {
        this.tileWide = tileWide;
    }

    public int getTileHigh() {
        return tileHigh;
    }

    public void setTileHigh(int tileHigh) {
        this.tileHigh = tileHigh;
    }

    //vẽ map
    public void draw() {
        for (int i = 0; i < map.length; i++){
            for (int j = 0; j < map[i].length; j++){
                map[i][j].draw();
            }
        }
    }
}

