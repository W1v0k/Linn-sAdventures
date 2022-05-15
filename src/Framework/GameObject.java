package Framework;

import java.awt.*;
import java.util.LinkedList;

public abstract class GameObject {  //abstract factory

    protected float x, y;
    protected ObjectId id ;
    protected float velX = 0, velY = 0;
    protected boolean falling = true;
    protected boolean jumping = false;
    protected boolean attacking = false;
    protected boolean facingLeft = false;   //Firstly, we only had it for our player.
                                            //Because we implement the projectile property,
                                            //we need to know in which way the player is
                                            //facing for that the projectile needs to
                                            //shoot in front of it.
    protected boolean hurtsPlayer = false;

    public GameObject(float x, float y, ObjectId id){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick(LinkedList<GameObject> object);   //for collision detection
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();  //collision bounding for the player

    public float getX(){
        return x;
    }
    public  float getY(){
        return y;
    }
    public  void setX(float x){
        this.x = x;
    }
    public  void setY(float y){
        this.y = y;
    }

    public  float getVelX(){
        return velX;
    }
    public  float getVelY(){
        return velY;
    }
    public  void setVelX(float velX){
        this.velX = velX;
    }
    public  void setVelY(float velY){
        this.velY = velY;
    }

    public boolean isFalling(){
        return falling;
    }
    public void setFalling(boolean falling){
        this.falling = falling;
    }

    public boolean isJumping(){
        return jumping;
    }
    public void setJumping(boolean jumping){
        this.jumping = jumping;
    }

    public boolean getFacingLeft(){ return this.facingLeft;}
    public boolean isHurtingPlayer(){return this.hurtsPlayer;}

    public boolean isAttacking(){return this.attacking;}
    public void setAttack(boolean attacking){this.attacking = attacking;}

    public ObjectId getId(){
        return id;
    }

}
