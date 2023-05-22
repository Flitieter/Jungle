package model;

// import java.security.PublicKey;

import java.io.Serializable;

public class ChessPiece implements Serializable {

    private PlayerColor owner;

    private String name;
    private int rank;
    private int id;
    private int x;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    private int y;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ChessPiece(PlayerColor owner, String name, int rank, int x, int y, int id) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
        this.x = x;
        this.y = y;
        this.id = id;
    }
    public void setId(int id){
        this.id=id;
    }
    public int getRank() {
        return rank;
    }

    public boolean canCapture(ChessPiece target) {
        if (owner == PlayerColor.RED && ((x == 0 && (y == 2 || y == 4))
                || (x == 1 && y == 3)))
            return false;
        if (owner == PlayerColor.BLUE && ((x == 8 && (y == 2 || y == 4))
                || (x == 7 && y == 3)))
            return false;
        if (target.getOwner() == owner)
            return false;
        if (target.getOwner() == PlayerColor.RED && ((target.getX() == 0 && (target.getY() == 2 || target.getY() == 4))
                || (target.getX() == 1 && target.getY() == 3)))
            return true;
        if (target.getOwner() == PlayerColor.BLUE && ((target.getX() == 8 && (target.getY() == 2 || target.getY() == 4)
                || (target.getX() == 7 && target.getY() == 3))))
            return true;
//        System.out.println("?????");
        int r = target.GetRank();
        if (rank == 1 && r == 8){
//            System.out.println(!((3 <= x && x <= 5) && ((1 <= y && y <= 2) || (4 <= y && y <= 5))));
//            System.out.println(x+","+y);
            return !((3 <= x && x <= 5) && ((1 <= y && y <= 2) || (4 <= y && y <= 5)));
        }
        if (rank == 8 && r == 1){
            return false;
        }
        return rank >= r;
    }


    public String getName() {
        return name;
    }

    public int GetRank() {
        return rank;
    }

    public int getId() {
        return id;
    }

    public PlayerColor getOwner() {
        return owner;
    }
}
