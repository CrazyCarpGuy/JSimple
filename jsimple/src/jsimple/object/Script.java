package jsimple.object;

/**
 * An interface to form the basis of object scripting in memory
 * @author Benjamin Boardman
 * @since 2022-04-16
 *
 */
public interface Script {

    /**
     * This script initializes a GameObject upon being paired with it.
     * @param o the GameObject that the script is being applied to
     */
	public void executeInit(GameObject o);
	
	/**
     * Executes the main functions of the script, updated every frame.
     * @param o the GameObject that the script is being applied to
     */
    public void executeUpdate(GameObject o);
    
    
}
