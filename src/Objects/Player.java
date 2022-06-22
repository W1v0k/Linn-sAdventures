package Objects;

import Framework.*;
import Window.*;
import Window.GameStates.GameState;

import java.awt.*;
import java.sql.SQLException;
import java.util.LinkedList;

public class Player extends GameObject {

    private static Player instancePlayer;

    private float width = 32, height = 32;
    private float gravity = 0.5f;
    private final float MAX_SPEED = 10;

    private Handler handler;
    private Camera cam;

    Texture tex = Game.getInstance();

    private Animation playerRun, playerAttack, playerIdle;

    public Player(float x, float y, Handler handler, Camera cam, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
        this.cam = cam;
        playerRun = new Animation(5, tex.playerRun[0], tex.playerRun[1], tex.playerRun[2], tex.playerRun[3], tex.playerRun[4], tex.playerRun[5]);
        playerAttack = new Animation(3, tex.playerAtk[0], tex.playerAtk[1],tex.playerAtk[2],tex.playerAtk[3],tex.playerAtk[4]);
        playerIdle = new Animation(11, tex.playerIdle[0], tex.playerIdle[1],tex.playerIdle[2],tex.playerIdle[3],tex.playerIdle[4],tex.playerIdle[5],tex.playerIdle[6],tex.playerIdle[7],tex.playerIdle[8]);
    }

    @Override
    public void tick(LinkedList<GameObject> object) throws SQLException {
        if(Game.health <= 0){
            deadPlayer();
        }

        playerIdle.runAnimation();
        x+= velX;
        y += velY;

        if(velX < 0){
            facingLeft = true;
        }else if(velX > 0){
            facingLeft = false;
        }

        if(falling || jumping){
            velY += gravity;

            if(velY > MAX_SPEED){   //velocity can increase quite rapidly
                                    //because of that, we will diminish the speed at which the player will fall
                velY = MAX_SPEED;
            }
            Collision(object);
            playerRun.runAnimation();
        }

        if(attacking && !jumping)
        {
            playerAttack.runAnimation(true);
        }
    }

    private void Collision(LinkedList<GameObject> object) throws SQLException {
        for(int i = 0 ; i < handler.object.size(); ++i){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ObjectId.Block){

                if(getBoundsTop().intersects(tempObject.getBounds())){ //Collision at the head
                    y = tempObject.getY() + 34;
                    velY = 0;
                }

                if(getBounds().intersects(tempObject.getBounds()) && !tempObject.isHurtingPlayer()){ //Collision at the feet
                    y = tempObject.getY() - height; //because of the speed that the player is falling
                                                    //we might catch the intersection later than we should
                                                    //and if that happens, a part of the player will be in the
                                                    //ground. For the consistency of the game, we will snap the
                                                    //player on top of the block
                    velY = 0;
                    falling = false;
                    jumping = false;
                }else if(getBounds().intersects(tempObject.getBounds()) && tempObject.isHurtingPlayer()){
                    deadPlayer();
                }else{
                    falling = true;
                }

                if(getBoundsRight().intersects(tempObject.getBounds())){ //Collision right
                    x = tempObject.getX() - width;
                }

                if(getBoundsLeft().intersects(tempObject.getBounds())){ //Collision left
                    x = tempObject.getX() + width;
                }
            } else if(tempObject.getId() == ObjectId.Flag){
                //switch level
                boolean passTheLevel = false;
                if(getBounds().intersects(tempObject.getBounds())) {
                    if(Game.LEVEL == 1){
                        if(Game.coins == Game.maxCoinsLvl1)   //he needs all the coins
                            passTheLevel = true;
                    }
                    if(Game.LEVEL == 2){
                        if(Game.coins == Game.maxCoinsLvl2)
                            passTheLevel = true;
                    }

                    if(passTheLevel){
                        if(!Game.getPause().sfxButton.getMuted())
                            Game.levelComplete.play("level");
                        handler.switchLevel();
                        Game.coins = 0;
                        cam.setX(0);
                    }
                }
            }else if(tempObject.getId() == ObjectId.Coin){
                if(getBounds().intersects(tempObject.getBounds())){
                    if(!Game.getPause().sfxButton.getMuted())
                        Game.grabbedCoin.play("coin");
                    Game.coins++;
                    handler.removeObject(tempObject);

                    switch (Game.LEVEL){
                        case 1->{
                            //Game.db.addRecord(Game.coins, Game.LEVEL, Game.maxCoinsLvl1, Game.score);
                            Game.db.insertRecord(Game.c, Game.coins, Game.LEVEL, Game.maxCoinsLvl1, Game.score);
                        }
                        case 2->{
                            Game.db.insertRecord(Game.c, Game.coins, Game.LEVEL, Game.maxCoinsLvl2, Game.score);
                        }
                        case 3->{
                            Game.db.insertRecord(Game.c, Game.coins, Game.LEVEL, Game.maxCoinsLvl3, Game.score);
                        }
                    }

                }
            }else if(tempObject.getId() == ObjectId.Acorn){
                if(getBounds().intersects(tempObject.getBounds())){
                    if(Game.acorns < 3){
                        if(!Game.getPause().sfxButton.getMuted())
                            Game.gatherAcorn.play();
                        Game.acorns++;
                        handler.removeObject(tempObject);
                    }
                }
            }else if(tempObject.getId() == ObjectId.Potion){
                if(getBounds().intersects(tempObject.getBounds())) {
                    if (Game.health < 100) {
                        if(!Game.getPause().sfxButton.getMuted())
                            Game.drinkPotion.play();
                        handler.removeObject(tempObject);
                        if(Game.health <= 80)
                            Game.health += 20;
                        else if(Game.health == 90)
                            Game.health += 10;
                    }
                }
            }else if(tempObject.getId() == ObjectId.Rubin){
                if(getBounds().intersects(tempObject.getBounds())){
                    boolean passTheLevel = false;
                    if(Game.coins == Game.maxCoinsLvl3)
                        passTheLevel = true;

                    if(passTheLevel){
                        if(!Game.getPause().sfxButton.getMuted())
                            Game.levelComplete.play("level");
                        Game.gameState = GameState.WIN;
                        handler.removeObject(tempObject);
                    }
                }
                //ENEMIES
            }else if(tempObject.getId() == ObjectId.Dogger || tempObject.getId() == ObjectId.Gladiator || tempObject.getId() == ObjectId.Bittin){
                Rectangle toCheckPlayer;
                int getBack = 0;
                if(facingLeft){
                     toCheckPlayer = getBoundsLeft();
                     getBack = 30;
                }else{
                    toCheckPlayer = getBoundsRight();
                    getBack = -30;
                }
                if(toCheckPlayer.intersects(tempObject.getBounds()) && !attacking) {
                    if (Game.health > 0) {
                        Game.health -= 10;
                        x += getBack;
                        if(!Game.getPause().sfxButton.getMuted())
                            Game.hitPlayer.play();
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics g){
        //g.setColor(Color.white);
//        g.setColor(Color.red);
//        Graphics2D graphics2D = (Graphics2D)g;
//        graphics2D.draw(getBoundsRight());
//        g.setColor(Color.green);
//        graphics2D.draw(getBoundsLeft());
//        g.setColor(Color.cyan);
//        graphics2D.draw(getBoundsTop());
//        g.setColor(Color.magenta);
//        graphics2D.draw(getBounds());

        if(jumping){
            if(facingLeft){
                g.drawImage(tex.playerJump, (int)x - 20 + tex.playerJump.getWidth() + tex.playerJump.getWidth(), (int)y - 22, -64, 64, null);
            }else{
                g.drawImage(tex.playerJump, (int)x - 20, (int)y - 22, 64, 64, null);
            }
        }else{
            if(velX != 0 && !attacking){
                if(facingLeft){
                    playerRun.drawAnimation(g, (int) x - 20, (int) y - 22, 64, 64, true);
                }else {
                    playerRun.drawAnimation(g, (int) x - 16, (int) y - 22, 64, 64);
                }
            }else if(velX == 0 && !attacking){
                if(facingLeft) {
                    playerIdle.drawAnimation(g, (int)x-20, (int)y - 22, 64,64,true);
                    //g.drawImage(tex.player[0], (int) x - 20 + tex.player[0].getWidth() + tex.player[0].getWidth(), (int) y - 22, -64, 64, null);
                }else {
                    //g.drawImage(tex.player[0], (int) x - 20, (int) y - 22, 64, 64, null);
                    playerIdle.drawAnimation(g, (int)x-20, (int)y - 22, 64,64);
                }
            }else if(attacking){
                if(facingLeft){
                    playerAttack.drawAnimation(g, (int) x - 20, (int) y - 22, 64, 64, true);
                }else{
                    playerAttack.drawAnimation(g, (int) x - 20, (int) y - 22, 64, 64);
                }
            }
        }
    }

    @Override//we create 4 bounding rectangles, one for the bottom collision,one for top, left and right
    public Rectangle getBounds() {
        return new Rectangle((int) (x+(width/2)-(width/4)), (int) ((int)y+(height/2)), (int)width/2, (int)height/2);
    }
    public Rectangle getBoundsTop() {
        return new Rectangle((int) (x+(width/2)-(width/4)),(int)y, (int)width/2, (int)height/2);
    }
    public Rectangle getBoundsRight() {
        if(!attacking)
            return new Rectangle((int) (x+width-8),(int)y+5, 5, (int)height-10);
        else
            return new Rectangle((int)(x + width + 8),(int)y+5, 5, (int)height-10);
    }
    public Rectangle getBoundsLeft() {
        if(!attacking)
            return new Rectangle((int)x,(int)y+5, 5, (int)height-10);
        else
            return new Rectangle((int)x - 16,(int)y+5, 5, (int)height-10);
    }

    public void deadPlayer(){
        Game.died.play();
        Game.health = 100;
        handler.resetLevel();
        Game.coins = 0;
        Game.acorns = 3;
        cam.setX(0);
    }
}
