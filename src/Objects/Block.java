package Objects;

import Framework.*;
import Window.*;

import java.awt.*;
import java.util.LinkedList;

public class Block extends GameObject {
    Texture tex = Game.getInstance();
    private int type;
    public Block(float x, float y, int type, ObjectId id, boolean hurtsPlayer) {
        super(x, y, id);
        this.type = type;
        this.hurtsPlayer = hurtsPlayer;
    }

    public void tick(LinkedList<GameObject> object) {
    }

    public void render(Graphics g) {
        if(type == 0){  //grass
            g.drawImage(tex.block[0], (int)x, (int)y, null);
        }

        if(type == 1){  //dirt
            g.drawImage(tex.block[1], (int)x, (int)y, null);
        }

        if(type == 2){  //water
            g.drawImage(tex.block[2], (int)x, (int)y, null);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, 32, 32);
    }

}
