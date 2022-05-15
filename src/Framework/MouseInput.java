package Framework;

import Window.Game;
import Window.GameStates.GameState;
import Window.GameStates.Menu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
//        /*
//        public Rectangle playButton = new Rectangle(Game.WIDTH/2 + 225, 350, 125, 50);
//        public Rectangle tutorialButton = new Rectangle(Game.WIDTH/2 + 175, 450, 225, 50);
//        public Rectangle quitButton = new Rectangle(Game.WIDTH/2 + 225, 550, 125, 50);
//         */
        //PLAY button
        if(mx >= Game.WIDTH/2 + 300 - 70 && mx <= Game.WIDTH/2 + 300 + 70){ //300 - 70 -> x start point - width/2
            if(my >= 350 && my <= 350+56){
                //Pressed the play button
                Game.menuSong.stop();
                Game.themeSong.play(10);
                Game.LEVEL = 1;
                Game.gameState = GameState.GAME;
            }
        }


        //TUTORIAL button
        if(mx >= Game.WIDTH/2 + 300 - 70 && mx <= Game.WIDTH/2 + 300 + 70){
            if(my >= 425 && my <= 425+56){
                //Pressed the play button
                System.out.println("Am apasat butonu de tutorial");
            }
        }

        //QUIT button
        if(mx >= Game.WIDTH/2 + 300 - 70 && mx <= Game.WIDTH/2 + 300 + 70){ //225+125 -> x start point + width
            if(my >= 500 && my <= 500+56){
                //Pressed the quit button
                System.exit(1);
            }
        }
//        switch(GameState.state){
//            case MENU -> {
//                Game.getMenu().mousePressed(e);
//                break;
//            }
//        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch(GameState.state){
            case MENU -> {
                Game.getMenu().mouseReleased(e);
                break;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
//        switch(GameState.state){
//            case MENU -> {
//                Game.getMenu().mouseMoved(e);
//                break;
//            }
//            }
        }
}
