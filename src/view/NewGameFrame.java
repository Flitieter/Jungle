package view;

import Control.User;
import Control.UserData;
import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;

public class NewGameFrame extends UserData {
    private  int WIDTH=410;
    private  int HEIGHT=610;

    private void addBackButton() {
        JButton button = new JButton("Back");
        button.setLocation(20, 20);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 12));
        add(button);
        button.addActionListener((e) -> {
            StartFrame startFrame=new StartFrame();
            this.setVisible(false);
            startFrame.setVisible(true);
        });
    }
    private void addPlayWithPlayerButton() {
        JButton button = new JButton("Play with player");
        button.setLocation(100, 100);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
        button.addActionListener((e) -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(),0);
            mainFrame.setVisible(true);
            this.setVisible(false);
        });
    }

    private void addPlayEasyButton() {
        JButton button = new JButton("Easy");
        button.setLocation(100, 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
        button.addActionListener((e) -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(),1);
            mainFrame.setVisible(true);
            this.setVisible(false);
        });
    }

    private void addPlayMediumButton() {
        JButton button = new JButton("Medium");
        button.setLocation(100, 300);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
        button.addActionListener((e) -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(),2);
            mainFrame.setVisible(true);
            this.setVisible(false);
        });
    }

    private void addPlayHardButton() {
        JButton button = new JButton("Hard");
        button.setLocation(100, 400);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
        button.addActionListener((e) -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(),3);
            mainFrame.setVisible(true);
            this.setVisible(false);
        });
    }
    public NewGameFrame(){
        setTitle("New Game");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addPlayWithPlayerButton();
        addBackButton();
        addPlayEasyButton();
        addPlayMediumButton();
        addPlayHardButton();
    }
}
