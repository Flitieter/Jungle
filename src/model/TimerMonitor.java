package model;

import controller.GameController;
import view.ChessGameFrame;

public class TimerMonitor extends Thread{
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
                    ChessGameFrame.TimeScreen.setText("Time: "+time);
                }
                GameController.EasyAI(GameController.getCurrentPlayer());
            }
        }
    }
}
