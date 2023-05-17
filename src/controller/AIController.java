package controller;

import model.*;
import view.ChessboardComponent;

import java.io.IOException;
import java.util.ArrayList;

public class AIController{

    private Chessboard model;
    private int LimStep;
    public ArrayList<ChessboardPoint> Fr=new ArrayList<>();
    public ArrayList<ChessboardPoint> To=new ArrayList<>();
    public AIController(int LimStep) throws IOException, ClassNotFoundException {
        model=GameController.model.My_Clone();
        this.LimStep=LimStep;
    }
    public int Status(PlayerColor current){
        int sum=0;
        for(int i=0;i<=8;i++){
            for(int j=0;j<=6;j++){
                ChessPiece nowPiece=model.getChessPieceAt(new ChessboardPoint(i,j));
                if(nowPiece==null)continue;
                if(nowPiece.getOwner()==current){
                    if((nowPiece.getY()==3&&nowPiece.getX()==0)||(nowPiece.getY()==3&&nowPiece.getX()==8)){
                        return 100000;
                    }
                    else sum+= nowPiece.getRank();
                }
                else {
                    if((nowPiece.getY()==3&&nowPiece.getX()==0)||(nowPiece.getY()==3&&nowPiece.getX()==8)){
                        return -100000;
                    }
                    else sum-=nowPiece.getRank();
                }
            }
        }
        return sum;
    }
    public int Move(PlayerColor current,int step){
        int tmp=Status(current);
        if(tmp==-100000||tmp==100000)return tmp;
        if(step==LimStep+1){
            return Status(current);
        }
        int maxStatus=-1000000;
        for(int i=0;i<=8;i++){
            for(int j=0;j<=6;j++){
                ChessPiece nowPiece=model.getChessPieceAt(new ChessboardPoint(i,j));
                if(nowPiece==null)continue;
                if(nowPiece.getOwner()==current){
                    ArrayList<Integer> Could=model.Get_Mark(new ChessboardPoint(i,j));
                    ChessboardPoint nowPoint=new ChessboardPoint(i, j);
                    for(Integer to:Could){
                        ChessboardPoint toPoint=new ChessboardPoint(to/7,to%7);
                        model.moveChessPiece(nowPoint, toPoint);
                        PlayerColor newColor;
                        if(current==PlayerColor.BLUE)newColor=PlayerColor.RED;
                        else newColor=PlayerColor.BLUE;
                        int res=-Move(newColor,step+1);
                        if(res>maxStatus){
                            maxStatus=res;
                            if(step==1){
                                Fr.clear();
                                To.clear();
                                Fr.add(nowPoint);
                                To.add(toPoint);
                            }
                        }
                        else if(res==maxStatus){
                            if(step==1){
                                Fr.add(nowPoint);
                                To.add(toPoint);
                            }
                        }
                        model.Erase();
                    }
                }
            }
        }
//        model.getGridAt(new ChessboardPoint(0,0)).setPiece(model.getChessPieceAt(new ChessboardPoint(0,6)));
//        model.PrintMap();
        return maxStatus;
    }
}
