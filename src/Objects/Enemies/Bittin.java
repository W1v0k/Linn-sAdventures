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

public class Bittin extends GameObject {    //Poate fi omorat DOAR cu o ghinde

    private float width = 48, height = 48;
    private Animation bittinRun;
    private Handler handler;
    Texture tex = Game.getInstance();
    private boolean faceLeft;
    private final float MAX_SPEED = 12;
    private int health = 50;


    private Random random = new Random();

    public Bittin(float x, float y, Handler handler, ObjectId id, boolean hurtsPlayer){
        super(x, y, id);
        this.handler = handler;
        this.hurtsPlayer = hurtsPlayer;
        int dir = random.nextInt(2);    //direction
        switch (dir){
            case 0:
                setVelX(-3);
                break;
            case 1:
                setVelX(3);
                break;
        }
        bittinRun = new Animation(4, tex.bittinRun[0], tex.bittinRun[1], tex.bittinRun[2], tex.bittinRun[3], tex.bittinRun[4]);
    }

    public void tick(LinkedList<GameObject> object) {
        jumping = false;
        if(health <= 0){
            handler.object.remove(this);
            Game.score += 5;
        }

//        int jump = random.nextInt(100);
//        if(jump == 22 && !jumping){
//            y -= 20;
//            System.out.println("We are here");
//            jumping = true;
//        }

        x += velX;
        y += velY;

        bittinRun.runAnimation();

        if(velX < 0){
            faceLeft = true;
        }else if(velX > 0){
            faceLeft = false;
        }

        if(falling){
            velY += gravity;

            if(velY > MAX_SPEED){   //velocity can increase quite rapidly
                                    //because of that, we will diminish the speed at which the player will fall
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
                    setVelX(-4);
                }

                if (getBoundsLeft().intersects(tempObject.getBounds())) { //Collision left
                    setVelX(+4);
                }
            }
        if(tempObject.getId() == ObjectId.Projectile){
                if(getBounds().intersects(tempObject.getBounds())){
                    health -= 50;
                    handler.object.remove(tempObject);
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
            bittinRun.drawAnimation(g, (int) x - 8, (int) y - 8, 48, 48, true);
        }else{
            bittinRun.drawAnimation(g, (int) x - 8 , (int) y - 8, 48, 48);
        }
    }

    public Rectangle getBounds() {
        if(faceLeft)
            return new Rectangle((int)x + 56, (int)y, (int)width - 24, 28);
        else
            return new Rectangle((int)x + 4, (int)y, (int)width - 24, 28);
    }

    public Rectangle getBoundsRight() {
        if(faceLeft)
            return new Rectangle((int) (x + width + 40),(int)y+5, 5, (int)height-30);
        else
            return new Rectangle((int)(x + width - 12),(int)y+5, 5, (int)height-30);
    }
    public Rectangle getBoundsLeft() {
        if(faceLeft)
            return new Rectangle((int)x + 42,(int)y+5, 5, (int)height-30);
        else
            return new Rectangle((int) (x) - 8,(int)y+5, 5, (int)height-30);
    }
}
