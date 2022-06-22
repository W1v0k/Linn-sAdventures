package Objects;

import Framework.GameObject;
import Framework.ObjectId;
import Framework.Texture;
import Window.Game;

import java.awt.*;
import java.util.LinkedList;

public class Potion extends GameObject {

    Texture tex = Game.getInstance();
    public Potion(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    public void tick(LinkedList<GameObject> object) {
    }

    public void render(Graphics g) {
          g.drawImage(tex.potionImg, (int)x - 8,(int)y - 8, 32, 32, null);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x - 8, (int)y - 8, 32, 32);
    }
}
