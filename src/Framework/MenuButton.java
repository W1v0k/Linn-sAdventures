package Framework;

import java.awt.*;
import Window.*;
import Window.GameStates.GameState;

import java.awt.image.BufferedImage;

public class MenuButton {
    private int xPos, yPos, rowIndex, index;
    private int xOffsetCenter = 140/2;  //button width/2
    Texture tex = Game.getInstance();
    private GameState state;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;
    
    public MenuButton(int xPos, int yPos, int rowIndex, GameState state){
        this.xPos = xPos;
        this.yPos = yPos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos-xOffsetCenter, yPos, 140, 56);
    }

    public void applyGameState(){
        GameState.state = state;
    }

    private void loadImgs(){
        imgs = new BufferedImage[3];    //default,hovered,clicked
        for(int i = 0 ; i < imgs.length; ++i){
            imgs[i] = tex.btns.grabImage(i+1 , rowIndex+1, 140, 56);
        }
    }

    public void tick(){
        index = 0;
        if(mouseOver){  //hover
            index = 1;
            System.out.println("SUNT DEASUPRA");
        }
        if(mousePressed){
            index = 2;
        }
    }

    public void render(Graphics g){
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, 140, 56,null);
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }

    public Rectangle getBounds(){
        return bounds;
    }
}
