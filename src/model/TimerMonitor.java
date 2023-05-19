package model;

import controller.GameController;
import view.ChessGameFrame;

import java.io.Serializable;

public class TimerMonitor extends Thread implements Serializable {
    public static int TimeLimit=40;
    public static int time;
    @Override
    public void run(){
        synchronized (this){
            while(true){
                time=TimeLimit;
                while(time>0){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    time--;
                    if(GameController.Win)time=TimeLimit;
                    ChessGameFrame.TimeScreen.setText("Time: "+time);
                }
                GameController.EasyAI(GameController.getCurrentPlayer());
            }
        }
    }
}
