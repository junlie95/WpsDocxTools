package UiView;

import javax.swing.JPanel;
import java.awt.*;
import java.io.IOException;

public class LeftPane extends JPanel {
    BorderLayout borderLayout = new BorderLayout();

    public LeftPane() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws IOException {
        this.setLayout(borderLayout);
    }
}


