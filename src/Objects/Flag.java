package Objects;

import Framework.*;
import Window.Game;

import java.awt.*;
import java.util.LinkedList;

public class Flag extends GameObject {

    Texture tex = Game.getInstance();
    public Flag(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    public void tick(LinkedList<GameObject> object) {
    }

    public void render(Graphics g) {
          g.drawImage(tex.flagImg, (int)x - 16,(int)y - 16, 48, 48, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x - 16, (int)y - 16, 48, 48);
    }
}
