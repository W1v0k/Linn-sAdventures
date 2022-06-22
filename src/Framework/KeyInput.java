package Framework;

import Objects.Projectile;
import Window.Camera;
import Window.GameStates.GameState;
import Window.Handler;
import Window.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;

public class KeyInput extends KeyAdapter {
    Handler handler;
    public KeyInput(Handler handler){
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(Game.gameState == GameState.GAME){
            for(int i = 0 ; i < handler.object.size();++i){
                GameObject tempObject = handler.object.get(i);

                if(tempObject.getId() == ObjectId.Player){  //we need this because in handler.object we also store blocks, etc
                    if(key == KeyEvent.VK_D){ tempObject.setVelX(5);}
                    if(key == KeyEvent.VK_A){ tempObject.setVelX(-5);}
                    if(key == KeyEvent.VK_W && !tempObject.isJumping()){
                        tempObject.setJumping(true);
                        if(!Game.getPause().sfxButton.getMuted())
                            Game.jump.play();
                        tempObject.setVelY(-10);
                    }
                    if(key == KeyEvent.VK_E){
                        if(Game.acorns > 0){
                            if(!Game.getPause().sfxButton.getMuted())
                                Game.throwAcorn.play();
                            if(tempObject.getFacingLeft()) {
                                handler.addObject(new Projectile(tempObject.getX(), tempObject.getY() + 8, ObjectId.Projectile, handler, ((-1) * 10)));
                                Game.acorns--;
                            }else{
                                handler.addObject(new Projectile(tempObject.getX(), tempObject.getY() + 8, ObjectId.Projectile, handler, ((+1) * 10)));
                                Game.acorns--;
                            }
                        }
                    }
                    if(key == KeyEvent.VK_SPACE){   //sword attack
                        tempObject.setAttack(true);
                    }
                }
            }
            if(key == KeyEvent.VK_P){   //Pause the game
                Game.gameState = GameState.PAUSE;
                Game.themeSong.stop();
                if(!Game.getPause().musicButton.getMuted())
                    Game.menuSong.play(10);
            }
            if(key == KeyEvent.VK_R){   //Restart the level
                Game.died.play();
                handler.resetLevel();
            }
        }

        if(key == KeyEvent.VK_BACK_SPACE){
            if(Game.gameState == GameState.GAME ){
                Game.gameState = GameState.MENU;
                Game.themeSong.stop();
                if(!Game.getPause().musicButton.getMuted())
                    Game.menuSong.play(10);
                handler.restartGame();
            }else if(Game.gameState == GameState.PAUSE) {
                Game.gameState = GameState.GAME;
                Game.menuSong.stop();
                if (!Game.getPause().musicButton.getMuted())
                    Game.themeSong.play(10);
            }else if(Game.gameState == GameState.OPTIONS) {
                Game.gameState = GameState.MENU;
                if(Game.getPause().musicButton.getMuted())
                   Game.menuSong.stop();
            }
        }

        if(key == KeyEvent.VK_ESCAPE){  //KeyEvent -> Enum
            System.exit(1);
        }
    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        for(int i = 0 ; i < handler.object.size();++i){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ObjectId.Player){
                if(key == KeyEvent.VK_D){ tempObject.setVelX(0);}
                if(key == KeyEvent.VK_A){ tempObject.setVelX(0);}
                if(key == KeyEvent.VK_SPACE){tempObject.setAttack(false);}
            }
        }
    }
}
