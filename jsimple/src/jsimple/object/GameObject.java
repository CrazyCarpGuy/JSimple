package jsimple.object;

import jsimple.core.JSimple;

/* This class contains information about game object entities, such as their 3D model, orientation, and collision detection.
 * Written by Tate Morris
 * Modified by Ryan Munn
 */

public class GameObject {
    
	public static int count = 0;
	private Script script;
	private int ID;
    private String name;
    public Model model;
    public Transform transform;
    public Collider collider;
    public boolean isVisible;
    
    public GameObject(String name) {
    	
    	this.ID = count;
        count++;
        
        this.setName(name);
        this.setModel(new Model());
        this.setTransform(new Transform());
        this.setCollider(new Collider());
        
        JSimple.addGameObject(this);
        isVisible = true;
        
    }

    public void setName(String name) {
    	this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setScript(Script s) {
    	script = s;
        script.executeInit(this);
    }

    public void setModel(Model model) {
    	this.model = model;
    	model.setParent(this);
    }
    
    public void setTransform(Transform transform) {
    	this.transform = transform;
    	transform.setParent(this);
    }
    
    public void setCollider(Collider collider) {
    	this.collider = collider;
    	collider.setParent(this);
    }
    
    public void update(float dt) {
       if(script != null) {
            script.executeUpdate(this);
       }
	   this.model.update(dt);
	   this.transform.update(dt);
	   this.collider.update(dt);
    }
    
    public boolean equals(GameObject gameObject) {
    	
    	return this.ID == gameObject.ID;
    	
    }

    public void delete() {
        JSimple.removeGameObject(this);
    }
}