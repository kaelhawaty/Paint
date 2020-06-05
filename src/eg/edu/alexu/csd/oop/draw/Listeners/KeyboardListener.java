package eg.edu.alexu.csd.oop.draw.Listeners;

import java.awt.*;
import java.awt.event.KeyEvent;

import static eg.edu.alexu.csd.oop.draw.Utils.StaticVars.*;

public class KeyboardListener implements KeyEventDispatcher {

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if(e.getID() == KeyEvent.KEY_PRESSED){
            if(e.getKeyCode() == KeyEvent.VK_DELETE){
                if(selectedShape != null){
                    engine.removeShape(selectedShape);
                    engine.refresh(canvas.getGraphics());
                    selectedShape = null;
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_Z){
                engine.undo();
            }
            if(e.getKeyCode() == KeyEvent.VK_X){
                engine.redo();
            }

        }
        return false;
    }
}
