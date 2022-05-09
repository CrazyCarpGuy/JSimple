package jsimple.scripts;

import jsimple.core.Input;
import jsimple.core.JSimple;
import jsimple.object.GameObject;
import jsimple.object.Script;
import jsimple.render.Camera;

//Version of current Main update method for new implementation
public class FreeCamera implements Script {

	@Override
	public void executeInit(GameObject o) {}
	
    @Override
    public void executeUpdate(GameObject o) {
    	
        //Use user input to assign camera speed
        if(Input.wPressed) {
            Camera.linearSpeed.z = 0.01f;
        } else if (Input.sPressed) {
            Camera.linearSpeed.z = -0.01f;
        } else {
            Camera.linearSpeed.z = 0;
        }

        if(Input.aPressed) {
            Camera.linearSpeed.x = -0.01f;
        } else if (Input.dPressed) {
            Camera.linearSpeed.x = 0.01f;
        }
        else {
            Camera.linearSpeed.x = 0;
        }

        if(Input.spacePressed) {
            Camera.linearSpeed.y = 0.01f;
        } else if (Input.shiftPressed) {
            Camera.linearSpeed.y = -0.01f;
        }
        else {
            Camera.linearSpeed.y = 0;
        }
        
        if(Input.upPressed) {
        	Camera.angularSpeed.x = 0.002f;
        } else if (Input.downPressed) {
        	Camera.angularSpeed.x = -0.002f;
        } else {
        	Camera.angularSpeed.x = 0;
        }
        
        if(Input.rightPressed) {
        	Camera.angularSpeed.y = -0.002f;
        } else if (Input.leftPressed) {
        	Camera.angularSpeed.y = 0.002f;
        } else {
        	Camera.angularSpeed.y = 0;
        }

        if(Input.qPressed) {
            Camera.angularSpeed.z = -0.002f;
        } else if (Input.ePressed) {
            Camera.angularSpeed.z = 0.002f;
        } else {
            Camera.angularSpeed.z = 0;
        }

        if(Input.escPressed) {
            Camera.reset();
        }
        if (Input.fPressed) {
            System.out.println("FPS " + JSimple.FPS);
        }
        if(Input.enterPressed) {
            JSimple.invertColor();
        }
    }
}
