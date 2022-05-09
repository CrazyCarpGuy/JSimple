package jsimple.core.input;

import java.util.HashMap;

public class Key {
    public static HashMap<Integer, Key> keys =new HashMap<>();
    private boolean pressed;
    private int keyCode;

    public Key(int k) {
        keyCode = k;
        pressed = false;
        keys.put(keyCode, this);
    }

    public void press() {
        pressed = true;
    }

    public void release() {
        pressed = false;
    }

    public boolean isPressed() {
        return pressed;
    }

}
