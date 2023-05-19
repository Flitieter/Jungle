package view;

import controller.GameController;
import model.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
// import java.lang.reflect.Array;
// import java.util.ArrayList;
// import java.util.HashMap;
import java.util.HashSet;
// import java.util.Map;
import java.util.Set;



/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent implements Serializable {
    private final CellComponent[][] gridComponents = new CellComponent[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE
            .getNum()];
    public final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> Traps = new HashSet<>();
    private final Set<ChessboardPoint> Dens = new HashSet<>();
    private int now_color=0, limit = 1;
    private GameController gameController;
    private Color[][] Pre_color = { { Color.GRAY, Color.YELLOW, Color.PINK, Color.GREEN } ,{ Color.RED, Color.MAGENTA, Color.ORANGE, Color.DARK_GRAY }};
    private Color[][] Init_color = new Color[20][20];

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);

        initiateGridComponents();
    }

    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO:Implement the initialization checkerboard
                gridComponents[i][j].removeAll();
                if (grid[i][j].getPiece() != null) {
                    System.out.println(i+" "+j);
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    System.out.println(chessPiece.getOwner());
                    gridComponents[i][j].add(
                            new ChessComponent(
                                    chessPiece.getOwner(),
                                    CHESS_SIZE, chessPiece.GetRank()));
                }
            }
        }
    }

    public void initiateGridComponents() {

        riverCell.add(new ChessboardPoint(3, 1));
        riverCell.add(new ChessboardPoint(3, 2));
        riverCell.add(new ChessboardPoint(4, 1));
        riverCell.add(new ChessboardPoint(4, 2));
        riverCell.add(new ChessboardPoint(5, 1));
        riverCell.add(new ChessboardPoint(5, 2));

        riverCell.add(new ChessboardPoint(3, 4));
        riverCell.add(new ChessboardPoint(3, 5));
        riverCell.add(new ChessboardPoint(4, 4));
        riverCell.add(new ChessboardPoint(4, 5));
        riverCell.add(new ChessboardPoint(5, 4));
        riverCell.add(new ChessboardPoint(5, 5));

        Dens.add(new ChessboardPoint(0, 3));
        Dens.add(new ChessboardPoint(8, 3));

        Traps.add(new ChessboardPoint(0, 2));
        Traps.add(new ChessboardPoint(1, 3));
        Traps.add(new ChessboardPoint(0, 4));
        Traps.add(new ChessboardPoint(8, 2));
        Traps.add(new ChessboardPoint(7, 3));
        Traps.add(new ChessboardPoint(8, 4));
        SetColor();
    }
    public void ChangeColor(){
        now_color^=1;
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                Color tmp;
                if (riverCell.contains(temp)) {
                    tmp = Color.CYAN;
                } else if (Traps.contains(temp)) {
                    tmp = Pre_color[now_color][0];
                } else if (Dens.contains(temp)) {
                    tmp = Pre_color[now_color][1];
                } else {
                    tmp = Pre_color[now_color][2];
                }
                Init_color[i][j] = tmp;
                gridComponents[i][j].setColor(tmp);
            }
        }
        repaint();
    }
    void SetColor(){
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j <  Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                Color tmp;
                if (riverCell.contains(temp)) {
                    tmp = Color.CYAN;
                } else if (Traps.contains(temp)) {
                    tmp = Pre_color[now_color][0];
                } else if (Dens.contains(temp)) {
                    tmp = Pre_color[now_color][1];
                } else {
                    tmp = Pre_color[now_color][2];
                }
                Init_color[i][j] = tmp;
                cell = new CellComponent(tmp, calculatePoint(i, j), CHESS_SIZE);
                this.add(cell);
                gridComponents[i][j] = cell;
            }
        }
    }

    public void Get_Mark(ArrayList<Integer> now) {
        int x, y;
        for (int num : now) {
            x = num / 7;
            y = num % 7;
            gridComponents[x][y].setColor(Pre_color[now_color][3]);
        }
        repaint();
    }

    public void Erase_Mark(ArrayList<Integer> now) {
        int x, y;
        for (int num : now) {
            x = num / 7;
            y = num % 7;
//            System.out.printf("%d %d\n", x, y);
            gridComponents[x][y].setColor(Init_color[x][y]);
        }
        repaint();
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(int kind, ChessboardPoint point, ChessComponent chess) {
        if (kind == 1)
            getGridComponentAt(point).remove(0);
        getGridComponentAt(point).add(chess);
    }

    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    private CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y / CHESS_SIZE + ", " + point.x / CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y / CHESS_SIZE, point.x / CHESS_SIZE);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    MusicPlayer Click=new MusicPlayer();
    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            try {
                Click.playClick("resource\\click.wav");
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                try {
                    gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                System.out.print("One chess here and ");
                try {
                    gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()),
                            (ChessComponent) clickedComponent.getComponents()[0]);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
