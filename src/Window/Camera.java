package Window;

import Framework.GameObject;

public class Camera {   //we don't want this as a gameObj because we will not remove it

    private float x, y;

    public Camera(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject player){    //we need the cam to snap onto the player
        //TWEENING Algorithm
        x = -player.getX() + Game.WIDTH/2;
    }

    public void setX(float x){this.x = x;}
    public void setY(float y){this.y = y;}

    public float getX(){return x;}
    public float getY(){return y;}
}
