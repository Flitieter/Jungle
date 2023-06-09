package view;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.security.PublicKey;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel implements Serializable {
    private Color background;

    public CellComponent(Color background, Point location, int size) {
        setLayout(new GridLayout(1, 1));
        setLocation(location);
        setSize(size, size);
        this.background = background;
    }

    public void setColor(Color c) {
        background = c;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(background);
        g.fillRect(1, 1, this.getWidth() - 1, this.getHeight() - 1);
    }
}
