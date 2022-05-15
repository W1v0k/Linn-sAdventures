package Window;

import Framework.*;
import Window.GameStates.GameState;
import Window.GameStates.Menu;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
//import java.util.Random;

public class Game extends Canvas implements Runnable{   //Runnable - interface
                                                        //has public void run() method that we @Override
                                                        //Composite Design Pattern
    private boolean running = false;
    private Thread thread;

    public static int WIDTH, HEIGHT;

    public static BufferedImage level1 = null;
    public BufferedImage background = null;
    public BufferedImage menuBackground = null;
    public BufferedImage coin = null;
    public BufferedImage acorn = null;

    //Object
    Handler handler;
    Camera cam;
    static Texture tex;
    static Menu menu;

    public static Sound jump;
    public static Sound grabbedCoin;
    public static Sound[] attack;
    public static Sound throwAcorn;
    public static Sound gatherAcorn;
    public static Sound levelComplete;
    public static Sound died;
    public static Sound themeSong, menuSong;

    public static int LEVEL = 1;
    public static int coins = 0;    //Coin implement
    public static int acorns = 3;  //Projectiles(Acorns) implement
    //Random rand = new Random();

    //GAME STATES - for MENU
    public static GameState gameState = GameState.MENU;   // start game in menu
    
    private void init(){
        WIDTH = getWidth();
        HEIGHT = getHeight();

        tex = new Texture();

        menu = new Menu();

        BufferedImageLoader loader = new BufferedImageLoader();

        background = loader.loadImage("/bgs/bg8_v2.jpeg");   //loading background
        menuBackground = loader.loadImage("/bgs/menu.png");
        level1 = loader.loadImage("/levels/level01_v2.png");

        coin = loader.loadImage("/obj/coin_0.png");
        acorn = loader.loadImage("/obj/acorn_0.png");

        cam = new Camera(0,0);
        handler = new Handler(cam);
        handler.LoadImageLevel(level1);

        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(new MouseInput());

        //SOUNDS
        jump = new Sound("/sounds/jump/3/jump_01.wav");
        grabbedCoin = new Sound("/sounds/coin/1/coin_03.wav");
        themeSong = new Sound("/sounds/background/bgsound_01.wav");
        menuSong = new Sound("/sounds/background/menu_01.wav");
        attack = new Sound[3];
        attack[0] = new Sound("/sounds/attack/attack_01.wav");
        attack[1] = new Sound("/sounds/attack/attack_02.wav");
        attack[2] = new Sound("/sounds/attack/attack_03.wav");
        throwAcorn = new Sound("/sounds/acornThrow/throw_02.wav");
        gatherAcorn = new Sound("/sounds/acornRefill/gather_02.wav");
        died = new Sound("/sounds/death/death_02.wav");
        levelComplete = new Sound("/sounds/newLevel/newLevel_01.wav");
        menuSong.play(10);  //we start playing the menu song right away
        //handler.addObject(new Player(100,100, handler, ObjectId.Player));
        //handler.createLevel();

    }
    public synchronized void start(){
        if(running) //if it's already running the thread, don't start again
            return;//fail-safe method

        running = true;
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        //System.out.println("Thread has begun");
        init();
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;    //60 ticks per second
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void tick(){    //updates

        if(gameState == GameState.GAME) {

            handler.tick();

            for (int i = 0; i < handler.object.size(); ++i) {
                if (handler.object.get(i).getId() == ObjectId.Player) {
                    cam.tick(handler.object.get(i));
                }
            }
        }
    }

    private void render(){  //graphics : images, background
        //BufferStrategy - the image is already preloaded in the back
        //and when it's the time, just print it in the front
        //3 numBuffers because the greatest computers will run 3 buffers
        //because they are really fast
        //if we put 300 it would be ineficient because no pc is fast enough
        BufferStrategy bs = this.getBufferStrategy();   //Canvas Method
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();  //Get a graphic context for our buffering
        Graphics2D g2D = (Graphics2D) g;
        /////////////////===DRAW EVERYTHING===////////////////

        if(gameState == GameState.GAME) {
            //what we have between the 2 translate methods will be affected by the camera
            g2D.translate(cam.getX(), cam.getY());  //camera movement, begin of cam
            for (int xx = -background.getWidth(); xx < background.getWidth() * 5; xx += background.getWidth()) {
                g.drawImage(background, xx, 0, this);
            }
            handler.render(g);

            g2D.translate(-cam.getX(), -cam.getY());  //camera movement, end of cam

            //COIN + ACORN DISPLAY

            Color transparentWhite = new Color(255, 255, 255, 127);    //alpha = 127, 50% transparency
            g.setColor(transparentWhite);
            g.fillRoundRect(5, 5, 145, 105, 10, 10);
            g.drawImage(coin, 100, 17, 32, 32, null);    //coin
            g.drawImage(acorn, 100, 66, 32, 32, null);    //acorn
            g.setColor(Color.black);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
            g.drawString(coins + " x ", 25, 42);   //coin
            g.drawString(acorns + " x ", 25, 92);   //acorn
            /////////////////////////////////////////////////////
        }else if(gameState == GameState.MENU){
            //g.setColor(Color.black);
            //g.fillRect(0,0,getWidth(),getHeight());
            g.drawImage(menuBackground,0,0,this);
            menu.render(g);
        }
        g.dispose();
        bs.show();
    }

    public static Texture getInstance(){
        return tex;
    }
    public static Menu getMenu(){return menu;}
    public static void main(String[] args){
        new Window(960, 608, "Linn's Adventures", new Game());
    }
}


//Old render
//Color background = new Color(0, 206, 209);
//Color backgroundColor = new Color(36, 172, 212);
//g.setColor(backgroundColor);
//g.fillRect(0,0,getWidth(),getHeight());

//g.drawImage(background, - background.getWidth(), HEIGHT - background.getHeight(), this);

