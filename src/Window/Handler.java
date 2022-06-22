package Window;

import Framework.ExceptionClass;
import Framework.GameObject;
import Framework.GameObjectFactory;
import Framework.ObjectId;
import Objects.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.LinkedList;

public class Handler {

    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    private GameObject tempObject;
    private Camera cam;
    private BufferedImage level2, level3;
    private GameObjectFactory gameObjFactory = new GameObjectFactory();

    public Handler(Camera cam) throws ExceptionClass {
        this.cam = cam;
        BufferedImageLoader loader = new BufferedImageLoader();
        level2 = loader.loadImage("/levels/level02.png");
        level3 = loader.loadImage("/levels/level03.png");
    }

    public void tick() throws SQLException {
        for(int i =0 ; i < object.size(); ++i){ //we update all the gameObjects from our list
            tempObject = object.get(i);
            tempObject.tick(object);
        }
    }

    public void render(Graphics g){ //NEVER CHANGE THIS IN A ECHANCED FOR!
        for (int i =0 ; i < object.size(); ++i) {
            tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void LoadImageLevel(BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();
        //System.out.println("width, height: "+w+" "+h);

        for(int xx = 0; xx < h; ++xx){
            for(int yy = 0; yy < w; ++yy){
                //loop through every single pixel
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if(red == 255 && green == 255 && blue == 255){   //white pixel color => grass
                    //addObject(new Block(xx*32, yy * 32, 0 ,ObjectId.Block, false));
                    addObject(gameObjFactory.createGameObject(xx*32,yy*32,null,null,ObjectId.Block,false,0,0));
                }

                if(red == 128 && green == 128 && blue == 128){  //dirt
                    //addObject(new Block(xx*32, yy * 32, 1 ,ObjectId.Block, false));
                    addObject(gameObjFactory.createGameObject(xx*32,yy*32,null,null,ObjectId.Block,false,1,0));
                }

                if(red == 178 && green == 220 && blue == 239){   //water
                    //addObject(new Block(xx*32, yy * 32, 2 ,ObjectId.Block, true));
                    addObject(gameObjFactory.createGameObject(xx*32,yy*32,null,null,ObjectId.Block,true,2,0));
                }
                if(red == 0 && green == 0 && blue == 255){  //dark blue pixel => is a player
                    //addObject(new Player(xx*32, yy*32, this, cam, ObjectId.Player));
                    addObject(gameObjFactory.createGameObject(xx*32,yy*32,this,cam,ObjectId.Player,false,0,0));
                }
                if(red == 255 && green == 201 && blue == 14){  //yellow pixel => coin
                    //addObject(new Coin(xx*32, yy*32, this, ObjectId.Coin));
                    addObject(gameObjFactory.createGameObject(xx*32,yy*32,this,null,ObjectId.Coin,false,0,0));
                }
                if(red == 249 && green == 143 && blue == 12){   //tan pixel => acorn
                    //addObject(new Acorn(xx*32, yy*32,this, ObjectId.Acorn));
                    addObject(gameObjFactory.createGameObject(xx*32,yy*32,this,null,ObjectId.Acorn,false,0,0));
                }
                if(red == 163 && green == 206 && blue == 39){  //lime pixel => change level
                    //addObject(new Flag(xx*32, yy*32, ObjectId.Flag));
                    addObject(gameObjFactory.createGameObject(xx*32,yy*32,null,null,ObjectId.Flag,false,0,0));
                }
                if(red == 255 && green == 0 && blue == 0){  //full red pixel => rubin
                    addObject(gameObjFactory.createGameObject(xx*32, yy*32, this, null, ObjectId.Rubin, false, 0 , 0));
                }
                if(red == 228 && green == 185 && blue == 185){  //light pink pixel => transparent block
                    addObject(gameObjFactory.createGameObject(xx*32,yy*32,null,null,ObjectId.TransparentBlock, false,0,0));
                }
                if(red == 240 && green == 105 && blue == 105){  //salmon color => health potion
                    addObject(gameObjFactory.createGameObject(xx*32,yy*32,null,null,ObjectId.Potion, false,0,0));
                }

                //ENEMIES
                if(red == 76 && green == 48 && blue == 10){  //dark brown pixel => dogger
                    //addObject(new Dogger(xx*32, yy*32, this, ObjectId.Dogger, true));
                    addObject(gameObjFactory.createGameObject(xx*32,yy*32,this,null,ObjectId.Dogger,true,0,0));
                }
                if(red == 90 && green == 1 && blue == 240){ //purple pixel => gladiator
                    addObject(gameObjFactory.createGameObject(xx*32,yy*32,this,null,ObjectId.Gladiator,true,0,0));
                }
                if(red == 202 && green == 244 && blue == 194){    //palid green => bittin
                    addObject(gameObjFactory.createGameObject(xx*32, yy*32, this, null, ObjectId.Bittin, true, 0, 0));
                }
            }
        }
    }

    public void switchLevel(){
        clearLevel();
        cam.setX(0);
        switch (Game.LEVEL){
            case 1:
                LoadImageLevel(level2);
                break;
            case 2:
                LoadImageLevel(level3);
                break;
        }
        Game.LEVEL++;
    }

    public void resetLevel(){
        clearLevel();
        cam.setX(0);
        Game.score = 0;
        switch (Game.LEVEL) {
            case 1 -> LoadImageLevel(Game.level1);
            case 2 -> LoadImageLevel(level2);
            case 3 -> LoadImageLevel(level3);
        }
    }

    public void restartGame(){
        clearLevel();
        cam.setX(0);
        Game.score = 0;
        Game.health = 100;
        Game.acorns = 3;
        Game.coins = 0;
        Game.LEVEL = 1;
        LoadImageLevel(Game.level1);
    }

    private void clearLevel(){
        object.clear(); //remove all the object in the game
    }

    public void addObject(GameObject object){
        this.object.add(object);    //this.object is the LinkedList
    }

    public void removeObject(GameObject object){
        this.object.remove(object);
    }

//    public void createLevel(){
//        for(int yy = 0; yy < Game.HEIGHT+32; yy += 32){
//            addObject(new Block(0,yy,ObjectId.Block));
//        }
//        for(int xx = 0 ; xx<Game.WIDTH*2;xx+=32){
//            addObject(new Block(xx, Game.HEIGHT-32, ObjectId.Block));
//        }
//        for(int xx = 200; xx< 600;xx+=32){
//            addObject(new Block(xx,400,ObjectId.Block));
//        }
//        //handler.addObject(new Test(rand.nextInt(800),rand.nextInt(600), ObjectId.Test));
//
//    }
}

//    public BufferedImage getLevel(int numLevel){
//        switch (numLevel){
//            case 1:
//                return level1;
//            case 2:
//                return level2;
//            default:
//                return null;
//        }
//    }

