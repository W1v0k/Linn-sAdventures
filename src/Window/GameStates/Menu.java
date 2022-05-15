package Window.GameStates;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Framework.MenuButton;
import Window.Game;

public class Menu extends State{

//    public Rectangle playButton = new Rectangle(Game.WIDTH/2 + 225, 350, 125, 50);
//    public Rectangle tutorialButton = new Rectangle(Game.WIDTH/2 + 175, 450, 225, 50);
//    public Rectangle quitButton = new Rectangle(Game.WIDTH/2 + 225, 550, 125, 50);
    public MenuButton[] buttons = new MenuButton[3];

    public Menu(){
        loadButtons();
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.WIDTH/2 + 300 , 350, 0, GameState.GAME);
        buttons[1] = new MenuButton(Game.WIDTH/2 + 300, 425, 1, GameState.OPTIONS);
        buttons[2] = new MenuButton(Game.WIDTH/2 + 300, 500, 2, GameState.QUIT);
    }

    public void tick(){
        for(MenuButton mb : buttons)
            mb.tick();
    }

    public void render(Graphics g){
        for(MenuButton mb : buttons)
            mb.render(g);

    }


    public void mousePressed(MouseEvent e){
        for(MenuButton mb : buttons){
            if(isIn(e,mb)){
                mb.setMousePressed(true);
            }
        }
    }

    public void mouseReleased(MouseEvent e){
        for(MenuButton mb : buttons){
            if(isIn(e,mb)){
                if(mb.isMousePressed()){
                    mb.applyGameState();
                }
                break;
            }
        }
        resetButtons();
    }

    public void mouseMoved(MouseEvent e){
        for(MenuButton mb:buttons){
            mb.setMouseOver(false);
        }
        for(MenuButton mb:buttons){
            if(isIn(e,mb)){
                mb.setMouseOver(true);
                break;
            }
        }
    }

    public void resetButtons() {
        for(MenuButton mb : buttons)
            mb.resetBools();
    }


}

//OLD render for MENU
//        Font titleFont = new Font("Comic Sans MS", Font.BOLD, 50);
//        g.setFont(titleFont);
//        g.setColor(Color.white);
//
//        Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 35);
//        g.setFont(buttonFont);
//
//        g2d.setStroke(new BasicStroke(4f));
//
//        //PLAY
//        g.setColor(Color.white);
//        g.fillRect(playButton.x, playButton.y, playButton.width, playButton.height);
//        g.setColor(Color.black);
//        g2d.draw(playButton);
//        g.setColor(Color.black);
//        g.drawString("PLAY", playButton.x + 22, playButton.y + 37);
//
//        //TUTORIAL
//        g.setColor(Color.white);
//        g.fillRect(tutorialButton.x, tutorialButton.y, tutorialButton.width, tutorialButton.height);
//        g.setColor(Color.black);
//        g2d.draw(tutorialButton);
//        g.setColor(Color.black);
//        g.drawString("TUTORIAL", tutorialButton.x + 17, tutorialButton.y + 37);
//
//        //QUIT
//        g.setColor(Color.white);
//        g.fillRect(quitButton.x, quitButton.y, quitButton.width, quitButton.height);
//        g.setColor(Color.black);
//        g2d.draw(quitButton);
//        g.setColor(Color.black);
//        g.drawString("QUIT", quitButton.x + 13, quitButton.y + 37);
