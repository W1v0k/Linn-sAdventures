package Framework;

import Window.BufferedImageLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {  //load all textures in the game
    SpriteSheet bs, ps, fs, projs, ds, btns; //bs - blockSheet, ps - playerSheet, fs - flagSheet, projs - projectileSheet, ds - doggerSheet, btns - buttonSheet

    public BufferedImage[] block = new BufferedImage[3];

    public BufferedImage[] playerRun = new BufferedImage[6];
    public BufferedImage[] playerAtk = new BufferedImage[5];
    public BufferedImage[] playerIdle = new BufferedImage[9];
    public BufferedImage[] playerHurt = new BufferedImage[2];
    public BufferedImage playerJump;

    public BufferedImage[] coins = new BufferedImage[8];
    public BufferedImage[] acorns = new BufferedImage[9];
    public BufferedImage projImg;   //projectile image
    public BufferedImage flagImg;   //flag image
    public BufferedImage[] buttons = new BufferedImage[3];

    //ENEMIES
    //Dogger
    public BufferedImage[] doggerIdle = new BufferedImage[6];

    public Texture(){
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage blockSheet = null;
        BufferedImage playerSheet = null;
        BufferedImage projSheet = null;
        BufferedImage flagSheet = null;
        BufferedImage doggerSheet = null;
        BufferedImage buttonSheet = null;
        try{
             blockSheet = loader.loadImage("/tileset/Tileset.png");
             playerSheet = loader.loadImage("/player/linn.png");
             projSheet = loader.loadImage("/player/acorn.png"); //used for the acorns shot
             flagSheet = loader.loadImage("/obj/yellowRight.png");
             doggerSheet = loader.loadImage("/enemies/dogger/idle.png");
             buttonSheet = loader.loadImage("/buttons/button_atlas_teal.png");

        }catch(Exception e){
            e.printStackTrace();
        }
        bs = new SpriteSheet(blockSheet);
        ps = new SpriteSheet(playerSheet);
        projs = new SpriteSheet(projSheet);
        fs = new SpriteSheet(flagSheet);
        ds = new SpriteSheet(doggerSheet);
        btns = new SpriteSheet(buttonSheet);
        getTextures();
    }

    private void getTextures(){
        block[0] = bs.grabImage(2,1,32,32);     //grass
        block[1] = bs.grabImage(2,2,32,32);     //dirt
        block[2] = bs.grabImage(4,10,32,32);    //water

        for(int i = 0 ; i < playerRun.length; ++i)  //run
            playerRun[i] = ps.grabImage(i+10,1,32,32);

        for(int i = 0 ; i < playerAtk.length; ++i)  //attacking
            playerAtk[i] = ps.grabImage(i+24,1,32,32);

        for(int i = 0 ; i < playerIdle.length;++i)  //idling
            playerIdle[i] = ps.grabImage((i+1),1,32,32);

        playerJump = ps.grabImage(16, 1, 32 ,32 );  //jumping

        playerHurt[0] = ps.grabImage(17, 1,32,32);  //hurting
        playerHurt[1] = ps.grabImage(18, 1,32,32);

        for(int i = 0 ; i < 8; ++i){    //coins
            try {
                coins[i] = ImageIO.read(new File("res/obj/coin_" + i + ".png"));
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        for(int i = 0 ; i < 9; ++i){    //acorns
            try {
                acorns[i] = ImageIO.read(new File("res/obj/acorn_" + i + ".png"));
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        flagImg = fs.grabImage(1,1,32,32);
        projImg = projs.grabImage(1,1,16,16);

        doggerIdle[0] = ds.grabImage(1,1,128,128);
        doggerIdle[1] = ds.grabImage(2,1,128,128);
        doggerIdle[2] = ds.grabImage(3,1,128,128);
        doggerIdle[3] = ds.grabImage(4,1,128,128);
        doggerIdle[4] = ds.grabImage(5,1,128,128);
        doggerIdle[5] = ds.grabImage(6,1,128,128);

        //PLAY
        buttons[0] = btns.grabImage(1,1,420,56);
        //OPTIONS
        buttons[1] = btns.grabImage(1,2,420,56);
        //QUIT
        buttons[2] = btns.grabImage(1,3,420,56);

    }
}
