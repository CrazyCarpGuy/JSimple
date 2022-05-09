package Main;

import jsimple.core.JSimple;
import jsimple.render.Camera;
import jsimple.utility.Vector;
import jsimple.object.GameObject;
import jsimple.scripts.*;

import java.awt.*;

public class Atom {
    public static void main(String[] args) {

        //Set up the JSimple framework
        JSimple.init();
        Camera.setScript(new FreeCamera());
        Camera.setPosition(new Vector(0, 0, -5));

        //***Engine Demo Code***\\

        GameObject obj_ = new GameObject("Benchmark Cube");
        GameObject obj0 = new GameObject("Stationary Cube"); //Create the reference object
        GameObject obj1 = new GameObject("Orbiting Cube 1"); //Create the first object
        GameObject obj2 = new GameObject("Orbiting Cube 2"); //Create the first object
        GameObject obj3 = new GameObject("Orbiting Cube 3"); //Create the first object
        GameObject obj4 = new GameObject("Orbiting Cube 4"); //Create the first object

        obj0.model.setShape("models/sphere.obj"); //Set the object's shape to be a box
        obj0.model.setColor(Color.white); //Set the object's color to blue
        obj0.transform.setPosition(new Vector(0, 0, 5)); //Move the object to the coordinates (0, 0, 5)
        obj0.transform.setLinearVelocity(new Vector(1f, 0, 0));
        obj0.setScript(new Oscillator(new Vector(0, 0, 5), 5f));
        Orbiter orb = new Orbiter(obj0);

        obj_.model.setShape("models/sphere.obj"); //Set the object's shape to be a box
        obj_.model.setColor(Color.red); //Set the object's color to blue
        obj_.transform.setPosition(new Vector(0, 0, 5)); //Move the object to the coordinates (5, 0, 5)

        obj1.model.setShape("models/sphere.obj"); //Set the object's shape to be a box
        obj1.model.setColor(Color.blue); //Set the object's color to blue
        obj1.transform.setPosition(new Vector(0, 0, 0)); //Move the object to the coordinates (5, 0, 5)
        obj1.transform.setLinearVelocity(new Vector((float)Math.sqrt(2),(float)Math.sqrt(2),0));
        obj1.setScript(orb);

        obj2.model.setShape("models/sphere.obj"); //Set the object's shape to be a box
        obj2.model.setColor(Color.blue); //Set the object's color to blue
        obj2.transform.setPosition(new Vector(0, 0, 10)); //Move the object to the coordinates (5, 0, 5)
        obj2.transform.setLinearVelocity(new Vector(-(float)Math.sqrt(2),(float)Math.sqrt(2),0));
        obj2.setScript(orb);

        obj3.model.setShape("models/sphere.obj"); //Set the object's shape to be a box
        obj3.model.setColor(Color.blue); //Set the object's color to blue
        obj3.transform.setPosition(new Vector(0, 5, 5)); //Move the object to the coordinates (5, 0, 5)
        obj3.transform.setLinearVelocity(new Vector(2f,0,0));
        obj3.setScript(orb);

        obj4.model.setShape("models/sphere.obj"); //Set the object's shape to be a box
        obj4.model.setColor(Color.blue); //Set the object's color to blue
        obj4.transform.setPosition(new Vector(5, 0, 5)); //Move the object to the coordinates (5, 0, 5)
        obj4.transform.setLinearVelocity(new Vector(0,0,2f));
        obj4.setScript(orb);


        //Start the engine
        JSimple.start();

    }
}
