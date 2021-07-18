package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;
import org.w3c.dom.ranges.Range;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private long seed;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
        // game.flashSequence("你好啊");
    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        this.seed = seed;
        this.rand = new Random(seed);

        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String string = "";
        for(int i = 0; i < n; i++){
            char randChar = (char)((int)'a' + rand.nextInt(26));
            string += randChar;
        }
        return string;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear();
        StdDraw.clear(Color.BLACK);

        int horizontalCenter = width / 2;
        int verticalCenter = height / 2;

        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.RED);
        StdDraw.text(horizontalCenter, verticalCenter, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        StdDraw.clear(Color.BLACK);
        char[] lettersArray = letters.toCharArray();
        for (char letter: lettersArray){
            drawFrame("" + letter);
            StdDraw.pause(500);
            StdDraw.clear(Color.BLACK);
            StdDraw.show();
            StdDraw.pause(500);
        }

    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String inputString = "";
        int inputNumber = 0;
        while(true){
            if(StdDraw.hasNextKeyTyped()){
                inputString += StdDraw.nextKeyTyped();
                inputNumber += 1;
            }
            if (inputNumber == n){
                break;
            }
        }
        return inputString;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        //TODO: Establish Game loop
        String randString = "", inputString = "";
        for(int i = 1; randString.equals(inputString); i ++){
            randString = generateRandomString(i);
            flashSequence(randString);
            inputString = solicitNCharsInput(i);
        }
        drawFrame("Game Over!");
    }

}
