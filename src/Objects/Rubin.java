package Objects;

import Framework.GameObject;
import Framework.ObjectId;
import Framework.Texture;
import Window.Animation;
import Window.Game;
import Window.Handler;

import java.awt.*;
import java.util.LinkedList;

public class Rubin extends GameObject {

    private Animation rubinCycle;
    private Handler handler;
    Texture tex = Game.getInstance();

    public Rubin(float x, float y, Handler handler, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
        rubinCycle = new Animation(5, tex.rubin[0], tex.rubin[1], tex.rubin[2], tex.rubin[3]);
    }

    public void tick(LinkedList<GameObject> object) {
        rubinCycle.runAnimation();
    }

    public void render(Graphics g) {
        rubinCycle.drawAnimation(g, (int)x, (int)y, 32,32);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }
}
