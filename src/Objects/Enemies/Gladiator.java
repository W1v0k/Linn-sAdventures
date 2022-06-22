package Objects.Enemies;

import Framework.GameObject;
import Framework.ObjectId;
import Framework.Texture;
import Window.Animation;
import Window.Game;
import Window.Handler;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Gladiator extends GameObject {

    private float width = 32, height = 32;
    private Animation gladRun;
    private Handler handler;
    Texture tex = Game.getInstance();
    private boolean faceLeft;

    private final float MAX_SPEED = 8;

    private int health = 150;

    private Random random = new Random();

    public Gladiator(float x, float y, Handler handler, ObjectId id, boolean hurtsPlayer) {
        super(x, y, id);
        this.handler = handler;
        this.hurtsPlayer = hurtsPlayer;
        int dir = random.nextInt(2);
        switch(dir){
            case 0:
                setVelX(-1);
                break;
            case 1:
                setVelX(1);
                break;
        }
        gladRun = new Animation(7, tex.gladiatorRun[0], tex.gladiatorRun[1], tex.gladiatorRun[2], tex.gladiatorRun[3], tex.gladiatorRun[4], tex.gladiatorRun[5], tex.gladiatorRun[6], tex.gladiatorRun[7]);
    }

    public void tick(LinkedList<GameObject> object) {
        if(health <= 0){
            handler.object.remove(this);
            Game.score += 20;
        }

        x += velX;
        y += velY;

        gladRun.runAnimation();

        if(velX < 0){
            faceLeft = true;
        }else if(velX > 0){
            faceLeft = false;
        }

        if(falling){
            velY += gravity;
            if(velY > MAX_SPEED){
                velY = MAX_SPEED;
            }
        }
        Collision(object);
    }

    private void Collision(LinkedList<GameObject> object){
        for(int i = 0 ; i < handler.object.size(); ++i){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ObjectId.Block || tempObject.getId() == ObjectId.TransparentBlock) {

                if (getBounds().intersects(tempObject.getBounds())) { //Collision at the feet
                    y = tempObject.getY() - height;
                    setVelY(0);
                    if (falling) falling = false;
                } else if (!falling) {
                    falling = true;
                    gravity = 0.8f;
                }

                if (getBoundsRight().intersects(tempObject.getBounds())) { //Collision right
                    setVelX(-1);
                }

                if (getBoundsLeft().intersects(tempObject.getBounds())) { //Collision left
                    setVelX(+1);
                }
            }

            if(tempObject.getId() == ObjectId.Projectile){
                if(getBounds().intersects(tempObject.getBounds())){
                    health -= 25;
                    handler.object.remove(tempObject);
                }
            }

            if(tempObject.getId() == ObjectId.Player){
                if(getBoundsRight().intersects(tempObject.getBounds()) && tempObject.isAttacking()){
                    x -= 35;
                    health -= 15;
                }
                if(getBoundsLeft().intersects(tempObject.getBounds()) && tempObject.isAttacking()){
                    x += 35;
                    health -= 15;
                }
            }
        }
        if(falling){
            gravity += 0.1;
            setVelY(gravity);
        }
    }

    public void render(Graphics g) {
//        g.setColor(Color.red);
//        Graphics2D graphics2D = (Graphics2D)g;
//        graphics2D.draw(getBoundsRight());
//        g.setColor(Color.green);
//        graphics2D.draw(getBoundsLeft());
//        g.setColor(Color.magenta);
//        graphics2D.draw(getBounds());

        if(faceLeft){
            gladRun.drawAnimation(g, (int) x - 20, (int) y - 52, 84, 84, true);

        }else{
            gladRun.drawAnimation(g, (int) x - 20, (int) y - 52, 84, 84);
        }
    }

    public Rectangle getBounds() {
        if(faceLeft)
            return new Rectangle((int)x - 16, (int)y - 26, (int)width , 56);
        else
            return new Rectangle((int)x, (int)y - 26, (int)width , 56);
    }

    public Rectangle getBoundsRight() {
        if(faceLeft)
            return new Rectangle((int) (x+width-8),(int)y-24, 5, (int)height+8);
        else
            return new Rectangle((int) (x+width+4),(int)y-24, 5, (int)height+8);
    }
    public Rectangle getBoundsLeft() {
        if(faceLeft)
            return new Rectangle((int)x - 28,(int)y-24, 5, (int)height+8);
        else
            return new Rectangle((int)x - 8,(int)y-24, 5, (int)height+8);
    }
}
