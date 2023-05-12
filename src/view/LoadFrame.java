package view;

import Control.UserData;

import javax.swing.*;
import java.awt.*;

public class LoadFrame extends UserData {
    public StartFrame startFrame=new StartFrame();
    private static int WIDTH=400;
    private static int HEIGHT=600;
    public LoadFrame(){
        setTitle("Load");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setLayout(null);

        addBackButton();
        addSave1Button();
        addSave2Button();
        addSave3Button();
    }
    private void addBackButton() {
        JButton button = new JButton("Back");
        button.setLocation(20, 20);
        button.setSize(70, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 10));
        add(button);
        button.addActionListener((e) -> {
            this.setVisible(false);
            startFrame.setVisible(true);
        });
    }
    private void addSave1Button() {
        JButton button = new JButton("Save 1");
        button.setLocation(100, 160);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener((e) -> {
            this.setVisible(false);
            startFrame.setVisible(true);
        });
    }
    private void addSave2Button() {
        JButton button = new JButton("Save 2");
        button.setLocation(100, 270);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener((e) -> {
            this.setVisible(false);
            startFrame.setVisible(true);
        });
    }
    private void addSave3Button() {
        JButton button = new JButton("Save 3");
        button.setLocation(100, 380);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener((e) -> {
            this.setVisible(false);
            startFrame.setVisible(true);
        });
    }
}
