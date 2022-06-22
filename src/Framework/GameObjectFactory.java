package Framework;

import Objects.*;
import Objects.Enemies.Bittin;
import Objects.Enemies.Dogger;
import Objects.Enemies.Gladiator;
import Window.Camera;
import Window.Handler;

public class GameObjectFactory {

    public GameObject createGameObject(float x, float y, Handler handler, Camera cam, ObjectId id, boolean hurtsPlayer, int type, int velX){
        GameObject newGameObject = null;

        switch(id){
            case Player -> {
                newGameObject = new Player(x, y, handler,cam, id);
            }
            case Block ->{
                newGameObject = new Block(x,y,type,id,hurtsPlayer);
            }
            case Coin->{
                newGameObject = new Coin(x,y,handler,id);
            }
            case Flag->{
                newGameObject = new Flag(x,y,id);
            }
            case Projectile -> {
                newGameObject = new Projectile(x,y,id,handler,velX);
            }
            case Dogger->{
                newGameObject = new Dogger(x,y,handler,id,hurtsPlayer);
            }
            case Acorn->{
                newGameObject = new Acorn(x,y,handler,id);
            }
            case Rubin->{
                newGameObject = new Rubin(x,y,handler,id);
            }
            case TransparentBlock -> {
                newGameObject = new TransparentBlock(x,y,id);
            }
            case Potion ->{
                newGameObject = new Potion(x,y,id);
            }
            case Gladiator -> {
                return new Gladiator(x,y,handler,id,hurtsPlayer);
            }
            case Bittin -> {
                return new Bittin(x,y,handler,id,hurtsPlayer);
            }
        }
        return newGameObject;
    }
}
