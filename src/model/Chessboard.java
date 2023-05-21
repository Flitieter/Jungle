package model;

import controller.GameController;
import view.ChessComponent;
import view.ChessboardComponent;

import javax.swing.*;
import java.io.*;
// import java.io.FileNotFoundException;
// import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard implements Serializable {
    private Cell[][] grid;
    private ChessPiece[] chess = new ChessPiece[20];
    private int[][][] Link = new int[10][102][102];
    public int top, now;
    public int[] Fr_x = new int[10004], Fr_y = new int[10004], To_x = new int[10004], To_y = new int[10004];
    public int[] Id = new int[10004], Num = new int[10004];
    private int Rx[] = { -1, 1, 0, 0 };
    private int Ry[] = { 0, 0, -1, 1 };
    private int[] Mk = new int[1002], Now = new int[5];

    // private ChessboardComponent view;
    // public void RegisterChessboardComponent(ChessboardComponent view){
    // this.view=view;
    // }
    public void initLog() {
        for (int i = 1; i <= top + 2; i++)
            Fr_x[i] = Fr_y[i] = To_x[i] = To_y[i] = Id[i] = Num[i] = 0;
        top = 0;
    }

    public Chessboard My_Clone() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(this); // 序列化

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Chessboard) objectInputStream.readObject();
    }

    public Chessboard() {
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];// 19X19
        initGrid();
        initPieces();
    }

    public void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private boolean In(int x, int y) {
        return x >= 0 && x <= 8 && y >= 0 && y <= 6;
    }

    private boolean In_Rever(int x, int y) {
        return x >= 3 && x <= 5 && ((1 <= y && y <= 2) || ((4 <= y && y <= 5)));
    }

    private boolean Check_M(int x, int y) {
        return grid[x][y].getPiece() != null && grid[x][y].getPiece().GetRank() == 1;
    }

    public ArrayList<Integer> Get_Mark(ChessboardPoint point) {
        int x = point.getRow(), y = point.getCol();
        int num = getChessPieceAt(point).getId();
        return Get_Mark(x, y, num);
    }

    public ArrayList<Integer> Get_Mark(int x, int y, int num) {
        ArrayList<Integer> res = new ArrayList<>();
        int tx, ty;
        boolean f;
        now = 0;
        for (int i = 0; i < 4; ++i) {
            tx = x + Rx[i];
            ty = y + Ry[i];
            if (!In(tx, ty))
                continue;
            if (In_Rever(tx, ty)) {
                if (num >> 1 == 1)
                    ;
                else if (num >> 1 == 6 || num >> 1 == 7) {
                    f = Check_M(tx, ty);
                    while (In_Rever(tx, ty)) {
                        f |= Check_M(tx, ty);
                        tx += Rx[i];
                        ty += Ry[i];
                    }
                    if (f)
                        continue;
                } else
                    continue;
            }
            // System.out.printf("???:%d %d\n", tx, ty);

            if (!isValidCapture(chess[num], grid[tx][ty].getPiece()))
                continue;

            if (tx * 7 + ty == 3 && (num & 1) == 0)
                continue;
            if (tx * 7 + ty == 3 + 7 * 8 && (num & 1) == 1)
                continue;
            // System.out.printf("???:%d %d %d\n", tx, ty, num);
            Mk[tx * 7 + ty] = 1;
            Now[++now] = tx * 7 + ty;
            res.add(tx * 7 + ty);
        }
        return res;
    }

    public void Erase_Mark() {
        for (int i = 1; i <= now; ++i)
            Mk[Now[i]] = 0;
    }

    public void initPieces() {
        grid[2][6].setPiece(chess[16] = new ChessPiece(PlayerColor.BLUE, "Elephant", 8, 2, 6, 16));
        grid[0][0].setPiece(chess[14] = new ChessPiece(PlayerColor.BLUE, "Lion", 7, 0, 0, 14));
        grid[0][6].setPiece(chess[12] = new ChessPiece(PlayerColor.BLUE, "Tiger", 6, 0, 6, 12));
        grid[2][2].setPiece(chess[10] = new ChessPiece(PlayerColor.BLUE, "Leopard", 5, 2, 2, 10));
        grid[2][4].setPiece(chess[8] = new ChessPiece(PlayerColor.BLUE, "Wolf", 4, 2, 4, 8));
        grid[1][1].setPiece(chess[6] = new ChessPiece(PlayerColor.BLUE, "Dog", 3, 1, 1, 6));
        grid[1][5].setPiece(chess[4] = new ChessPiece(PlayerColor.BLUE, "Cat", 2, 1, 5, 4));
        grid[2][0].setPiece(chess[2] = new ChessPiece(PlayerColor.BLUE, "Rat", 1, 2, 0, 2));

        grid[6][0].setPiece(chess[17] = new ChessPiece(PlayerColor.RED, "Elephant", 8, 6, 0, 17));
        grid[8][6].setPiece(chess[15] = new ChessPiece(PlayerColor.RED, "Lion", 7, 8, 6, 15));
        grid[8][0].setPiece(chess[13] = new ChessPiece(PlayerColor.RED, "Tiger", 6, 8, 0, 13));
        grid[6][4].setPiece(chess[11] = new ChessPiece(PlayerColor.RED, "Leopard", 5, 6, 4, 11));
        grid[6][2].setPiece(chess[9] = new ChessPiece(PlayerColor.RED, "Wolf", 4, 6, 2, 9));
        grid[7][5].setPiece(chess[7] = new ChessPiece(PlayerColor.RED, "Dog", 3, 7, 5, 7));
        grid[7][1].setPiece(chess[5] = new ChessPiece(PlayerColor.RED, "Cat", 2, 7, 1, 5));
        grid[6][6].setPiece(chess[3] = new ChessPiece(PlayerColor.RED, "Rat", 1, 6, 6, 3));
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    public void PrintMap() {
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 6; j++) {
                ChessPiece nowPiece = getChessPieceAt(new ChessboardPoint(i, j));
                if (nowPiece == null)
                    continue;
                System.out.println("(" + i + "," + j + ")" + nowPiece.getName() + " " + nowPiece.getOwner());
            }
        }
    }

    public void Erase(int type) {
        grid[To_x[top]][To_y[top]].removePiece();
        if (Id[top] != 0) {
            chess[Id[top]].setX(To_x[top]);
            chess[Id[top]].setY(To_y[top]);
            grid[To_x[top]][To_y[top]].setPiece(chess[Id[top]]);
        }
        chess[Num[top]].setX(Fr_x[top]);
        chess[Num[top]].setY(Fr_y[top]);
        grid[Fr_x[top]][Fr_y[top]].setPiece(chess[Num[top]]);
        if (type == 1) {
            ChessboardPoint To = new ChessboardPoint(To_x[top], To_y[top]);
            ChessboardPoint Fr = new ChessboardPoint(Fr_x[top], Fr_y[top]);
            GameController.view.removeChessComponentAtGrid(To);
            if (Id[top] != 0) {
                GameController.view.setChessComponentAtGrid(0, To, new ChessComponent(chess[Id[top]].getOwner(),
                        GameController.view.CHESS_SIZE, chess[Id[top]].getRank()));
            }
            GameController.view.setChessComponentAtGrid(0, Fr, new ChessComponent(chess[Num[top]].getOwner(),
                    GameController.view.CHESS_SIZE, chess[Num[top]].getRank()));
            GameController.view.repaint();
        }
        top--;
        // PrintMap();
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private ChessPiece tmp;

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        tmp = getGridAt(point).getPiece();
        if (tmp != null) {
            // Id[top] = tmp.getId();
            tmp.setX(-1);
            tmp.setY(-1);
        }
        getGridAt(point).setPiece(chessPiece);
        chessPiece.setX(point.getRow());
        chessPiece.setY(point.getCol());
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest, boolean log) {
        // PrintMap();
        // System.out.println("form to"+src+" "+dest);
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        if (log) {
            ++top;
            Fr_x[top] = src.getRow();
            Fr_y[top] = src.getCol();
            To_x[top] = dest.getRow();
            To_y[top] = dest.getCol();
            Num[top] = getChessPieceAt(src).getId();
            if (getChessPieceAt(dest) != null)
                Id[top] = getChessPieceAt(dest).getId();
            else
                Id[top] = 0;
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null) {
            System.out.println("From is null!");
            return false;
        }
        if (getChessPieceAt(dest) != null &&
                !isValidCapture(src, dest)) {
            System.out.println(getChessPieceAt(src).getRank() + " " + getChessPieceAt(src).getOwner() + "   "
                    + getChessPieceAt(dest).getRank() + " " + getChessPieceAt(dest).getOwner());
            System.out.println("Eat is unvalid!");
            return false;
        }
        // if (getChessPieceAt(src) == null || (getChessPieceAt(dest) != null &&
        // !isValidCapture(src, dest))) {
        // return false;
        // }
        return Mk[dest.getID()] == 1;
    }

    public boolean isValidCapture(ChessPiece src, ChessPiece dest) {
        if (dest == null)
            return true;
        return src.canCapture(dest);
    }

    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        return getChessPieceAt(src).canCapture(getChessPieceAt(dest));
    }

    public int win() {
        if (grid[0][3].getPiece() != null)
            return -1;
        if (grid[8][3].getPiece() != null)
            return 1;
        int a = 0, b = 0;
        for (int i = 1; i <= 8; ++i)
            if (chess[i << 1].getX() != -1)
                ++a;
        for (int i = 1; i <= 8; ++i)
            if (chess[i << 1 | 1].getX() != -1)
                ++b;
        if (a == 0)
            return -1;
        if (b == 0)
            return 1;
        return 0;
    }

    public boolean load(String way) throws IOException {
        if(Objects.equals(way, ""))return false;
        File infile = new File(way);
        Scanner In = new Scanner(infile, "UTF-8");
        try{
            int n = In.nextInt();
            for (int i = 1, s, e; i <= n; ++i) {
//            System.out.println(i+"#####");
                s = Num[i] = In.nextInt();
                Fr_x[i] = In.nextInt();
                Fr_y[i] = In.nextInt();
                To_x[i] = In.nextInt();
                To_y[i] = In.nextInt();
                e = Id[i] = In.nextInt();
//            System.out.println(Fr_x[i]+","+Fr_y[i]);
                if (Num[i] < 1 || Num[i] > 17 || Id[i] < 0 || Id[i] > 17) {
                    System.out.printf("The id of chess is invalid\n");
                    JOptionPane.showMessageDialog(null, "The id of chess is invalid", "Error", 0);
                    return false;
                } else if (chess[s].getX() != Fr_x[i] || chess[s].getY() != Fr_y[i]) {
                    System.out.printf("The place of chosen chess is invalid\n");
                    JOptionPane.showMessageDialog(null, "The place of chosen chess is invalid", "Error", 0);
                    return false;
                } else if (e!=0&&(chess[e].getX() != To_x[i] || chess[e].getY() != To_y[i])) {
                    System.out.printf("The place of captured chess is invalid\n");
                    JOptionPane.showMessageDialog(null, "The place of captured chess is invalid", "Error", 0);
                    return false;
                } else {
                    Get_Mark(Fr_x[i], Fr_y[i], s);
                    if (Mk[To_x[i] * 7 + To_y[i]] == 0 || (e != 0 && !chess[s].canCapture(chess[e]))) {
                        System.out.printf((e == 0 ? "Move" : "Capture") + " is invalid\n");
                        JOptionPane.showMessageDialog(null, (e == 0 ? "Move" : "Capture") + " is invalid", "Error", 0);
                        return false;
                    }
                }
                moveChessPiece(new ChessboardPoint(Fr_x[i],Fr_y[i]),new ChessboardPoint(To_x[i],To_y[i]),true);
                Erase_Mark();
                // TOMO:playback
            }
//        System.out.println("Load succeed!");
            In.close();
            if(GameController.AI>0&&n%2==1){
                JOptionPane.showMessageDialog(null, "This save is invalid for AI-playing", "Error", 0);
                return false;
            }
            return true;
        }catch (NoSuchElementException e){
            JOptionPane.showMessageDialog(null, "The input data is invalid", "Error", 0);
            return false;
        }
    }
    public void Save(String way) throws IOException {
        if(Objects.equals(way, ""))return;
        File outfile = new File(way);
        // PrintWriter Out = new PrintWriter(outfile, "UTF-8");
        PrintWriter Out = new PrintWriter(outfile, "UTF-8");
        Out.printf("%d\n", top);
        for (int i = 1; i <= top; ++i)
            Out.printf("%d %d %d %d %d %d\n", Num[i], Fr_x[i], Fr_y[i], To_x[i], To_y[i], Id[i]);
        Out.close();
    }
}
