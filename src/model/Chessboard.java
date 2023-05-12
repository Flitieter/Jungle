package model;

import java.io.File;
// import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
// import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    private ChessPiece[] chess = new ChessPiece[20];
    private int[][][] Link = new int[10][102][102];
    private int top, now;
    private int[] Fr_x = new int[10004], Fr_y = new int[10004], To_x = new int[10004], To_y = new int[10004];
    private int[] Id = new int[10004], Num = new int[10004];
    private int Rx[] = { -1, 1, 0, 0 };
    private int Ry[] = { 0, 0, -1, 1 };
    private int[] Mk = new int[1002], Now = new int[5];

    public Chessboard() {
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];// 19X19
        initGrid();
        initPieces();
    }

    private void initGrid() {
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
        ArrayList<Integer> res = new ArrayList<>();
        int x = point.getRow(), y = point.getCol();
        int tx, ty;
        int num = getChessPieceAt(point).getId();
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
            System.out.printf("???:%d %d\n", tx, ty);

            if (!isValidCapture(chess[num], grid[tx][ty].getPiece()))
                continue;

            if (tx * 7 + ty == 3 && (num & 1) == 0)
                continue;
            if (tx * 7 + ty == 3 + 7 * 8 && (num & 1) == 1)
                continue;
            System.out.printf("???:%d %d %d\n", tx, ty, num);
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

    private void initPieces() {
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

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    public void Erase() {
        grid[To_x[top]][To_y[top]].removePiece();
        if (Id[top] != 0)
            grid[To_x[top]][To_y[top]].setPiece(chess[Id[top]]);
        grid[Fr_x[top]][Fr_y[top]].setPiece(chess[Num[top]]);
        top--;
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
            Id[top] = tmp.getId();
            tmp.setX(-1);
            tmp.setY(-1);
        }
        getGridAt(point).setPiece(chessPiece);
        chessPiece.setX(point.getRow());
        chessPiece.setY(point.getCol());
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        System.out.println("form to"+src+" "+dest);
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        ++top;
        Fr_x[top] = src.getRow();
        Fr_y[top] = src.getCol();
        To_x[top] = dest.getRow();
        To_y[top] = dest.getCol();
        Num[top] = getChessPieceAt(src).getId();
        if (getChessPieceAt(dest) != null)
            Id[top] = getChessPieceAt(dest).getId();
        setChessPiece(dest, removeChessPiece(src));
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || (getChessPieceAt(dest) != null &&
                !isValidCapture(src, dest))) {
            return false;
        }
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

    public void Save() throws IOException {
        File outfile = new File("1.txt");

        // PrintWriter Out = new PrintWriter(outfile, "UTF-8");
        PrintWriter Out = new PrintWriter(outfile, "UTF-8");
        for (int i = 2; i <= 17; ++i)
            Out.printf("%d %d %d\n", i, chess[i].getX(), chess[i].getY());
        Out.printf("%d\n", top);
        for (int i = 1; i <= top; ++i)
            Out.printf("%d %d %d %d %d %d\n", Num[i], Fr_x[i], Fr_y[i], To_x[i], Fr_y[i], Id[i]);
        Out.close();
    }
}