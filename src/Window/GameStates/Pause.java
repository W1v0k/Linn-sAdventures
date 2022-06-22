package Window.GameStates;

import Framework.ExceptionClass;
import UI.PauseButton;
import UI.SoundButton;
import Window.Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


public class Pause {
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    public SoundButton musicButton, sfxButton;

    public Pause() throws ExceptionClass {
        loadBackground();
        createSoundButtons();
    }

    private void createSoundButtons() throws ExceptionClass {
        musicButton = new SoundButton(515, 216,42,42);
        sfxButton = new SoundButton(515, 262,42,42);
    }

    private void loadBackground() {
        backgroundImg = Game.getGameInst().pauseBanner;
        bgW = backgroundImg.getWidth();
        bgH = backgroundImg.getHeight();
        bgX = Game.WIDTH/2 - bgW/2; //center
        bgY = 100;
    }

    public void tick(){
        musicButton.tick();
        sfxButton.tick();
    }

    public void render(Graphics g){
        g.drawImage(backgroundImg,bgX,bgY,bgW,bgH, null);
        musicButton.render(g);
        sfxButton.render(g);
    }

    public void mouseMoved(MouseEvent e){
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        if(isIn(e,musicButton))
            musicButton.setMouseOver(true);
        else if(isIn(e,sfxButton))
            sfxButton.setMouseOver(true);
    }

    public void mouseReleased(MouseEvent e){
        if(isIn(e,musicButton)) {
            if(musicButton.getMousePressed()){
                musicButton.setMuted(!musicButton.getMuted());
                if(musicButton.getMuted()){
                    musicStop();
                }else{
                    musicPlay();
                }
            }
        }
        else if(isIn(e,sfxButton)) {
            if (sfxButton.getMousePressed())
                sfxButton.setMuted(!sfxButton.getMuted());
        }

        musicButton.resetBools();
        sfxButton.resetBools();
    }

    public void mousePressed(MouseEvent e){
        if(isIn(e,musicButton)){
            musicButton.setMousePressed(true);
        }
        else if(isIn(e,sfxButton))
            sfxButton.setMousePressed(true);
    }

//    public void mouseDragged(MouseEvent e){ //for volume slider
//
//    }

    private boolean isIn(MouseEvent e, PauseButton pb){
        return pb.getBounds().contains(e.getX(), e.getY());
    }

    private void musicStop(){
        switch(Game.gameState){
            case MENU, PAUSE -> {
                Game.menuSong.stop();
            }
            case GAME ->{
                Game.themeSong.stop();
            }
        }
    }

    private void musicPlay(){
        switch(Game.gameState){
            case MENU, PAUSE -> {
                Game.menuSong.play(10);
            }
            case GAME ->{
                Game.themeSong.play(10);
            }
        }
    }
}
