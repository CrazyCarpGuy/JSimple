package jsimple.core;
import jsimple.core.input.Key;

import java.awt.MouseInfo;
// Adds Key inputs
// KeyChar = Character
// KeyCode = Number that represent key
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Input extends MouseAdapter implements KeyListener {
    public Key backspace = new Key(8/*keyCode*/);

    public static boolean wPressed, aPressed, sPressed, dPressed, iPressed, fPressed, leftPressed, rightPressed, upPressed, downPressed,
    						spacePressed, escPressed, enterPressed, shiftPressed, ctrlPressed, mouseDown, zeroPressed, onePressed, twoPressed, threePressed,
                            fourPressed, fivePressed, sixPressed, sevenPressed, eightPressed, ninePressed, semicolonPressed, F1Pressed, F2Pressed, F3Pressed, 
                            F4Pressed, F5Pressed, F6Pressed, F7Pressed, F8Pressed, F9Pressed, F10Pressed, F11Pressed, F12Pressed,
                            bPressed, cPressed, ePressed, gPressed, hPressed, jPressed, kPressed, lPressed, mPressed, nPressed, oPressed, pPressed, qPressed,
                            rPressed, tPressed, uPressed, vPressed, xPressed, yPressed, zPressed, bkspcPressed, tabPressed, altPressed, capLckPressed, pgUpPressed,
                            pgDwnPressed, deletePressed, equalPressed, hyphenPressed, commaPressed, periodPressed, fwdSlashPressed, gravePressed, lBracketPressed, bckSlashPressed,
                            rBracketPressed;
    
    @Override
    public void keyTyped(KeyEvent e) {
        // When a Key is used (Only uses KeyChar, thus gives character output)
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // When a Key is Pressed (Only uses KeyCode, thus gives integer output of key code)

        try {
            Key.keys.get(e.getKeyCode()).press();
        }
        catch (Exception x) {}
        switch (e.getKeyCode()){
            case 8: // Backspace key is pressed
                bkspcPressed = true;
                break;
            case 9: // Tab key is pressed
                tabPressed = true;
                break;
            case 10: // Enter key is pressed
                enterPressed = true;
                break;
            case 16: // Shift key is pressed
                shiftPressed = true;
                break;
            case 17: // Ctrl key is pressed
                ctrlPressed = true;
                break;
            case 18: // Alt key is pressed
                altPressed = true;
                break;
            case 20: // Caps lock key is pressed
                capLckPressed = true;
                break;
            case 27: // Esc key is pressed
                escPressed = true;
                break;
            case 32: // Space key is pressed
                spacePressed = true;
                break;
            case 33: // Page Up key is pressed
                pgUpPressed = true;
                break;
            case 34: // Page Down key is pressed
                pgDwnPressed = true;
                break;
            case 37: // Left key is pressed
                leftPressed = true;
                break;
            case 38: // Up key is pressed
                upPressed = true;
                break;
            case 39: // Right key is pressed
                rightPressed = true;
                break;
            case 40: // Down key is pressed
                downPressed = true;
                break;
            case 46: // delete key is pressed
                deletePressed = true;
                break;
            case 48: // 0 key is pressed
                zeroPressed = true;
                break;
            case 49: // 1 key is pressed
                onePressed = true;
                break;
            case 50: // 2 key is pressed
                twoPressed = true;
                break;
            case 51: // 3 key is pressed
                threePressed = true;
                break;
            case 52: // 4 key is pressed
                fourPressed = true;
                break;
            case 53: // 5 key is pressed
                fivePressed = true;
                break;
            case 54: // 6 key is pressed
                sixPressed = true;
                break;
            case 55: // 7 key is pressed
                sevenPressed = true;
                break;
            case 56: // 8 key is pressed
                eightPressed = true;
                break;
            case 57: // 9 key is pressed
                ninePressed = true;
                break;
            case 59: // semicolon key is pressed
                semicolonPressed = true;
                break;
            case 61: // = key is pressed
                equalPressed = true;
                break;
            case 65:  // A key is pressed
                aPressed = true;
                break;
            case 66: // B key is pressed
                bPressed = true;
                break;
            case 67: // C key is pressed
                cPressed = true;
                break;
            case 68: // D key is pressed
                dPressed = true;
                break;
            case 69: // E key is pressed
                ePressed = true;
                break;
            case 70: // F key is pressed
                fPressed = true;
                break;
            case 71: // G key is pressed
                gPressed = true;
                break; 
            case 72: // H key is pressed
                hPressed = true;
                break;
            case 73: // I key is pressed
                iPressed = true;
                break;
            case 74: // J key is pressed
                jPressed = true;
                break;
            case 75: // K key is pressed
                kPressed = true;
                break; 
            case 76: // L key is pressed    
                lPressed = true;
                break;
            case 77: // M key is pressed
                mPressed = true;
                break;
            case 78: // N key is pressed 
                nPressed = true;
                break;
            case 79: // O key is pressed
                oPressed = true;
                break;
            case 80: // P key is pressed
                pPressed = true;
                break;
            case 81: // Q key is pressed
                qPressed = true;
                break;
            case 82: // R key is pressed
                rPressed = true;
                break;
            case 83: // S key is pressed
                sPressed = true;
                break;
            case 84: // T key is pressed
                tPressed = true;
                break;
            case 85: // U key is pressed
                uPressed = true;
                break;
            case 86: // V key is pressed
                vPressed = true;
                break;
            case 87: // W key is pressed
                wPressed = true;
                break;
            case 88: // X key is pressed
                xPressed = true;
                break;
            case 89: // Y key is pressed
                yPressed = true;
                break;
            case 90: // Z key is pressed
                zPressed = true;
                break;
            case 112: // F1 key is pressed
                F1Pressed = true;
                break;
            case 113: // F2 key is pressed
                F2Pressed = true;
                break;
            case 114: // F3 key is pressed
                F3Pressed = true;
                break;
            case 115: // F4 key is pressed
                F4Pressed = true;
                break;
            case 116: // F5 key is pressed
                F5Pressed = true;
                break;
            case 117: // F6 key is pressed
                F6Pressed = true;
                break;
            case 118: // F7 key is pressed
                F7Pressed = true;
                break;
            case 119: // F8 key is pressed
                F8Pressed = true;
                break;
            case 120: // F9 key is pressed
                F9Pressed = true;
                break;
            case 121: // F10 key is pressed
                F10Pressed = true;
                break;
            case 122: // F11 key is pressed
                F11Pressed = true;
                break;
            case 123: // F12 key is pressed
                F12Pressed = true;
                break;
            case 173: // - key is pressed
                hyphenPressed = true;
                break;
            case 188: // , key is pressed
                commaPressed = true;
                break;
            case 190: // . key is pressed
                periodPressed = true;
                break;
            case 191: // / key is pressed
                fwdSlashPressed = true;
                break;
            case 192: // ` key is pressed
                gravePressed = true;
                break;
            case 219: // [ key is pressed
                lBracketPressed = true;
                break;
            case 220: // \ key is pressed
                bckSlashPressed = true;
                break;
            case 221: // ] key is pressed
                rBracketPressed = true;
                break;


    }
    
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // When a key is released (Uses both KeyCode and KeyChar, thus gives either output but keep in mind it only works once the key is released)

        try {
            Key.keys.get(e.getKeyCode()).release();
        }
        catch (Exception x) {}
        switch (e.getKeyCode()){
            case 8: // Backspace key is released
                bkspcPressed = false;
                break;
            case 9: // Tab key is released
                tabPressed = false;
                break;
            case 10: // Enter key is released
                enterPressed = false;
                break;
            case 16: // Shift key is released
                shiftPressed = false;
                break;
            case 17: // Ctrl key is released
                ctrlPressed = false;
                break;
            case 18: // Alt key is released
                altPressed = false;
                break;
            case 20: // Caps lock key is released
                capLckPressed = false;
                break;
            case 27: // Esc key is released
                escPressed = false;
                break;
            case 32: // Space key is released
                spacePressed = false;
                break;
            case 33: // Page Up key is released
                pgUpPressed = false;
                break;
            case 34: // Page Down key is released
                pgDwnPressed = false;
                break;
            case 37: // Left key is released
                leftPressed = false;
                break;
            case 38: // Up key is released
                upPressed = false;
                break;
            case 39: // Right key is released
                rightPressed = false;
                break;
            case 40: // Down key is released
                downPressed = false;
                break;
            case 46: // delete key is released
                deletePressed = false;
                break;
            case 48: // 0 key is released
                zeroPressed = false;
                break;
            case 49: // 1 key is released
                onePressed = false;
                break;
            case 50: // 2 key is released
                twoPressed = false;
                break;
            case 51: // 3 key is released
                threePressed = false;
                break;
            case 52: // 4 key is released
                fourPressed = false;
                break;
            case 53: // 5 key is released
                fivePressed = false;
                break;
            case 54: // 6 key is released
                sixPressed = false;
                break;
            case 55: // 7 key is released
                sevenPressed = false;
                break;
            case 56: // 8 key is released
                eightPressed = false;
                break;
            case 57: // 9 key is released
                ninePressed = false;
                break;
            case 59: // semicolon key is released
                semicolonPressed = false;
                break;
            case 61: // = key is released
                equalPressed = false;
                break;
            case 65:  // A key is released
                aPressed = false;
                break;
            case 66: // B key is released
                bPressed = false;
                break;
            case 67: // C key is released
                cPressed = false;
                break;
            case 68: // D key is released
                dPressed = false;
                break;
            case 69: // E key is released
                ePressed = false;
                break;
            case 70: // F key is released
                fPressed = false;
                break;
            case 71: // G key is released
                gPressed = false;
                break; 
            case 72: // H key is released
                hPressed = false;
                break;
            case 73: // I key is released
                iPressed = false;
                break;
            case 74: // J key is released
                jPressed = false;
                break;
            case 75: // K key is released
                kPressed = false;
                break; 
            case 76: // L key is released    
                lPressed = false;
                break;
            case 77: // M key is released
                mPressed = false;
                break;
            case 78: // N key is released 
                nPressed = false;
                break;
            case 79: // O key is released
                oPressed = false;
                break;
            case 80: // P key is released
                pPressed = false;
                break;
            case 81: // Q key is released
                qPressed = false;
                break;
            case 82: // R key is released
                rPressed = false;
                break;
            case 83: // S key is released
                sPressed = false;
                break;
            case 84: // T key is released
                tPressed = false;
                break;
            case 85: // U key is released
                uPressed = false;
                break;
            case 86: // V key is released
                vPressed = false;
                break;
            case 87: // W key is released
                wPressed = false;
                break;
            case 88: // X key is released
                xPressed = false;
                break;
            case 89: // Y key is released
                yPressed = false;
                break;
            case 90: // Z key is released
                zPressed = false;
                break;
            case 112: // F1 key is released
                F1Pressed = false;
                break;
            case 113: // F2 key is released
                F2Pressed = false;
                break;
            case 114: // F3 key is released
                F3Pressed = false;
                break;
            case 115: // F4 key is released
                F4Pressed = false;
                break;
            case 116: // F5 key is released
                F5Pressed = false;
                break;
            case 117: // F6 key is released
                F6Pressed = false;
                break;
            case 118: // F7 key is released
                F7Pressed = false;
                break;
            case 119: // F8 key is released
                F8Pressed = false;
                break;
            case 120: // F9 key is released
                F9Pressed = false;
                break;
            case 121: // F10 key is released
                F10Pressed = false;
                break;
            case 122: // F11 key is released
                F11Pressed = false;
                break;
            case 123: // F12 key is released
                F12Pressed = false;
                break;
            case 173: // - key is released
                hyphenPressed = false;
                break;
            case 188: // , key is released
                commaPressed = false;
                break;
            case 190: // . key is released
                periodPressed = false;
                break;
            case 191: // / key is released
                fwdSlashPressed = false;
                break;
            case 192: // ` key is released
                gravePressed = false;
                break;
            case 219: // [ key is released
                lBracketPressed = false;
                break;
            case 220: // \ key is released
                bckSlashPressed = false;
                break;
            case 221: // ] key is released
                rBracketPressed = false;
                break;
        }
    }  
    
    @Override
    public void mousePressed(MouseEvent e){
    	mouseDown = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseDown = false; 
    }
    
    public static int getMouseX() {
    	return MouseInfo.getPointerInfo().getLocation().x;
    }
    
    public static int getMouseY() {
    	return MouseInfo.getPointerInfo().getLocation().y;
    }

}

