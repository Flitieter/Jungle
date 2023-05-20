package view;

import controller.GameController;
import model.MusicPlayer;
import model.TimerMonitor;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */

public class ChessGameFrame extends JFrame implements Serializable {
    // public final Dimension FRAME_SIZE ;
    private static int WIDTH;
    private static int HEIGTH;

    private static int ONE_CHESS_SIZE;
    private static int[][] Map = new int[10][7];
    private static TimerMonitor timer;
    private static ChessboardComponent chessboardComponent;
    public static JLabel TimeScreen;
    public static JLabel statusLabel;
    private ArrayList<JLabel>Picture=new ArrayList<JLabel>();
    private int nowPicture=0;
    public ChessGameFrame(int width, int height) {
        setTitle("The Jungle"); // 设置标题
        WIDTH = width;
        HEIGTH = height;
        ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        TimerMonitor.time=TimerMonitor.TimeLimit;
        if(timer==null){
            timer=new TimerMonitor();
            timer.start();
        }
        addTimeScreenLabel();
        addChessboard();
        addLabel();
        addLoadButton();
        addEraseButton();
        addBackButton();
        addChangeBackgroundButton();
        addChangeChessboardButton();
        addSaveButton();
        addPlayBackButton();
        AddPicture("resource/ChessBoard1.jpg",WIDTH,HEIGTH,0,0);
        AddPicture("resource/ChessBoard1.png",WIDTH,HEIGTH,0,0);
        Picture.get(nowPicture).setVisible(true);
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        ChessGameFrame.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addTimeScreenLabel(){
        TimeScreen=new JLabel("Time: "+TimerMonitor.TimeLimit);
        TimeScreen.setLocation(700, HEIGHT / 10);
        TimeScreen.setSize(120, 60);
        TimeScreen.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(TimeScreen);
    }
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        statusLabel = new JLabel("Turn: "+((GameController.getRound()/2+1)+" "+GameController.getCurrentPlayer()));
        statusLabel.setLocation(900, HEIGTH/10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }
    public void AddPicture(String Address,int width,int height,int x,int y){
        Image image = new ImageIcon(Address).getImage();
        image = image.getScaledInstance(width, height,Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        JLabel bg = new JLabel(icon);
        bg.setSize(width, height);
        bg.setLocation(x, y);
        bg.setVisible(false);
        add(bg);
        Picture.add(bg);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.addActionListener((e) -> {
            try {
                GameController.Load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }


    private void addEraseButton() {
        JButton button = new JButton("Erase");
        button.addActionListener((e) ->
                GameController.Erase()
//                JOptionPane.showMessageDialog(this, "Erase")
        );
        button.setLocation(HEIGTH, HEIGTH / 10 + 180);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

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
    private void addChangeBackgroundButton() {
        JButton button = new JButton("Change Background");
        button.addActionListener((e) ->{
            Picture.get(nowPicture).setVisible(false);
            nowPicture=nowPicture^1;
            Picture.get(nowPicture).setVisible(true);
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }
    private void addChangeChessboardButton() {
        JButton button = new JButton("Change Chessboard");
        button.addActionListener((e) ->{
            GameController.ChangeColor();
//            ChessboardComponent.ChangeColor();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 300);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }
    private void addPlayBackButton() {
        JButton button = new JButton("Play Back");
        button.addActionListener((e) ->{
            GameController.PlayBack();
//            ChessboardComponent.ChangeColor();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 +360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }
    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.addActionListener((e) -> {
            String Way=GameController.getFileWay();
            try {
                GameController.model.Save(Way);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 420);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    // private void addLoadButton() {
    // JButton button = new JButton("Load");
    // button.setLocation(HEIGTH, HEIGTH / 10 + 240);
    // button.setSize(200, 60);
    // button.setFont(new Font("Rockwell", Font.BOLD, 20));
    // add(button);
    //
    // button.addActionListener(e -> {
    // System.out.println("Click load");
    // String path = JOptionPane.showInputDialog(this,"Input Path here");
    // gameController.loadGameFromFile(path);
    // });
    // }

}
