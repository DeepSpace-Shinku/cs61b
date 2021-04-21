package hw4.puzzle;


import edu.princeton.cs.algs4.Queue;
import static org.junit.Assert.*;

public class Board implements WorldState{
    private int[][] board;
    private final int BLANK = 0;

    public Board(int[][] x) {
        if (x.length != x[0].length) {
            throw new IllegalArgumentException("The length and the width should be equal.");
        }
        this.board = copy(x);
    }

    private static int[][] copy(int[][] x)
    {
        int[][] result = new int[x.length][x.length];
        for (int i = 0; i < x.length; i++){
            for (int j = 0; j < x.length; j++){
                result[i][j] = x[i][j];
            }
        }
        return result;
    }

    public int tileAt(int x, int y){
        if (!(x < size() && y < size() && x >= 0 && y >= 0)) {
            throw new IndexOutOfBoundsException("x and y should between 0 and N - 1");
        }
        return board[x][y];
    }

    public int size()
    {
        return board.length;
    }

    public int hamming()
    {
        int hammingNumber = 0;
        for (int x = 0; x < size(); x++){
            for (int y = 0; y < size(); y++){
                if (tileAt(x, y) != BLANK && this.tileAt(x, y) != xyTo1D(x, y)) hammingNumber += 1;
            }
        }
        return hammingNumber;
    }

    public int manhattan()
    {
        int mahattanNumber = 0;
        for (int x = 0; x < size(); x++){
            for (int y = 0; y < size(); y++){
                mahattanNumber += xyToManhattanError(x, y);
            }
        }
        return mahattanNumber;
    }

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        Board b = (Board) o;
        if (b.size() != this.size()) return false;
        for (int x = 0; x < size(); x++){
            for (int y = 0; y < size(); y++){
                if (this.tileAt(x, y) != b.tileAt(x, y)) return false;
            }
        }
        return true;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    private int xyTo1D(int x, int y)
    {
        if (x == size() - 1 && y == size() - 1) return BLANK;
        return x * size() + y + 1;
    }

    private int oneDToX(int oneD)
    {
        return (oneD - 1) / size();
    }

    private int oneDToY(int oneD)
    {
        return (oneD - 1) % size();
    }

    private static int oDToX(int oneD)
    {
        return (oneD - 1) / 3;
    }

    private static int oDToY(int oneD)
    {
        return (oneD - 1) % 3;
    }

    private int xyToManhattanError(int x, int y)
    {
        int tile = tileAt(x, y);
        if (tile == BLANK) return 0;
        return Math.abs(x - oneDToX(tile)) + Math.abs(y - oneDToY(tile));
    }


    /**
     * Returns neighbors of this board.
     * SPOILERZ: This is the answer.
     * @Source:http://joshh.ug/neighbors.html
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }



    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    @Override
    public int hashCode()
    {
        return 0;
    }
    

}
