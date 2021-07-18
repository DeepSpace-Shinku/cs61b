import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static org.junit.Assert.*;

public class TestBoggle {
    @Test
    public void testSolve()
    {
        int k = 7;
        String boardFilePath = "exampleBoard.txt";
        List<String> solution = Boggle.solve(k, boardFilePath);
        assertEquals(k, solution.size());
        assertTrue(solution.contains("thumbtacks"));
        assertTrue(solution.contains("thumbtack"));
        assertTrue(solution.contains("setbacks"));
        assertTrue(solution.contains("setback"));
        assertTrue(solution.contains("ascent"));
        assertTrue(solution.contains("humane"));
        assertTrue(solution.contains("smacks"));
    }

    @Test
    public void testReadDict(){
        WordTrieSet s =  Boggle.readDict("words.txt");
        assertTrue(s.contains("AIDS's"));
        assertFalse(s.contains("ALLU"));
    }

    @Test
    public void testReadBoard()
    {
        char[][] board = Boggle.readBoard("exampleBoard2.txt");
        String expected = "baa" + "aba" + "aab" + "baa";
        for (int i = 0; i < 4; i += 1){
            for (int j = 0; j < 3; j +=1){
                assertEquals(expected.charAt(3 * i + j), board[i][j]);
            }
        }
    }

    @Test
    public void testSearch()
    {
        char[][] exampleBoard2 = Boggle.readBoard("exampleBoard2.txt");
        WordTrieSet set = Boggle.readDict("trivial_words.txt");
        HashSet<String> solution = Boggle.search(new Boggle.Index(0, 0), set, exampleBoard2);
        assertTrue(solution.size() == 0);
        solution = Boggle.search(new Boggle.Index(0, 1), set, exampleBoard2);
        assertTrue(solution.size() == 2);
        assertTrue(solution.contains("aaaa"));
        assertTrue(solution.contains("aaaaa"));
    }

    @Test
    public void testGetNeighbours()
    {
        char[][] board = Boggle.readBoard("exampleBoard2.txt");
        HashSet<Boggle.Index> neighbours = Boggle.getNeighbours(board, new Boggle.Index(0, 0));
        assertTrue(neighbours.size() == 3);
        for (Boggle.Index neighbour: neighbours) {
            System.out.println(neighbour.x + " " + neighbour.y);
        }
        System.out.println();
        neighbours = Boggle.getNeighbours(board, new Boggle.Index(0, 1));
        assertTrue(neighbours.size() == 5);
        for (Boggle.Index neighbour: neighbours) {
            System.out.println(neighbour.x + " " + neighbour.y);
        }
        System.out.println();
        neighbours = Boggle.getNeighbours(board, new Boggle.Index(1, 1));
        assertTrue(neighbours.size() == 8);
        for (Boggle.Index neighbour: neighbours) {
            System.out.println(neighbour.x + " " + neighbour.y);
        }
    }
}
