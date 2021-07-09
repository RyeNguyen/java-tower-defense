package helpers;

import mainGame.GameTile.Tile;
import mainGame.GameTile.TileGrid;
import mainGame.GameTile.TileType;

import java.io.*;

public class Level {
    public static void save(String mapName, TileGrid grid) {
        String mapData = "";
        for (int i = 0; i < grid.getTileWide(); i++) {
            for (int j = 0; j < grid.getTileHigh(); j++) {
                mapData += getTileID(grid.getTile(i, j));
            }
        }
        try {
            File file = new File(mapName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(mapData);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TileGrid load(String mapName) {
        TileGrid grid = new TileGrid();
        try {
            BufferedReader br = new BufferedReader(new FileReader(mapName));
            String data = br.readLine();
            for (int i = 0; i < grid.getTileWide(); i++) {
                for (int j = 0; j < grid.getTileHigh(); j++) {
                    grid.setTile(i, j, getTileType(data.substring(i * grid.getTileHigh() + j, i * grid.getTileHigh() + j + 1)));
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grid;
    }

    public static TileType getTileType(String ID) {
        TileType type = TileType.NULL;
        switch (ID) {
            case "0":
                type = TileType.FreshGrass;
                break;
            case "1":
                type = TileType.FadedGrass;
                break;
            case "2":
                type = TileType.Sand;
                break;
            case "3":
                type = TileType.Route;
                break;
            case "4":
                type = TileType.Water;
                break;
            case "5":
                type = TileType.NULL;
                break;
        }
        return type;
    }

    public static String getTileID(Tile t) {
        String ID = "ERROR!";
        switch (t.getType()) {
            case FreshGrass:
                ID = "0";
                break;
            case FadedGrass:
                ID = "1";
                break;
            case Sand:
                ID = "2";
                break;
            case Route:
                ID = "3";
                break;
            case Water:
                ID = "4";
                break;
            case NULL:
                ID = "5";
                break;
        }
        return ID;
    }
}
