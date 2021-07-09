package mainGame.GameTile;

//enum là kiểu liệt kê
public enum TileType {

    FreshGrass("grass", true),
    FadedGrass("grass4", false),
    Sand("grass5", true),
    Route("road12", false),
    Water("water2", false),
    NULL("water2", false);

    String  textureName;
    boolean buidable;

    //khai báo texture
    TileType (String textureName, boolean buidable){
        this.textureName = textureName;
        this.buidable = buidable;
    }
}

