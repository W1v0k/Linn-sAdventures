package Window;

import Framework.*;
import Window.GameStates.GameState;
import Window.GameStates.*;
import Window.GameStates.Menu;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.sql.*;

public class Game extends Canvas implements Runnable{   //Runnable - interface
                                                        //has public void run() method that we @Override
                                                        //Composite Design Pattern

    private static Game gameInst = null;
    private boolean running = false;
    private Thread thread;

    public static int WIDTH, HEIGHT;

    public static BufferedImage level1 = null;
    public BufferedImage background = null;
    public BufferedImage menuBackground = null;
    public BufferedImage pauseBanner = null;
    public BufferedImage youWinBackground = null;
    public BufferedImage coin = null;
    public BufferedImage acorn = null;

    //Object
    Handler handler;
    static Camera cam;
    static Texture tex;

    //GAME STATES
    static Menu menu;
    static Win win;
    static Pause pause;
    static Options options;

    public static Sound jump;
    public static Sound grabbedCoin;
    public static Sound[] attack;
    public static Sound throwAcorn;
    public static Sound gatherAcorn;
    public static Sound drinkPotion;
    public static Sound levelComplete;
    public static Sound died;
    public static Sound hitPlayer;
    public static Sound themeSong, menuSong;

    public static int LEVEL = 1;
    //Player objects
    public static int coins;    //Coin implement
    public static int maxCoinsLvl1, maxCoinsLvl2, maxCoinsLvl3;
    public static int acorns;  //Projectiles(Acorns) implement
    public static int score;
    public static int health;

    //GAME STATES - for MENU
    public static GameState gameState = GameState.MENU;   // start game in menu
    public static Connection c;
    public static DataBase db;

    private Game(){
        coins = 0;
        maxCoinsLvl1 = 10;
        maxCoinsLvl2 = 12;
        maxCoinsLvl3 = 14;
        acorns = 3;
        score = 0;
        health = 100;
    }

    public static Game getGameInst(){
        if(gameInst == null)
            gameInst = new Game();
        return gameInst;
    }

    private void init() throws ExceptionClass {
        WIDTH = getWidth();
        HEIGHT = getHeight();

        tex = new Texture();

        BufferedImageLoader loader = new BufferedImageLoader();

        background = loader.loadImage("/bgs/bg8_v2.jpeg");   //loading background
        menuBackground = loader.loadImage("/bgs/menu.png");
        youWinBackground = loader.loadImage("/bgs/youWin.png");
        pauseBanner = loader.loadImage("/buttons/PAUSE_banner_teal_v2.png");
        level1 = loader.loadImage("/levels/level01.png");

        coin = loader.loadImage("/obj/coin_0.png");
        acorn = loader.loadImage("/obj/acorn_0.png");

        cam = new Camera(0,0);
        handler = new Handler(cam);
        handler.LoadImageLevel(level1);

        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(new MouseInput());
        this.addMouseMotionListener(new MouseInput());

        menu = new Menu();
        win = new Win();
        pause = new Pause();
        options = new Options();

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
        drinkPotion = new Sound("/sounds/potion/drinkPotion.wav");
        died = new Sound("/sounds/death/death_02.wav");
        hitPlayer = new Sound("/sounds/hitPlayer.wav");
        levelComplete = new Sound("/sounds/newLevel/newLevel_01.wav");
        if(!pause.musicButton.getMuted())
            menuSong.play(10);  //we start playing the menu song right away
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
        try {
            init();
        } catch (ExceptionClass e) {
            throw new RuntimeException(e);
        }
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
                try {
                    tick();
                } catch (SQLException e) {
                    try {
                        throw new ExceptionClass("SQLException caught!!!", e);
                    } catch (ExceptionClass ex) {
                        throw new RuntimeException(ex);
                    }
                }
                updates++;
                delta--;
            }
            try {
                render();
            } catch (ExceptionClass e) {
                throw new RuntimeException(e);
            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void tick() throws SQLException {    //updates

        if(gameState == GameState.GAME) {
            handler.tick();

            for (int i = 0; i < handler.object.size(); ++i) {
                if (handler.object.get(i).getId() == ObjectId.Player) {
                    cam.tick(handler.object.get(i));
                }
            }
        }else if(gameState == GameState.MENU){
            menu.tick();
        }else if(gameState == GameState.QUIT){
            System.exit(1);
        }else if(gameState == GameState.PAUSE){
            pause.tick();
        }else if(gameState == GameState.WIN){
            win.tick();
        }
    }

    private void render() throws ExceptionClass {  //graphics : images, background
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

            //COIN + ACORN DISPLAY + HEALTH

            Color transparentWhite = new Color(255, 255, 255, 127);    //alpha = 127, 50% transparency
            g.setColor(transparentWhite);
            g.fillRoundRect(2, 5, 175, 155, 10, 10);
            g.fillRoundRect(WIDTH - 235, 5, 225, 50, 10, 10);   //score

            g.drawImage(tex.health[(int)(health/10)], 5, 17, 170, 34, null);    //health

            g.drawImage(coin, 100, 57, 32, 32, null);    //coin

            g.drawImage(acorn, 100, 106, 32, 32, null);    //acorn

            g.setColor(Color.black);
            g.setFont(new Font("Comic Sans MS", Font.BOLD, 32));
            g.drawString(coins + " x ", 25, 82);   //coin
            g.drawString(acorns + " x ", 25, 132);   //acorn
            g.drawString("SCORE: " + score , WIDTH - 215 , 42);   //score
            //pauseOverlay.render(g);
            /////////////////////////////////////////////////////
        }else if(gameState == GameState.MENU){
            g.drawImage(menuBackground,0,0,this);
            menu.render(g);
        }else if(gameState == GameState.WIN){
            g.drawImage(menuBackground,0,0,this);
            g.drawImage(youWinBackground,575, 1 , this);  //315
            win.render(g);
        }else if(gameState == GameState.PAUSE){
            pause.render(g);
        }else if(gameState == GameState.OPTIONS){
            options.render(g);
        }

        g.dispose();
        bs.show();
    }

    public static Texture getInstance(){
        return tex;
    }
    public static Menu getMenu(){return menu;}
    public static Pause getPause(){return pause;}

    public static void main(String[] args) throws ExceptionClass {
        Game newGame = Game.getGameInst();  //singleton
        new Window(960, 608, "Linn's Adventures", newGame);
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:gameDatabase.db");
            DatabaseMetaData dbm = c.getMetaData();
            ResultSet table = dbm.getTables(null, null, "Scor", null);
            if(!table.next())
                try {
                    db = DataBase.getInstance(c);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new ExceptionClass("SQL getInstance(c) FAILED!", e);
                }

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            throw new ExceptionClass("SQL init failed!", e);
        }
    }
}


//Old render
//Color background = new Color(0, 206, 209);
//Color backgroundColor = new Color(36, 172, 212);
//g.setColor(backgroundColor);
//g.fillRect(0,0,getWidth(),getHeight());

//g.drawImage(background, - background.getWidth(), HEIGHT - background.getHeight(), this);

