package Control;

import java.io.IOException;

public class User implements Comparable<User>{
    private String ID;
    private String Password;
    private int WinTimes;
    private int PlayTimes;
    public String getID(){
        return ID;
    }
    public String getPassword(){
        return Password;
    }
    public User(String ID,String password){
        this.ID=ID;
        this.Password=password;
    }
    public User(String ID,String password,int winTimes,int playTimes){
        this.ID=ID;
        this.Password=password;
        this.WinTimes=winTimes;
        this.PlayTimes=playTimes;
    }
    public int compareTo(User o) {
        return o.WinTimes - this.WinTimes;
    }
    public int getWinTimes(){
        return WinTimes;
    }
    public int getPlayTimes(){
        return PlayTimes;
    }
    public void addWinTimes() throws IOException {
        WinTimes++;
        UserData userData=new UserData();
        userData.WriteToFile();
    }
    public void addPlayTimes() throws IOException {
        PlayTimes++;
        UserData userData=new UserData();
        userData.WriteToFile();
    }
}
