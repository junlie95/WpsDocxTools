package xml;

import unzip.LeftBottomJPanel;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyMouseListener extends MouseAdapter {
    public void mouseReleased(MouseEvent me) {
        if ((me.getModifiers() & InputEvent.BUTTON3_MASK) != 0)
            LeftBottomJPanel.popupMenu.show(me.getComponent(), me.getX(), me.getY());
    }

}