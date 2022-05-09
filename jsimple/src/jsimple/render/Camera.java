package jsimple.render;

import jsimple.core.JSimple;
import jsimple.object.Script;
import jsimple.utility.Matrix;
import jsimple.utility.Vector;

public class Camera {
	
	public static Vector position, rotation, forward, up, right, linearSpeed, angularSpeed;
	public static Matrix worldMatrix;
	private static Script script;

	public static void init() {
		
		Camera.reset();
		
	}

	public static void setScript(Script s) {
		script = s;
	}

	public static void update() {
		
		//Give the illusion of moving the camera by shifting all objects in world space by the worldMatrix (applied per triangle).
		worldMatrix = Matrix.getRotation(rotation.x, rotation.y, rotation.z).multiply(Matrix.getTranslation(-position.x, -position.y, -position.z));
				
		//Rotate the camera by its angular speed
		rotation = rotation.add(angularSpeed.multiply(JSimple.deltaTime));
		
		//Determine relative axis based on rotation
		Camera.getAxis();
		
		//Move the camera relative to its rotation by its linear speed
		position = position.add(right.multiply(linearSpeed.x * JSimple.deltaTime));
		position = position.add(up.multiply(linearSpeed.y * JSimple.deltaTime));
		position = position.add(forward.multiply(linearSpeed.z * JSimple.deltaTime));

		if(script != null) {
			script.executeUpdate(null);
		}
	}
	
	//Determine the forward, right, and up vectors relative to the camera's current rotation.
	private static void getAxis() {
		
		//Calculate trigonometric values once to save processing time
		float sinX = (float)Math.sin(rotation.x), cosX = (float)Math.cos(rotation.x),
			  sinY = (float)Math.sin(rotation.y), cosY = (float)Math.cos(rotation.y);
		
		right = new Vector(cosY, 0, sinY); //Vector points right on the screen
		forward = new Vector(-sinY * cosX, sinX, cosY * cosX); //Vector points into the screen
		up = forward.cross(right); //Vector points up on the screen
		
	}

	//Resets the camera position, rotation axis, and velocities
	public static void reset() {
		
		position = new Vector(0, 0, 0);
		rotation = new Vector(0, 0, 0);
		linearSpeed = new Vector(0, 0, 0);
		angularSpeed = new Vector(0, 0, 0);
		
	}

	public static void setPosition(Vector v) {
		position = v;
	}
} 

