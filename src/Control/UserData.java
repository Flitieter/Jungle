package Control;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserData extends JFrame implements Serializable {
    public static ArrayList<User> userList=new ArrayList<User>();
    public static User ActiveUser;
    public void getUser(){
        System.out.println("UserData:");
        for(User e :userList){
            System.out.println("ID+"+e.getID()+" "+"PW+"+e.getPassword());
        }
    }
    public void WriteToFile() throws IOException {
        System.out.println("Write!");
        File outfile = new File("data\\User.txt");
        // PrintWriter Out = new PrintWriter(outfile, "UTF-8");
        PrintWriter Out = new PrintWriter(outfile, "UTF-8");
        int tot=userList.size();
        Out.println(tot);
        for(User e:userList){
            Out.println(e.getID());
            Out.println(e.getPassword());
            Out.println(e.getWinTimes());
            Out.println(e.getPlayTimes());
        }
        Out.close();
    }
    public static void ReadFromFile() throws IOException, ClassNotFoundException {
        System.out.println("Read!");
        File infile = new File("data\\User.txt");
        Scanner In = new Scanner(infile, "UTF-8");
        int tot=In.nextInt();
        for(int i=1;i<=tot;i++){
            String ID=In.next();
            String Pw=In.next();
            int WinTimes=In.nextInt();
            int PlayTimes=In.nextInt();
            userList.add(new User(ID,Pw,WinTimes,PlayTimes));
        }
    }
}
