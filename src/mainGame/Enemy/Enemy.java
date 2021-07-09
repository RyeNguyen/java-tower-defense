package mainGame.Enemy;

import mainGame.GameTile.Tile;
import mainGame.GameTile.TileGrid;
import mainGame.PlayerControllerAndProperties;
import mainGame.Road.TurningPoint;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;

import static helpers.Drawer.*;
import static helpers.TimeManager.Delta;


public class Enemy implements Threat {
    private int                     width, height, currentTurningPoint; //chiều rộng, chiều cao và điểm rẽ hiện tại
    private float                   speed, x, y, health, totalHealth, check; // tốc độ, toạ độ, máu hiện tại, tổng máu và máu ẩn
    private Tile                    eTile; //ô vuông nơi enemy đang đứng
    private Texture                 texture, healthBackgound, healthForeground, healthBorder; //texture và thanh máu
    private boolean                 first, alive; //enemy đầu tiên và còn sống
    private TileGrid                grid; //bản đồ
    private ArrayList<TurningPoint> turningPoints; //list các góc để enemy rẽ hướng
    private int[]                   directions; //mảng chứa hướng di chuyển của enemy

    public Enemy(int x, int y, TileGrid grid) {
        this.texture =             quickLoad("enemy");
        this.healthBackgound =     quickLoad("health1");
        this.healthForeground =    quickLoad("health4");
        this.healthBorder =        quickLoad("health3");
        this.eTile =               grid.getTile(x, y);
        this.x =                   eTile.getX();
        this.y =                   eTile.getY();
        this.width =               TILE;
        this.height =              TILE;
        this.health =              100;
        this.totalHealth =         health;
        this.check =               health;
        this.speed =               100;
        this.grid =                grid;
        this.first =               true;
        this.alive =               true;
        this.turningPoints =       new ArrayList<TurningPoint>();
        this.directions =          new int[2];
        this.directions[0] =       0; //hướng của enemy theo x
        this.directions[1] =       0; //hướng của enemy theo y
        this.directions =          findNextDirection(eTile); //tìm hướng đi tiếp theo của enemy
        this.currentTurningPoint = 0;
        populateTurningPointList(); //xác định các điểm rẽ trên bản đồ
    }

    public Enemy(Texture texture, Tile eTile, TileGrid grid, int width, int height, float health, float speed){
        this.texture =             texture;
        this.healthBackgound =     quickLoad("health1");
        this.healthForeground =    quickLoad("health4");
        this.healthBorder =        quickLoad("health3");
        this.eTile =               eTile;
        this.x =                   eTile.getX();
        this.y =                   eTile.getY();
        this.width =               width;
        this.height =              height;
        this.health =              health;
        this.totalHealth =         health;
        this.check =               health;
        this.speed =               speed;
        this.grid =                grid;
        this.first =               true;
        this.alive =               true;
        this.turningPoints =       new ArrayList<TurningPoint>();
        this.directions =          new int[2];
        this.directions[0] =       0; //hướng của enemy theo x
        this.directions[1] =       0; //hướng của enemy theo y
        this.directions =          findNextDirection(eTile); //tìm hướng đi tiếp theo của enemy
        this.currentTurningPoint = 0;
        populateTurningPointList(); //xác định các điểm rẽ trên bản đồ
    }

    //nhận damage từ tháp
    public void takingHit(int amount) {
        health -= amount;
        //reward của normal enemy
        if (totalHealth == 100 && health <= 0) {
            die();
            PlayerControllerAndProperties.setTheMoney(10);
        }
        //reward của tanker enemy
        else if (totalHealth == 200 && health <= 0) {
            die();
            PlayerControllerAndProperties.setTheMoney(20);
        }
        //reward của boss
        else if (totalHealth == 1000 && health <= 0) {
            die();
            PlayerControllerAndProperties.setTheMoney(50);
        }
        //reward của smaller enemy
        else if (totalHealth == 60 && health <= 0) {
            die();
            PlayerControllerAndProperties.setTheMoney(5);
        }
        //reward của final boss
        else if (totalHealth == 5000 && health <= 0) {
            die();
            PlayerControllerAndProperties.setTheMoney(150);
        }
    }

    public void die() {
        alive = false;
    }

    public void update() {
        //kiểm tra xem có phải lần update đầu tiên không
        if (first) first = false;
        else {
            //nếu như đến điểm rẽ
            if (turningPointReached()) {
                if (currentTurningPoint + 1 == turningPoints.size()) {
                    endOfMap(); //end đường thì die
                }
                else {
                    currentTurningPoint++;
                }
            }
            else {
                //nếu chưa đến điểm rẽ, di chuyển enemy theo toạ độ của điểm rẽ tiếp theo
                x += Delta() * turningPoints.get(currentTurningPoint).getDirectionX() * speed;
                y += Delta() * turningPoints.get(currentTurningPoint).getDirectionY() * speed;
            }
        }
    }

    //khi enemy đến điểm rẽ cuối cùng
    public void endOfMap() {
        PlayerControllerAndProperties.setTheLives(-1);
        die();
    }

    public boolean turningPointReached() {
        boolean reached = false;
        Tile t = turningPoints.get(currentTurningPoint).getTile();
        //kiểm tra xem nếu toạ độ enemy lớn hơn toạ độ điểm rẽ - 3 và nhỏ hơn toạ độ điểm rẽ + 3
        if (x > t.getX() - 3 && x < t.getX() + 3 && y > t.getY() - 3 && y < t.getY() + 3) {
            reached = true;
            x = t.getX();
            y = t.getY();
        }
        return reached;
    }

    public void populateTurningPointList() {
        //thêm điểm rẽ đầu tiên vào mảng checkpoints
        turningPoints.add(findNextTurningPoint(eTile, directions = findNextDirection(eTile)));
        int count = 0;
        boolean cont = true;
        while (cont){
            //tìm hướng đi tiếp theo từ điểm rẽ hiện tại rồi add vào mảng currentDirection
            int[] currentDirection = findNextDirection(turningPoints.get(count).getTile());
            // kiểm tra xem điểm rẽ tiếp theo có tồn tại không, dừng lại sau 20 lần đếm
            if (currentDirection[0] == 100 || count == 20) {
                cont = false;
            }
            else {
                turningPoints.add(findNextTurningPoint(turningPoints.get(count).getTile(),
                        directions = findNextDirection(turningPoints.get(count).getTile())));
            }
            count++;
        }
    }

    public TurningPoint findNextTurningPoint (Tile start, int[] direc){
        Tile next = null;
        TurningPoint c = null;
        boolean found = false; //xem có tìm thấy ô rẽ tiếp theo không
        int count = 1; //tăng mỗi vòng lặp
        while (!found) {
            if (start.getPlaceX() + direc[0] * count == grid.getTileWide()
                    || start.getPlaceY() + direc[1] * count == grid.getTileHigh()
                    || start.getType() != grid.getTile(start.getPlaceX() + direc[0] * count,
                    start.getPlaceY() + direc[1] * count).getType()) {
                found = true; //đã tìm thấy
                count -= 1; //trở lại ô trước ô khác ô của enemy
                next = grid.getTile(start.getPlaceX() + direc[0] * count ,
                        start.getPlaceY() + direc[1] * count); //và next sẽ = ô đó
            }
            count++;
        }
        c = new TurningPoint(next, direc[0], direc[1]); //trả về điểm rẽ
        return c;
    }

    public int[] findNextDirection(Tile start) {
        int[] direc = new int[2];
        Tile up = grid.getTile(start.getPlaceX(), start.getPlaceY() - 1); //ô trên enemy
        Tile right = grid.getTile(start.getPlaceX() + 1, start.getPlaceY()); //ô bên phải enemy
        Tile down = grid.getTile(start.getPlaceX(), start.getPlaceY() + 1); //ô dưới enemy
        Tile left = grid.getTile(start.getPlaceX() - 1, start.getPlaceY()); //ô bên trái enemy
        if (start.getType() == up.getType() && directions[1] != 1) { //nếu ô trên enemy giống ô của enemy và đang không đi xuống
            direc[0] = 0;
            direc[1] = -1;
        }
        else if (start.getType() == right.getType() && directions[0] != -1) { //nếu ô bên phải enemy giống ô của enemy và đang không sang trái
            direc[0] = 1;
            direc[1] = 0;
        }
        else if (start.getType() == down.getType() && directions[1] != -1) { //nếu ô dưới enemy giống ô của enemy và đang không đi lên
            direc[0] = 0;
            direc[1] = 1;
        }
        else if (start.getType() == left.getType() && directions[0] != 1) { //nếu ô bên trái enemy giống ô của enemy và đang không sang phải
            direc[0] = -1;
            direc[1] = 0;
        }
        else {  //không đi hướng nào
            direc[0] = 100;
            direc[1] = 100;
        }
        return direc;
    }

    //vẽ enemy và vẽ máu
    public void draw() {
        //vẽ máu dựa trên tỉ lệ giữa máu hiện tại và tổng máu
        float percent = health / totalHealth;
        drawTexture (texture, x, y, width, height);
        drawTexture(healthBackgound, x, y - 16, width, 8);
        drawTexture(healthForeground, x, y - 16, TILE * percent, 8);
        drawTexture(healthBorder, x, y - 16, width, 8);
    }

    public void reduceCheck(float amount) {
        check -= amount;
    }

    public float getCheck() { return check; }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Tile geteTile() {
        return eTile;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public TileGrid getTileGrid() {
        return grid;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setTexture(String name) {
        this.texture = quickLoad(name);
    }
}