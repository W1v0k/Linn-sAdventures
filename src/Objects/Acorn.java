package Objects;

import Framework.GameObject;
import Framework.ObjectId;
import Framework.Texture;
import Window.Animation;
import Window.Game;
import Window.Handler;

import java.awt.*;
import java.util.LinkedList;

public class Acorn extends GameObject {

    private Animation acornCycle;
    private Handler handler;
    Texture tex = Game.getInstance();

    public Acorn(float x, float y, Handler handler, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
        acornCycle = new Animation(4, tex.acorns[0], tex.acorns[1], tex.acorns[2], tex.acorns[3], tex.acorns[4], tex.acorns[5], tex.acorns[6], tex.acorns[7], tex.acorns[8]);
    }

    public void tick(LinkedList<GameObject> object) {
        acornCycle.runAnimation();
    }

    public void render(Graphics g) {
        acornCycle.drawAnimation(g, (int)x, (int)y, 26,26);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }
}
