package view;

import Control.User;
import Control.UserData;
import controller.GameController;
import model.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

public class StartFrame extends UserData {
    private static int WIDTH=1100;
    private static int HEIGHT=810;

    static JLabel activeUser=new JLabel("Please login first!");
    private void addNewGameButton() {
        JButton button = new JButton("new game");
        button.setLocation(100, 100);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener((e) -> {
            NewGameFrame newGameFrame=new NewGameFrame();
            newGameFrame.setVisible(true);
            this.setVisible(false);
            //隐藏
        });
    }

    private void addLoadGameButton() {
        JButton button = new JButton("load game");
        button.setLocation(100, 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener((e) -> {
            LoadFrame loadFrame=new LoadFrame();
            this.setVisible(false);
            loadFrame.setVisible(true);
            //隐藏
        });
    }
    void addQuitButton(){
        JButton button = new JButton("quit");
        button.setLocation(100, 500);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener((e) -> {
            this.setVisible(false);
            //隐藏
        });
    }
    private void addRankButton(){
        JButton button = new JButton("Rank");
        button.addActionListener((e) -> {
            Collections.sort(userList);
            String rank = "Rank  ID   WinTimes/PlayTimes\n";
            if(userList.isEmpty()){
                rank="There is no user yet!";
            }
            else{
                for(int i=0;i<userList.size();i++){
                    User user=userList.get(i);
                    rank+=(i+1)+": "+user.getID()+"   "+user.getWinTimes()+"/"+user.getPlayTimes();
                }
            }
            JOptionPane.showMessageDialog(null, rank, "Rank List",1);
        });
        button.setLocation(100, 300);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addUserButton() {
        JButton button = new JButton("User");
        button.setLocation(100, 400);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener((e) -> {
            UserFrame userFrame=new UserFrame();
            userFrame.setVisible(true);
        });
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
    public StartFrame(){
        setTitle("Jungle");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        activeUser.setFont(new Font("", Font.BOLD, 12));
        activeUser.setBounds(800, 20, 300, 50);
        add(activeUser);

        addNewGameButton();
        addLoadGameButton();
        addQuitButton();
        addUserButton();
        addRankButton();

        AddPicture("resource/background.jpg",WIDTH,HEIGHT,0,0);
//        AddPicture("resource/R-C.png",256,256,500,500);

    }
}
