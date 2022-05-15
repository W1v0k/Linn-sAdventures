package Window.GameStates;

import Window.Game;

import java.awt.*;

public class Pause {

    public Rectangle playButton = new Rectangle(Game.WIDTH/2 + 225, 350, 125, 50);
    public Rectangle tutorialButton = new Rectangle(Game.WIDTH/2 + 175, 450, 225, 50);
    public Rectangle quitButton = new Rectangle(Game.WIDTH/2 + 225, 550, 125, 50);

    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        Font titleFont = new Font("Comic Sans MS", Font.BOLD, 50);
        g.setFont(titleFont);
        g.setColor(Color.white);

        Font buttonFont = new Font("Comic Sans MS", Font.BOLD, 35);
        g.setFont(buttonFont);

        g2d.setStroke(new BasicStroke(4f));

        //PLAY
        g.setColor(Color.white);
        g.fillRect(playButton.x, playButton.y, playButton.width, playButton.height);
        g.setColor(Color.black);
        g2d.draw(playButton);
        g.setColor(Color.black);
        g.drawString("PLAY", playButton.x + 22, playButton.y + 37);

        //TUTORIAL
        g.setColor(Color.white);
        g.fillRect(tutorialButton.x, tutorialButton.y, tutorialButton.width, tutorialButton.height);
        g.setColor(Color.black);
        g2d.draw(tutorialButton);
        g.setColor(Color.black);
        g.drawString("TUTORIAL", tutorialButton.x + 17, tutorialButton.y + 37);

        //QUIT
        g.setColor(Color.white);
        g.fillRect(quitButton.x, quitButton.y, quitButton.width, quitButton.height);
        g.setColor(Color.black);
        g2d.draw(quitButton);
        g.setColor(Color.black);
        g.drawString("QUIT", quitButton.x + 13, quitButton.y + 37);

    }
}
