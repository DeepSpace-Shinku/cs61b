package byog.Core;

import byog.Core.Object.Player;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

import static byog.Core.StringInputHandling.*;

public class Game {


    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    private static final int midWidth = WIDTH/ 2;
    private static final int midHeight = HEIGHT / 2;
    private TERenderer ter;
    private TETile[][] world;
    private Player player;
    private WorldGenerator generator;


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        showMainMenu();
        startGame();
    }

    private void showMainMenu()
    {
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        String start = "Start(n)", load = "Load(l)", quit = "Quit(q)";
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        // Draw the actual text
        Font bigFont = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(midWidth, midHeight + 3, start);
        StdDraw.text(midWidth, midHeight, load);
        StdDraw.text(midWidth, midHeight - 3, quit);
        StdDraw.show();
    }

    private void startGame()
    {
        while(true) {
            char nextKey = getNextKeyPressed();
            mainMenuSwitchKey(nextKey);
        }
    }

    private void quitGame()
    {
        System.exit(0);
    }

    public void startNewGame()
    {
        initializeNewGame();
        playGame();
    }

    private void initializeNewGame()
    {
        long seed = seedGetter();
        this.generator = new WorldGenerator(seed, WIDTH, HEIGHT);
        this.world = generator.world;
        this.player = generator.player;
        ter.renderFrame(world);
    }

    private void initializeSavedGame()
    {
        this.generator = SaveAndLoad.loadWorld();
        this.world = generator.world;
        this.player = generator.player;
        ter.renderFrame(world);
    }

    private void mainMenuSwitchKey(char key)
    {
        switch (key){
            case 'N':
            case 'n':
                startNewGame();
                break;
            case 'L':
            case 'l':
                loadGame();
                break;
            case 'Q':
            case 'q':
                quitGame();
                break;
        }
    }

    private void playGame()
    {
        boolean colonPressed = false;
        while(true){
            char nextKey = getNextKeyPressed();
            if((nextKey == 'q' || nextKey == 'Q') && colonPressed){
                saveGame();
                quitGame();
            }
            if(nextKey == ':'){
                colonPressed = true;
            }else{
                colonPressed = false;
            }
            switch (nextKey){
                case 'w':
                case 'W':
                    player.move("up");
                    break;
                case 's':
                case 'S':
                    player.move("down");
                    break;
                case 'a':
                case 'A':
                    player.move("left");
                    break;
                case 'd':
                case 'D':
                    player.move("right");
                    break;
            }
            ter.renderFrame(world);
        }
    }


    private long seedGetter()
    {
        String seed = "";
        showSeedFrame(seed);
        char nextKey = getNextKeyPressed();
        while(nextKey != 's' && nextKey != 'S'){
            seed += nextKey;
            showSeedFrame(seed);
            nextKey = getNextKeyPressed();
        }
        return Long.parseLong(seed);
    }

    private void showSeedFrame(String seed)
    {
        String prompt = "Please enter the seed:";
        StdDraw.clear(Color.BLACK);
        StdDraw.text(midWidth, midHeight + 2, prompt);
        StdDraw.text(midWidth, midHeight - 2, seed);
        StdDraw.show();
    }

    private void loadGame()
    {
        initializeSavedGame();
        playGame();
    }



    private void saveGame()
    {
        SaveAndLoad.saveWorld(generator);
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        StringGame stringGame = new StringGame(input);
        TETile[][] finalWorldFrame = stringGame.getFinalWorldFrame();
        return finalWorldFrame;
    }

    public static char getNextKeyPressed() {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                return StdDraw.nextKeyTyped();
            }
        }
    }
}
