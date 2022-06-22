package Objects;

import Framework.GameObject;
import Framework.ObjectId;
import Framework.Texture;
import Window.Game;

import java.awt.*;
import java.util.LinkedList;

public class TransparentBlock extends GameObject {
    Texture tex = Game.getInstance();

    public TransparentBlock(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    public void tick(LinkedList<GameObject> object) {
    }

    public void render(Graphics g) {
        g.drawImage(tex.transparentBlock, (int)x, (int)y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }

}
