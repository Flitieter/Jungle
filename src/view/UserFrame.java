package view;

import Control.User;
import Control.UserData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserFrame extends UserData {
    private static int WIDTH=600;
    private static int HEIGHT=400;
    JTextField tfID;
    JPasswordField tfPassword;


    public void Succeed_login(){


    }
    private void login(){
        String InID=tfID.getText();
        String InPassword=new String(tfPassword.getPassword());
        System.out.println(InID+" "+InPassword);
        if(InID.equals("")){
            JOptionPane.showMessageDialog(null, "Please input ID", "Error", 0);
            return;
        }
        else if(InPassword.equals("")){
            JOptionPane.showMessageDialog(null, "Please input password", "Error", 0);
            return;
        }
        getUser();
        for(User e:userList){
            System.out.println(e.getID());
            if(e.getID().equals(InID)){
                if(e.getPassword().equals(InPassword)){
                    StartFrame.activeUser.setText("Welcome back, "+InID+"!");
                    this.setVisible(false);
                    ActiveUser=new User(InID,InPassword);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Your password is wrong!", "Error", 0);
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "User doesn't exist!", "Error", 0);
    }
    public UserFrame(){
        setTitle("Login");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setLayout(null);

        JLabel lbAccount = new JLabel("UserID");
        lbAccount.setFont(new Font("", Font.BOLD, 20));
        lbAccount.setBounds(55, 90, 100, 40);
        add(lbAccount);

        tfID = new JTextField();
        tfID.setBounds(200, 95, 200, 30);
        add(tfID);

        JLabel lbPassword = new JLabel("Password");
        lbPassword.setFont(new Font("", Font.BOLD, 20));
        lbPassword.setBounds(55, 140, 100, 40);
        add(lbPassword);

        tfPassword = new JPasswordField();
        tfPassword.setBounds(200, 145, 200, 30);
        add(tfPassword);

        addLoginButton();
        addRegisterButton();

    }
    private void addLoginButton() {
        JButton button = new JButton("Login");
        button.setLocation(170, 210);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 12));
        add(button);
        button.addActionListener((e) -> {
            login();
        });
    }
    private void addRegisterButton() {
        JButton button = new JButton("Register");
        button.setLocation(320, 210);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 12));
        add(button);
        button.addActionListener((e) -> {
            this.setVisible(false);
            RegisterFrame registerFrame=new RegisterFrame();
            registerFrame.setVisible(true);
        });
    }
}
