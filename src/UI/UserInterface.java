package UI;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.util.ArrayList;

import static helpers.Drawer.*;

public class UserInterface {
    private ArrayList<Button> buttons; //list các nút
    private ArrayList<Menu>   menuList; //list các menu
    private Font              text;
    private TrueTypeFont      normaltext;

    public UserInterface() {
        buttons =    new ArrayList<Button>();
        menuList =   new ArrayList<Menu>();
        text =       new Font("Times New Roman", Font.BOLD, 24);
        normaltext = new TrueTypeFont(text, false);
    }

    public void drawText(int x, int y, String text) {
        normaltext.drawString(x, y, text);
    }

    //thêm nút vào list
    public void addButton(String name, String textureName, int x, int y){
        buttons.add(new Button(name, quickLoad(textureName), x, y));
    }

    public boolean isButtonClicked (String butt) {
        Button b = getButton(butt);
        float mouseY = HEIGHT - Mouse.getY() - 1;
        //nếu chuột click vào khoảng của nút thì trả về true
        if (Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() && mouseY > b.getY() && mouseY < b.getY() + b.getHeight()) {
            return true;
        }
        return false;
    }

    //lấy nút từ list
    public Button getButton(String butt) {
        for (Button b: buttons) {
            if (b.getName().equals(butt)) {
                return b;
            }
        }
        return null;
    }

    //thêm menu vào list
    public void createMenu(String name, int x, int y, int sideWidth, int sideHeight, int width, int height) {
        menuList.add(new Menu(name, x, y, sideWidth, sideHeight, width, height));
    }

    //lấy menu từ list
    public Menu getMenu(String name) {
        for (Menu m: menuList)
            if (name.equals(m.getName()))
                return m;
        return null;
    }

    //vẽ button và menu
    public void draw() {
        for (Button b: buttons) {
            drawTexture(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
        }
        for (Menu m: menuList) {
            m.draw();
        }
    }

    //class con Menu
    public class Menu {
        String      name; //tên của menu
        private     ArrayList<Button> keys; //list cac nút trong menu
        private int x, y, keyNumber, width, height, sideWidth, sideHeight, filling;

        public Menu(String name, int x, int y, int sideWidth, int sideHeight, int width, int height) {
            this.name =       name;
            this.x =          x;
            this.y =          y;
            this.width =      width;
            this.height =     height;
            this.sideWidth =  sideWidth;
            this.sideHeight = sideHeight;
            this.filling =    (sideWidth - (width * TILE)) / (width + 1);
            this.keyNumber =  0;
            this.keys =       new ArrayList<Button>();
        }

        public void addKey (Button b) {
            setKey(b);
        }

        //load texture cho nút
        public void quickAccess(String name, String buttonTex) {
            Button b = new Button(name, quickLoad(buttonTex), 0, 0);
            setKey(b);
        }

        private void setKey(Button b) {
            if (width != 0)
                b.setY(y + (keyNumber / width) * TILE);
            b.setX(x + (keyNumber % width) * (filling + TILE) + filling);
            keyNumber+=2;
            keys.add(b);
        }

        //kiểm tra nút có đc bấm không
        public boolean isButtonClicked (String butt) {
            Button b = getButton(butt);
            float mouseY = HEIGHT - Mouse.getY() - 1;
            if (Mouse.getX() > b.getX() && Mouse.getX() < b.getX() + b.getWidth() && mouseY > b.getY() && mouseY < b.getY() + b.getHeight()) {
                return true;
            }
            return false;
        }

        //lấy nút từ trong list
        public Button getButton(String butt) {
            for (Button b: keys) {
                if (b.getName().equals(butt)) {
                    return b;
                }
            }
            return null;
        }

        //vẽ nút
        public void draw() {
            for (Button b: keys) {
                drawTexture(b.getTexture(), b.getX(), b.getY(), b.getWidth(), b.getHeight());
            }
        }

        public String getName() { return name; }
    }
}