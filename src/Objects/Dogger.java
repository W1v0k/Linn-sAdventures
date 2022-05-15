package Objects;

import Framework.GameObject;
import Framework.ObjectId;
import Framework.Texture;
import Window.Animation;
import Window.Game;
import Window.Handler;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Dogger extends GameObject {

    private Animation doggerIdle;
    private Handler handler;
    Texture tex = Game.getInstance();
    private boolean faceLeft;

    public Dogger(float x, float y, Handler handler, ObjectId id, boolean faceLeft, boolean hurtsPlayer) {
        super(x, y, id);
        this.handler = handler;
        this.faceLeft = faceLeft;
        this.hurtsPlayer = hurtsPlayer;
        doggerIdle = new Animation(5, tex.doggerIdle[0], tex.doggerIdle[1], tex.doggerIdle[2], tex.doggerIdle[3], tex.doggerIdle[4], tex.doggerIdle[5]);
    }

    public void tick(LinkedList<GameObject> object) {
        doggerIdle.runAnimation();
        Collision(object);
    }

    private void Collision(LinkedList<GameObject> object){
        for(int i = 0 ; i < handler.object.size(); ++i){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ObjectId.Projectile){
                if(getBounds().intersects(tempObject.getBounds())){
                    handler.object.remove(this);
                    handler.object.remove(tempObject);
                }
            }
        }
    }

    public void render(Graphics g) {
        if(faceLeft){
            doggerIdle.drawAnimation(g, (int) x - 128 - 48 - 48, (int) y - 16, 48, 48, true);

        }else{
            doggerIdle.drawAnimation(g, (int) x , (int) y - 16, 48, 48);
        }
    }

    public Rectangle getBounds() {
        if(facingLeft)
            return new Rectangle((int)x - 16, (int)y - 16,48, 48);
        else
            return new Rectangle((int)x, (int)y - 16,48, 48);
    }
}
