package helpers;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import java.io.IOException;
import java.io.InputStream;
import static org.lwjgl.opengl.GL11.*;

public class Drawer {
    public static final int WIDTH = 1600, HEIGHT = 896, TILE = 64;

    public static void beginSession () {
        Display.setTitle("Tower Defense");
        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        //vẽ 2d của lwjgl
        glMatrixMode (GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glEnable(GL_TEXTURE_2D);
        //blend ảnh png
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static boolean isCollided(float x1, float y1, float width1, float height1,
                                     float x2, float y2, float width2, float height2) {
        if (x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2)
            return true;
        return false;
    }

    //vẽ ô vuông
    public static void drawTexture (Texture texture, float x, float y, float width, float height){
        texture.bind();
        glTranslatef(x, y, 0);
        glBegin(GL_QUADS);
        //vẽ góc trái trên
        glTexCoord2f(0, 0);
        glVertex2f(0,0);
        //vẽ góc phải trên
        glTexCoord2f(1, 0);
        glVertex2f(width, 0);
        //vẽ góc phải dưới
        glTexCoord2f(1, 1);
        glVertex2f(width, height);
        //ve góc trái dưới
        glTexCoord2f(0, 1);
        glVertex2f(0, height);
        glEnd();
        glLoadIdentity();
    }

    //vẽ xoay
    public static void drawRotation (Texture texture, float x, float y, float width, float height, float angle){
        texture.bind();
        glTranslatef(x + width / 2, y + height / 2, 0); //xoay từ trung tâm bức ảnh
        glRotatef(angle, 0, 0, 1);
        glTranslatef(- width / 2, - height / 2, 0);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0);
        glVertex2f(0,0);
        glTexCoord2f(1, 0);
        glVertex2f(width, 0);
        glTexCoord2f(1, 1);
        glVertex2f(width, height);
        glTexCoord2f(0, 1);
        glVertex2f(0, height);
        glEnd();
        glLoadIdentity();
    }

    //load texture
    public static Texture loadTexture (String path, String fileType){
        Texture tex = null;
        InputStream in = ResourceLoader.getResourceAsStream(path);
        try {
            tex = TextureLoader.getTexture(fileType, in);
        } catch (IOException e){
            e.printStackTrace();
        }
        return tex;
    }

    //load ảnh png
    public static Texture quickLoad (String name){
        Texture tex = null;
        tex = loadTexture("res/" + name + ".png", "PNG");
        return tex;
    }
}
