package Control;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UserData extends JFrame {
    public static ArrayList<User> userList=new ArrayList<User>();
    public static User ActiveUser;
    public void getUser(){
        System.out.println("UserData:");
        for(User e :userList){
            System.out.println("ID+"+e.getID()+" "+"PW+"+e.getPassword());
        }
    }
}
