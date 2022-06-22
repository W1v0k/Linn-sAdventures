package Window;

import Framework.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation {
    private int speed;
    private int frames;

    private int index = 0;
    private int count = 0;

    private BufferedImage[] images;
    private BufferedImage currentImg;

    private Texture tex = Game.getInstance();

    private static int swordSlash; //for attack animation

    public Animation(int speed, BufferedImage... args){ //infinite amount of parameters containing the buffered image
        this.speed = speed;
        images = new BufferedImage[args.length];
        for(int i=0; i< args.length; ++i){
            images[i] = args[i];
        }
        frames = args.length;
    }

    public void runAnimation(){
        index++;
        if(index > speed){
            index = 0;
            nextFrame();
        }
    }

    private void nextFrame(){
        for(int i = 0 ; i < frames; ++i){
            if(count == i){
                currentImg = images[i];
            }
        }
        count++;

        if(count > frames){
            count = 0;
        }
    }

    //FOR SWORD ANIMATION, SOUND NEEDS TO WAIT THE ANIMATION

    public void runAnimation(boolean attackAnimation){
        index++;
        if(index > speed){
            index = 0;
            nextFrame(attackAnimation);
        }
    }
    private void nextFrame(boolean attackAnimation){
        for(int i = 0 ; i < frames; ++i){
            if(count == i){
                currentImg = images[i];
            }
        }
        count++;

        if(count > frames){ //it finished one iteration of animation
            swordSlash++;
            if(swordSlash >= 3){
                swordSlash = 0;
            }
            Game.attack[swordSlash].play("attack");
            count = 0;
        }
    }

    public void drawAnimation(Graphics g, int x, int y){
        g.drawImage(currentImg, x, y, null);
    }

    public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY){
        g.drawImage(currentImg, x, y, scaleX, scaleY, null);
    }

    public void drawAnimation(Graphics g, int x, int y, int scaleX, int scaleY, boolean flip){
        if(flip){
            if(this.currentImg == null){    //when falling on level 1, in first water while going left, error could appear in which this.currentImg == null
                currentImg = tex.playerJump;
            }
                g.drawImage(currentImg, x + currentImg.getWidth() + currentImg.getWidth(), y, -scaleX, scaleY, null);

        }else{
            g.drawImage(currentImg, x, y, scaleX, scaleY, null);
        }
    }
}
