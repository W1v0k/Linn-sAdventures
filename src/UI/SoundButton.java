package UI;

import Framework.ExceptionClass;
import Window.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SoundButton extends PauseButton{

    private BufferedImage[][] soundImgs;
    private Boolean mouseOver=false, mousePressed=false;
    private Boolean muted = false;  //swap between first or second row
    private int rowIndex, colIndex;

    public SoundButton(int x, int y, int width, int height) throws ExceptionClass {
        super(x, y, width, height);

        loadSoundImgs();
    }

    private void loadSoundImgs() throws ExceptionClass {
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage temp = loader.loadImage("/buttons/sound_button_teal.png");
        soundImgs = new BufferedImage[2][3];    //2 rows, 3 columns
        for(int j = 0 ; j < soundImgs.length; ++j){
            for(int i = 0; i < soundImgs[j].length; ++i){
                soundImgs[j][i] = temp.getSubimage(i*42,j*42,42,42);
            }
        }
    }

    public void tick(){
        if(muted)
            rowIndex = 1;
        else
            rowIndex = 0;

        colIndex = 0;
        if(mouseOver)
            colIndex = 1;
        if(mousePressed)
            colIndex = 2;
    }

    public void render(Graphics g){
        g.drawImage(soundImgs[rowIndex][colIndex],x,y,width,height,null);
    }

    public Boolean getMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(Boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public Boolean getMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(Boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Boolean getMuted() {
        return muted;
    }

    public void setMuted(Boolean muted) {
        this.muted = muted;
    }

    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }
}
