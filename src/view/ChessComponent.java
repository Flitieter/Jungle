package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ChessComponent extends JComponent {
    private PlayerColor owner;
    private int kind;
    private boolean selected;
    static String MapBlue[] = { "", "Rat_Blue", "Cat_Blue", "Dog_Blue", "Wolf_Blue", "Leopard_Blue", "Tiger_Blue", "Lion_Blue", "Elephant_Blue" };
    static String MapRed[] = { "", "Rat_Red", "Cat_Red", "Dog_Red", "Wolf_Red", "Leopard_Red", "Tiger_Red", "Lion_Red", "Elephant_Red" };
    public ChessComponent(PlayerColor owner, int size, int kind) {
        this.owner = owner;
        this.selected = false;
        setSize(size / 2, size / 2);
        this.kind = kind;
        setLocation(0, 0);
        setVisible(true);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void AddPicture(String Address,int width,int height,int x,int y){
        Image image = new ImageIcon(Address).getImage();
        image = image.getScaledInstance(width, height,Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        JLabel bg = new JLabel(icon);
        bg.setSize(width, height);
        bg.setLocation(x, y);
        add(bg);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if(owner.getColor()==Color.BLUE){
            AddPicture("resource/ChessImage/"+MapBlue[kind]+".png", 40,40,15,15);
        }
        else{
            AddPicture("resource/ChessImage/"+MapRed[kind]+".png", 40,40,15,15);
        }
//        Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
//        g2.setFont(font);
//        g2.setColor(owner.getColor());
//        g2.drawString(Map[kind], getWidth() / 4, getHeight() * 5 / 8); // FIXME: Use
        // library to find the correct offset.
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}
