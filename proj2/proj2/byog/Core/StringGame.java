package byog.Core;

import byog.Core.Object.Player;
import byog.TileEngine.TETile;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class StringGame {
    char[] charsInput;
    String input;
    WorldGenerator generator;
    Player player;
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    boolean isNew;

    public StringGame(String input) {
        this.input = input;
        this.charsInput = input.toCharArray();
    }

    private Character getFirstValidChar()
    {
        for(char c: charsInput)
        {
            if(c == 'n' || c == 'N' || c == 'l' || c == 'L' || c == 'q' || c == 'Q'){
                return c;
            }
        }
        return null;
    }


    public TETile[][] getFinalWorldFrame()
    {
        char firstValidChar = getFirstValidChar();
        switch (firstValidChar)
        {
            case 'N':
            case 'n':
                isNew = true;
                startNewGame();
                return playGame();
            case 'L':
            case 'l':
                isNew = false;
                loadGame();
                return playGame();
            case 'Q':
            case 'q':
                quitGame();
                break;
        }
        return null;
    }

    private TETile[][] playGame()
    {
        boolean colonPressed = false;
        char[] playSequence;
        if(isNew) {
            playSequence = getPlaySequence();
        }else{
            playSequence = getLoadPlaySequence();
        }
        for(char nextKey: playSequence){
            if((nextKey == 'q' || nextKey == 'Q') && colonPressed)
            {
                saveGame();
                return generator.world;
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
        }
        return generator.world;
    }

    private char[] getPlaySequence()
    {
        int playSequenceStart = StringInputHandling.seedEndIndex(input) + 1;
        return input.substring(playSequenceStart, input.length()).toCharArray();
    }

    private char[] getLoadPlaySequence()
    {
        int IndexOfL = input.indexOf("L");
        int IndexOfl = input.indexOf("l");
        int playSequenceStart;
        if(IndexOfL == -1 || IndexOfl == -1) {
            playSequenceStart =  max(IndexOfL, IndexOfl) + 1;
        } else{
            playSequenceStart =  min(IndexOfL, IndexOfl) + 1;
        }
        return input.substring(playSequenceStart, input.length()).toCharArray();
    }

    private void startNewGame()
    {
        long seed = StringInputHandling.getSeed(input);
        this.generator = new WorldGenerator(seed, WIDTH, HEIGHT);
        this.player = generator.player;
    }

    private void quitGame()
    {
        System.exit(0);
    }

    private void loadGame()
    {
        initializeSavedGame();
    }

    private void initializeSavedGame()
    {
        this.generator = SaveAndLoad.loadWorld();
        this.player = generator.player;
    }

    private void saveGame()
    {
        SaveAndLoad.saveWorld(generator);
    }
}
