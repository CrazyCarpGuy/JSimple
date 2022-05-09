package jsimple.scripts;

import jsimple.object.GameObject;
import jsimple.object.Script;
import jsimple.utility.Vector;

public class Rotator implements Script {
    private GameObject target;

    public Rotator(GameObject o) {
        target = o;
    }

    @Override
    public void executeInit(GameObject o) {
    }

    @Override
    public void executeUpdate(GameObject o) {
        Vector direction = o.transform.getPosition().subtract(target.transform.getPosition()).invert();
        double x = (Math.atan(direction.z / direction.y) - Math.PI), y = 0, z = (Math.atan(direction.x / direction.y))-(Math.atan(direction.z / direction.x));
        Vector angle  = new Vector(x, y, z);
        if(direction.z > 0 && direction.x == 0) {
            angle.z -= (float)Math.PI / 2;
        }
        else if(direction.z < 0 && direction.x == 0) {
            angle.z += (float)Math.PI / 2;
        }
        if (direction.z > 0) {
            angle.x  += Math.PI;
        }
        if(direction.y > 0) {
            angle.z = (float)(Math.PI + angle.z);
        }
        if(direction.z > 0) {
            angle.z = (float)Math.PI - angle.z;
        }
        o.transform.setRotation(angle);
    }

}
