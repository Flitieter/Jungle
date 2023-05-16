package controller;

import listener.GameListener;
import model.*;
import view.CellComponent;
import view.ChessComponent;
import view.ChessGameFrame;
import view.ChessboardComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 *
 */
public class GameController implements GameListener {

    public static Chessboard model;
    private static ChessboardComponent view;
    private static PlayerColor currentPlayer=PlayerColor.BLUE;

    private static int Round;
    private ArrayList<Integer> now;
    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    public static int getRound(){
        return Round;
    }
    public static PlayerColor getCurrentPlayer(){
        return currentPlayer;
    }
    public GameController(ChessboardComponent view, Chessboard model,int ai) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();

        AI=ai;
    }
    public static void ChangeColor(){
        view.ChangeColor();
    }
    private void initialize() {
        // for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
        // for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

        // }
        // }
    }

    // after a valid move swap the player
    private static void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        TimerMonitor.time=TimerMonitor.TimeLimit;
        Round++;
        ChessGameFrame.statusLabel.setText("Turn: "+((GameController.getRound()/2+1)+" "+GameController.getCurrentPlayer()));
    }

    private static void CheckWin() {
        // 1:BLUE, 2:RED
        int res = model.win();
        if (res == 0)
            return;
        else {
            String[] options = { "新局", "撤销", "读档" };
            int opt = JOptionPane.showOptionDialog(null, (res == 1 ? "蓝方" : "红方") + "胜利！！", "终局",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (opt == 0)
                ;
            // model = new Chessboard();
            else if (opt == 1) {
                model.Erase();
                model.Erase();
            } else
                ;// TODO:
        }
    }

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(0, point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            CheckWin();
            model.Erase_Mark();
            view.Erase_Mark(now);
            view.repaint();

            if(AI>0){
                AImove(AI);
            }
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                CheckWin();
                now = model.Get_Mark(point);
                view.Get_Mark(now);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            model.Erase_Mark();
            view.Erase_Mark(now);
            component.repaint();
        } else if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(1, point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            CheckWin();
            model.Erase_Mark();
            view.Erase_Mark(now);
            view.repaint();

            if(AI>0){
                AImove(AI);
            }
        }
    }

    private int AI;
    static Random random=new Random();
    public static int getRandomNumber(int L, int R){
        if(L==R)return L;
        System.out.println("Random:"+L+" "+R);
        int x=L+random.nextInt(R-L);
        System.out.println(x);
        return x;
    }
    private static PlayerColor AIColor=PlayerColor.RED;
    public static void EasyAI(PlayerColor NowColor){
        List<ChessPiece> Has= new ArrayList<ChessPiece>();
        for(int i=0;i<=8;i++){
            for(int j=0;j<=6;j++){
                ChessPiece nowPiece=model.getChessPieceAt(new ChessboardPoint(i,j));
                if(nowPiece==null)continue;
                if(nowPiece.getOwner()==NowColor){
                    ArrayList<Integer> Could=model.Get_Mark(new ChessboardPoint(i,j));
                    if(!Could.isEmpty())Has.add(nowPiece);
                }
            }
        }
//        System.out.println("The AI now has:");
//        for(ChessPiece e:Has){
//            System.out.println(e.getX()+" "+e.getY()+" "+e.getName());
//        }
//        AIController.GetMap();
        ChessPiece choChess=Has.get(getRandomNumber(0,Has.size()-1));
        ChessboardPoint nowPoint=new ChessboardPoint(choChess.getX(), choChess.getY());
        ArrayList<Integer> Could=model.Get_Mark(nowPoint);
        int to=Could.get(getRandomNumber(0,Could.size()-1));
        ChessboardPoint toPoint=new ChessboardPoint(to/7,to%7);
        boolean flag=(model.getChessPieceAt(toPoint)==null);
        model.moveChessPiece(nowPoint, toPoint);
        view.setChessComponentAtGrid((flag)?(0):(1), toPoint, view.removeChessComponentAtGrid(nowPoint));
        view.repaint();
        swapColor();
        CheckWin();
    }

    public void AImove(int ai){
        EasyAI(AIColor);
//        x = num / 7;
//        y = num % 7;
    }
}
