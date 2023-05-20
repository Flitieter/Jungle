package view;

import Control.User;
import Control.UserData;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public class RegisterFrame extends UserData implements Serializable {
    public UserFrame userFrame=new UserFrame();
    private static int WIDTH=600;
    private static int HEIGHT=400;
    JTextField tfID;
    JPasswordField tfPassword;
    public RegisterFrame(){
        setTitle("Register");
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
//
//        addLoginButton();
        addRegisterButton();
        addBackButton();
    }
    public void register() throws IOException {
        String InID=tfID.getText();
        String InPassword=new String(tfPassword.getPassword());
        if(InID.equals("")){
            JOptionPane.showMessageDialog(null, "Please input ID", "Error", 0);
            return;
        }
        else if(InPassword.equals("")){
            JOptionPane.showMessageDialog(null, "Please input password", "Error", 0);
            return;
        }
        for(User e:userList){
            if(Objects.equals(e.getID(), InID)){
                JOptionPane.showMessageDialog(null, "The ID has existed!", "Error", 0);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Register successfully", "Message", 1);
        userList.add(new User(InID,InPassword));
        WriteToFile();
        getUser();
        this.setVisible(false);
        userFrame.setVisible(true);
    }
    private void addRegisterButton() {
        JButton button = new JButton("Register");
        button.setLocation(320, 210);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 12));
        add(button);
        button.addActionListener((e) -> {
            try {
                register();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    private void addBackButton() {
        JButton button = new JButton("Back");
        button.setLocation(170, 210);
        button.setSize(100, 30);
        button.setFont(new Font("Rockwell", Font.BOLD, 12));
        add(button);
        button.addActionListener((e) -> {
            this.setVisible(false);
            userFrame.setVisible(true);
        });
    }
}
