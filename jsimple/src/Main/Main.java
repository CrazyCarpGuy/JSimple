package Main;

import jsimple.core.*;
import jsimple.object.GameObject;
import jsimple.render.Camera;
import jsimple.scripts.*;
import jsimple.utility.Vector;

import java.awt.Color;

public class Main {

	/* This class contains the main method.
	 * Written by Ed Araujo on 2/7/22.
	 */

	public static void main(String[] args) throws InterruptedException {

		//Set up the JSimple framework
		JSimple.init();

		//***Engine Demo Code***\\

		GameObject obj1 = new GameObject("Duck");
		GameObject obj2 = new GameObject("Teapot");
		GameObject obj3 = new GameObject("Cat");
		Orbiter orb = new Orbiter(obj1.transform.getPosition());


		obj1.transform.setPosition(0, -2, 10);
		//obj1.transform.setRotation(0, 180, 0);
		obj1.model.setShape("models/Duck.obj");
		obj1.model.setTexture("textures/MallardColor.png");
		obj1.model.setScale(0.5f, 0.5f, 0.5f);
		obj1.transform.setAngularVelocity(0, 1f, 0f);

		obj2.transform.setPosition(5, -2, 10);
		obj2.model.setShape("models/Teapot.obj");
		obj2.model.setColor(Color.CYAN);
		obj2.model.setScale(0.5f, 0.5f, 0.5f);
		//obj2.transform.setAngularVelocity(1f, 0f, 0f);
		obj2.transform.setLinearVelocity(0, -2f, 0);
		obj2.setScript(orb);


		obj3.transform.setPosition(0, -2, 15);
		obj3.model.setShape("models/cat.obj");
		obj3.model.setTexture("textures/cat.jpg");
		obj3.model.setScale(0.05f, 0.05f, 0.05f);
		//obj3.transform.setAngularVelocity(0, 0f, 1f);
		obj3.transform.setLinearVelocity(2f, 0, 0);
		obj3.setScript(orb);

		Camera.setScript(new FreeCamera());
		//Camera.setScript(new FollowCamera(obj3, new Vector(0, 0, 10), true));
		//Start the engine
		JSimple.start();

	}

}