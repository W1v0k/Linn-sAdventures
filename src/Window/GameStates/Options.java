package Window.GameStates;


import java.awt.*;
import java.awt.image.BufferedImage;

import Framework.ExceptionClass;
import Window.BufferedImageLoader;

public class Options {
    BufferedImageLoader loader = new BufferedImageLoader();
    private BufferedImage optionsBackground = null;
    public void render(Graphics g) throws ExceptionClass {
        optionsBackground = loader.loadImage("/bgs/options.png");
        g.drawImage(optionsBackground, 0, 0, null);
    }
}