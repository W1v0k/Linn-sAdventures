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

public class Dogger extends GameObject {

    private float width = 32, height = 32;
    private Animation doggerRun;
    private Handler handler;
    Texture tex = Game.getInstance();
    private boolean faceLeft;
    private final float MAX_SPEED = 10;
    private int health = 100;


    private Random random = new Random();

    public Dogger(float x, float y, Handler handler, ObjectId id, boolean hurtsPlayer){
        super(x, y, id);
        this.handler = handler;
        this.hurtsPlayer = hurtsPlayer;
        int dir = random.nextInt(2);    //direction
        switch (dir){
            case 0:
                setVelX(-2);
                break;
            case 1:
                setVelX(2);
                break;
        }
        doggerRun = new Animation(5, tex.doggerRun[0], tex.doggerRun[1], tex.doggerRun[2], tex.doggerRun[3], tex.doggerRun[4], tex.doggerRun[5], tex.doggerRun[6], tex.doggerRun[7]);
    }

    public void tick(LinkedList<GameObject> object) {

        if(health <= 0){
            handler.object.remove(this);
            Game.score += 10;
        }

        x += velX;
        y += velY;

        doggerRun.runAnimation();

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
                    setVelX(-2);
                }

                if (getBoundsLeft().intersects(tempObject.getBounds())) { //Collision left
                    setVelX(+2);
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
            doggerRun.drawAnimation(g, (int) x - 128 - 48 - 48, (int) y - 16, 48, 48, true);
        }else{
            doggerRun.drawAnimation(g, (int) x , (int) y - 16, 48, 48);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int) (x+(width/2)-(width/4)), (int) ((int)y), (int)width/2, (int)32);
    }

    public Rectangle getBoundsRight() {
        if(faceLeft)
            return new Rectangle((int) (x+width-8),(int)y+5, 5, (int)height-10);
        else
            return new Rectangle((int) (x+width+4),(int)y+5, 5, (int)height-10);
    }
    public Rectangle getBoundsLeft() {
        if(faceLeft)
            return new Rectangle((int)x- 8,(int)y+5, 5, (int)height-10);
        else
            return new Rectangle((int) (x),(int)y+5, 5, (int)height-10);
    }
}
