package Objects;

import Framework.GameObject;
import Framework.ObjectId;

import java.awt.*;
import java.util.LinkedList;

public class Test extends GameObject {

    public Test(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    public void tick(LinkedList<GameObject> object) {

    }

    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int)x,(int)y,32,32);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

}
