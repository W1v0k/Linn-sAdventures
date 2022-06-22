package Objects;

import Framework.*;
import Window.*;

import java.awt.*;
import java.util.LinkedList;

public class Projectile extends GameObject {
    private Handler handler;
    Texture tex = Game.getInstance();

    public Projectile(float x, float y, ObjectId id, Handler handler, int velX) {
        super(x, y, id);
        this.handler = handler;
        this.velX = velX;
    }

    public void tick(LinkedList<GameObject> object) {
        x += velX;
        Collision(object);
    }

    private void Collision(LinkedList<GameObject> object){
        for(int i = 0 ; i < handler.object.size(); ++i){
            GameObject tempObject = handler.object.get(i);

            if(tempObject.getId() == ObjectId.Block){
                if(getBounds().intersects(tempObject.getBounds())){
                    removeProjectile();
                }
            }
        }
    }
    public void render(Graphics g) {
//        g.setColor(Color.red);
//        g.fillRect((int)x,(int)y,16,16);
        if(facingLeft)
            g.drawImage(tex.projImg, (int)x - tex.projImg.getWidth(),(int)y - tex.projImg.getHeight(), tex.projImg.getWidth() * 2, tex.projImg.getHeight() * 2, null);
        else
            g.drawImage(tex.projImg, (int)x + tex.projImg.getWidth(),(int)y - tex.projImg.getHeight(), tex.projImg.getWidth() * 2, tex.projImg.getHeight() * 2, null);

    }

    public void removeProjectile(){
        handler.object.remove(this);
    }

    public Rectangle getBounds() {
        if(facingLeft)
            return new Rectangle((int)x - tex.projImg.getWidth(), (int)y - tex.projImg.getHeight(),16, 16);
        else
            return new Rectangle((int)x + tex.projImg.getWidth(), (int)y - tex.projImg.getHeight(),16, 16);
    }
}
