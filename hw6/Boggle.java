import java.util.*;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "words.txt";

    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {

        // Validate k
        if (k <= 0){
            throw new IllegalArgumentException("k should be an integer that greater than 0.");
        }
        WordTrieSet set = readDict(dictPath);
        HashSet<String> allWords = new HashSet<>();
        char[][] board = readBoard(boardFilePath);
        for (int i = 0; i < board.length; i += 1){
            for (int j = 0; j < board[0].length; j += 1){
                allWords.addAll(search(new Index(i, j), set, board));
            }
        }
        List<String> listAllWords = new ArrayList<>(allWords);
        listAllWords.sort(new StringLengthComparator());
        List<String> solution = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            solution.add(listAllWords.get(i));
        }
        return solution;
    }

    protected static WordTrieSet readDict(String dictPath)
    {
        In in = new In(dictPath);
        WordTrieSet set = new WordTrieSet();
        while (in.hasNextLine()) {
            String word = in.readLine();
            set.put(word);
        }
        return set;
    }

    protected static char[][] readBoard(String boardFilePath)
    {
        In in = new In(boardFilePath);
        String[] lines = in.readAllLines();
        int rows = lines.length;
        int columns = lines[0].length();

        // Validate if all lines are of the same length.
        for (String line: lines) {
            if (line.length() != columns){
                throw new IllegalArgumentException("The board is not a square.");
            }
        }

        char[][] board = new char[rows][columns];
        for (int i = 0; i < rows; i += 1){
            board[i] = lines[i].toCharArray();
        }
        return board;
    }

    /** Search and return all a hashset that contains all possible strings start at the given index.*/
    protected static HashSet<String> search(Index index, WordTrieSet set, char[][] board)
    {
        return null;
    }

    /** Get the indices which are the neighbours of the given index in the board.
     * The indices are in the form of int arrays of length 2, the first element
     * is the x index and the second element is the y index.*/
    protected static HashSet<Index> getNeighbours(char[][] board, Index index)
    {
        int boardHeight = board.length, boardWidth = board[0].length;
        int x = index.x, y = index.y;
        if (x >= boardWidth || x < 0 || y >= boardHeight || y < 0){
            throw new IllegalArgumentException("Index (" + x + ", " + y + ") is out of the size of the board.");
        }
        HashSet<Index> neighbours = new HashSet<>();
        if (x != boardWidth - 1){
            neighbours.add(new Index(x + 1, y));
        }
        if (x != 0){
            neighbours.add(new Index(x - 1, y));
        }
        if (y != boardHeight - 1){
            neighbours.add(new Index(x, y + 1));
        }
        if (y != 0){
            neighbours.add(new Index(x , y - 1));
        }
        if (x != 0 && y != 0){
            neighbours.add(new Index(x - 1, y - 1));
        }
        if (x != 0 && y != boardHeight - 1){
            neighbours.add(new Index(x - 1, y + 1));
        }
        if (x != boardWidth - 1 && y != 0){
            neighbours.add(new Index(x + 1, y - 1));
        }
        if (x != boardWidth - 1 && y != boardHeight - 1){
            neighbours.add(new Index(x + 1, y + 1));
        }
        return neighbours;
    }


    public static void main(String[] args)
    {
        HashSet<Index> set = new HashSet<>();
        set.add(new Index(0, 0));
        System.out.println(set.contains(new Index(0, 0)));
    }

    protected static class Index
    {
        public int x, y;
        public Index(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o)
        {
            if (o == null) return false;
            if (o.getClass() != this.getClass()) return false;
            Index i = (Index) o;
            return this.x == i.x && this.y == i.y;
        }
    }
}
