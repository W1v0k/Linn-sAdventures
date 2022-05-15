package Framework;

import Objects.Projectile;
import Window.GameStates.GameState;
import Window.Handler;
import Window.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
                    Game.jump.play();
                    tempObject.setVelY(-10);
                }
                if(key == KeyEvent.VK_E){
                    if(Game.acorns > 0){
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
        if(key == KeyEvent.VK_BACK_SPACE){  //Back in the MENU
            Game.gameState = GameState.MENU;
            Game.themeSong.stop();
            Game.menuSong.play(10);
        }
        if(key == KeyEvent.VK_P){   //Pause the game
            Game.gameState = GameState.MENU;
            Game.themeSong.stop();
            Game.menuSong.play(10);
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
