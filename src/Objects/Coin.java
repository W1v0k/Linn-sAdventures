package Objects;

import Framework.GameObject;
import Framework.ObjectId;
import Framework.Texture;
import Window.*;

import java.awt.*;
import java.util.LinkedList;

public class Coin extends GameObject {

    private Animation coinCycle;
    private Handler handler;
    Texture tex = Game.getInstance();

    public Coin(float x, float y, Handler handler, ObjectId id, boolean hurtsPlayer) {
        super(x, y, id);
        this.handler = handler;
        this.hurtsPlayer = hurtsPlayer;
        coinCycle = new Animation(5, tex.coins[0], tex.coins[1], tex.coins[2], tex.coins[3], tex.coins[4], tex.coins[5], tex.coins[6], tex.coins[7]);
    }

    public void tick(LinkedList<GameObject> object) {
        coinCycle.runAnimation();
    }

    public void render(Graphics g) {
        coinCycle.drawAnimation(g, (int)x, (int)y, 24,24);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }
}
