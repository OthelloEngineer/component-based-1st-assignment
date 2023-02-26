package dk.sdu.mmmi.cbse.playersystem;


import com.badlogic.gdx.graphics.Color;
import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author corfixen
 */
public class Asteroids extends Entity implements Cloneable {

    public Asteroids() {
        super.setColor(Color.YELLOW);
        super.setRadius(100);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
