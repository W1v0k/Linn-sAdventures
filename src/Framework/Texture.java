package Framework;

import Window.BufferedImageLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {  //load all textures in the game
    public SpriteSheet bs, ps, fs, projs, dsr, gsr, bits, btns, rs, tbs, hs, pots; //bs - blockSheet,
                                                            // ps - playerSheet,
                                                            // fs - flagSheet,
                                                            // projs - projectileSheet,
                                                            // dsr - doggerSheetRun,
                                                            // gsr - gladiatorSheetRun,
                                                            // bits - bittinSheetRun
                                                            // btns - buttonSheet,
                                                            // rs - rubinSheet
                                                            // tbs - transparentBlockSheet
                                                            // hs - healthSheet
                                                            // pots - potionSheet

    public BufferedImage[] block = new BufferedImage[3];
    public BufferedImage transparentBlock;

    public BufferedImage[] playerRun = new BufferedImage[6];
    public BufferedImage[] playerAtk = new BufferedImage[5];
    public BufferedImage[] playerIdle = new BufferedImage[9];
    public BufferedImage[] playerHurt = new BufferedImage[2];
    public BufferedImage playerJump;

    public BufferedImage[] coins = new BufferedImage[8];
    public BufferedImage[] acorns = new BufferedImage[9];
    public BufferedImage projImg;   //projectile image
    public BufferedImage flagImg;   //flag image
    public BufferedImage potionImg; //potion image
    public BufferedImage[] buttons = new BufferedImage[3];
    public BufferedImage[] rubin = new BufferedImage[4];
    public BufferedImage[] health = new BufferedImage[11];

    //ENEMIES
    //Dogger
    public BufferedImage[] doggerRun = new BufferedImage[8];

    public BufferedImage[] gladiatorRun = new BufferedImage[8];

    public BufferedImage[] bittinRun = new BufferedImage[5];

    public Texture() throws ExceptionClass {
        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage blockSheet;
        BufferedImage playerSheet;
        BufferedImage projSheet;
        BufferedImage flagSheet;
        BufferedImage potionSheet;
        BufferedImage buttonSheet;
        BufferedImage rubinSheet;
        BufferedImage transparentSheet;
        BufferedImage healthSheet;

        //ENEMIES
        BufferedImage doggerSheetRun;
        BufferedImage gladiatorSheetRun;
        BufferedImage bittinSheetRun;

        try{
             blockSheet = loader.loadImage("/tileset/Tileset.png");
             playerSheet = loader.loadImage("/player/linn.png");
             projSheet = loader.loadImage("/player/acorn.png"); //used for the acorns shot
             flagSheet = loader.loadImage("/obj/yellowRight.png");
             potionSheet = loader.loadImage("/potions/potions.png");
             buttonSheet = loader.loadImage("/buttons/button_atlas_teal.png");
             rubinSheet = loader.loadImage("/obj/rubin.png");
             transparentSheet = loader.loadImage("/obj/transparentTile.png");
             healthSheet = loader.loadImage("/health/healthStatus.png");

             //ENEMIES
            doggerSheetRun = loader.loadImage("/enemies/dogger/run.png");
            gladiatorSheetRun = loader.loadImage("/enemies/gladiator/gladiator.png");
            bittinSheetRun = loader.loadImage("/enemies/bittins/bittins.png");

        }catch(Exception e){
            e.printStackTrace();
            throw new ExceptionClass("Read of OBJECTS sheet FAILED!");
        }
        bs = new SpriteSheet(blockSheet);
        ps = new SpriteSheet(playerSheet);
        projs = new SpriteSheet(projSheet);
        fs = new SpriteSheet(flagSheet);
        pots = new SpriteSheet(potionSheet);
        btns = new SpriteSheet(buttonSheet);
        rs = new SpriteSheet(rubinSheet);
        tbs = new SpriteSheet(transparentSheet);
        hs = new SpriteSheet(healthSheet);

        //ENEMIES
        dsr = new SpriteSheet(doggerSheetRun);
        gsr = new SpriteSheet(gladiatorSheetRun);
        bits = new SpriteSheet(bittinSheetRun);
        getTextures();
    }

    private void getTextures() throws ExceptionClass {
        block[0] = bs.grabImage(2,1,32,32);     //grass
        block[1] = bs.grabImage(2,2,32,32);     //dirt
        block[2] = bs.grabImage(4,10,32,32);    //water
        transparentBlock = tbs.grabImage(1,1,32,32);

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
                throw new ExceptionClass("Read of COIN images FAILED!");
            }
        }

        for(int i = 0 ; i < 9; ++i){    //acorns
            try {
                acorns[i] = ImageIO.read(new File("res/obj/acorn_" + i + ".png"));
            }catch(IOException e){
                e.printStackTrace();
                throw new ExceptionClass("Read of ACORN images FAILED!");
            }
        }

        flagImg = fs.grabImage(1,1,32,32);
        potionImg = pots.grabImage(1,1,16,16);
        projImg = projs.grabImage(1,1,16,16);

        for(int i = 0 ; i < rubin.length; ++i){
            rubin[i] = rs.grabImage(i+1, 1, 16, 16);
        }

        for(int i = 0 ; i < health.length; ++i){
            health[i] = hs.grabImage(1, health.length - i, 85, 17);
        }

        //ENEMIES

        for(int i = 0 ; i < doggerRun.length; ++i){
            doggerRun[i] = dsr.grabImage(i+1,1,128,128);
        }

        for(int i = 0 ; i < gladiatorRun.length; ++i){
            gladiatorRun[i] = gsr.grabImage(i+1,2,32,32);
        }

        for(int i = 0; i < bittinRun.length; ++i){
            bittinRun[i] = bits.grabImage(i+1, 1, 50, 50);
        }

        //PLAY
        buttons[0] = btns.grabImage(1,1,420,56);
        //OPTIONS
        buttons[1] = btns.grabImage(1,2,420,56);
        //QUIT
        buttons[2] = btns.grabImage(1,3,420,56);
    }
}
