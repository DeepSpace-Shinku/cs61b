package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import static org.junit.Assert.*;


public class Percolation {
    private int N;
    private int numOfOpenSites;
    WeightedQuickUnionUF set;
    WeightedQuickUnionUF dualSet;
    boolean opened[][];

    public Percolation(int N)
    {
        this.N = N;
        // let the last node be the bottom node and the one before last be the top node.
        this.set = new WeightedQuickUnionUF(N * N + 2);
        this.dualSet = new WeightedQuickUnionUF(N * N + 1);
        opened = new boolean[N][N];
        numOfOpenSites = 0;
    }

    public void open(int row, int col)
    {
        opened[row][col] = true;
        numOfOpenSites += 1;
        connectAllOpenedAround(row, col);
        if (row == 0){
            connectTopToFirstLevel(col);
        }
        if (row == N - 1){
            connectBottomToLastLevel(col);
        }

    }

    public boolean isOpen(int row, int col)
    {
        return opened[row][col];
    }

    public boolean isFull(int row, int col)
    {
        return dualSet.connected(topIndex(), xyTo1D(row, col)) && opened[row][col];
    }

    public int numberOfOpenSites()
    {
        return numOfOpenSites;
    }

    public boolean percolates()
    {
        return set.connected(topIndex(), bottomIndex());
    }

    private int xyTo1D(int x, int y)
    {
        return x + y * N;
    }

    private void connectAllOpenedAround(int row, int col)
    {
        connectIfOpened(row, col, row - 1, col);
        connectIfOpened(row, col, row, col - 1);
        connectIfOpened(row, col, row + 1, col);
        connectIfOpened(row, col, row, col + 1);
    }

    private void connectIfOpened(int thisRow, int thisCol, int otherRow, int otherCol)
    {
        if (otherRow >= 0 && otherRow < N && otherCol >= 0 && otherCol < N && opened[otherRow][otherCol]){
            set.union(xyTo1D(thisRow, thisCol), xyTo1D(otherRow, otherCol));
            dualSet.union(xyTo1D(thisRow, thisCol), xyTo1D(otherRow, otherCol));
        }
    }

    /**
     * Return the 1D index of the top Node
     */
    private int topIndex()
    {
        return xyTo1D(N - 1, N - 1) + 1;
    }

    /**
     * Return the 1D index of the bottom Node
     */
    private int bottomIndex()
    {
        return xyTo1D(N - 1, N - 1) + 2;
    }


    private void connectTopToFirstLevel(int col)
    {
        set.union(topIndex(), xyTo1D(0, col));
        dualSet.union(topIndex(), xyTo1D(0, col));
    }

    private void connectBottomToLastLevel(int col)
    {
        set.union(bottomIndex(), xyTo1D(N - 1, col));
    }

    public static void main(String[] args)
    {
        Percolation p = new Percolation(10);
        assertFalse(p.percolates());
        assertEquals(0, p.numberOfOpenSites());
        p.open(3, 4);
        assertFalse(p.percolates());
        assertEquals(1, p.numberOfOpenSites());
        assertTrue(p.isOpen(3, 4));
        p.open(0,0);
        assertFalse(p.percolates());
        assertEquals(2, p.numberOfOpenSites());
        p.open(0, 1);
        assertFalse(p.percolates());
        assertEquals(3, p.numberOfOpenSites());
        assertTrue(p.set.connected(p.xyTo1D(0, 0), p.xyTo1D(0, 1)));
        assertTrue(p.isFull(0, 0));
        assertTrue(p.isFull(0, 1));
        assertFalse(p.isFull(1, 1));
        p.open(1, 1);
        assertFalse(p.percolates());
        assertEquals(4, p.numberOfOpenSites());
        assertTrue(p.isFull(1, 1));
        assertFalse(p.isFull(9,7));
        for (int i = 0; i < 9; i++) {
            p.open(i, 5);
            assertFalse(p.percolates());
        }
        p.open(9, 5);
        assertTrue(p.percolates());
        assertFalse(p.isFull(9,7));
        System.out.println(20 / 5);
        System.out.println(20 % 5);
    }
}
