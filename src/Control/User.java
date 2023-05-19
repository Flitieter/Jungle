package Control;

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
    public int compareTo(User o) {
        return o.WinTimes - this.WinTimes;
    }
    public int getWinTimes(){
        return WinTimes;
    }
    public int getPlayTimes(){
        return PlayTimes;
    }
    public void addWinTimes(){
        WinTimes++;
    }
    public void addPlayTimes(){
        PlayTimes++;
    }
}
