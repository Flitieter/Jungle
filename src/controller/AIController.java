package controller;

import model.*;
import view.ChessboardComponent;

import java.io.IOException;
import java.util.ArrayList;

public class AIController{

    private static Chessboard model;
    private static int LimStep;
    public int Status(PlayerColor current){
        int sum=0;
        for(int i=0;i<=8;i++){
            for(int j=0;j<=6;j++){
                ChessPiece nowPiece=model.getChessPieceAt(new ChessboardPoint(i,j));
                if(nowPiece==null)continue;
                if(nowPiece.getOwner()==current){
                    sum+= nowPiece.getRank();
                }
                else sum-=nowPiece.getRank();
            }
        }
        return sum;
    }
    public int Move(PlayerColor current,int step){
        if(step==LimStep+1){
            return Status(current);
        }
        int maxStatus=0;
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
                        maxStatus=Math.max(Move(newColor,step+1),-maxStatus);
                    }

                }
            }
        }
        return maxStatus;
    }
    public static void GetMap() throws IOException, ClassNotFoundException {
        model=GameController.model.My_Clone();

    }
}
