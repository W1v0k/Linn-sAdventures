package Window.GameStates;

import UI.MenuButton;
import Window.Game;

import java.awt.*;

public class Win{

//    public Rectangle playButton = new Rectangle(Game.WIDTH/2 + 225, 350, 125, 50);
//    public Rectangle tutorialButton = new Rectangle(Game.WIDTH/2 + 175, 450, 225, 50);
//    public Rectangle quitButton = new Rectangle(Game.WIDTH/2 + 225, 550, 125, 50);
    public MenuButton quitButton;

    public Win(){
        loadButtons();
    }

    private void loadButtons() {
        quitButton = new MenuButton(Game.WIDTH/2 + 300, 425, 2, GameState.QUIT);
    }

    public void tick(){
        quitButton.tick();
    }

    public void render(Graphics g){
        quitButton.render(g);
    }

    public void resetButtons() {
        quitButton.resetBools();
    }


}