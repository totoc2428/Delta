package src.platform.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput {
    private static boolean isPressed;
    public static boolean isKeyPressed(int KeyEventNumber) {
        Object lock = new Object();

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEventNumber) {
                    isPressed = true;
                    synchronized (lock) {
                        lock.notify();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        synchronized (lock) {
            try {
                java.awt.KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
                    if (e.getID() == KeyEvent.KEY_TYPED) {
                        keyListener.keyTyped(e);
                    } else if (e.getID() == KeyEvent.KEY_PRESSED) {
                        keyListener.keyPressed(e);
                    } else if (e.getID() == KeyEvent.KEY_RELEASED) {
                        keyListener.keyReleased(e);
                    }
                    return false;
                });
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return isPressed;
    }

}
