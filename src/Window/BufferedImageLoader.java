package Window;

import Framework.ExceptionClass;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BufferedImageLoader {
    private BufferedImage image;

    public BufferedImage loadImage(String path) throws ExceptionClass {
        try {
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ExceptionClass("Failed loading the image given at the path String !!!", e);
        }
        return image;
    }
}
