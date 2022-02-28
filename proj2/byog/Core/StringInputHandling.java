package byog.Core;
import java.io.Serializable;

import static java.lang.Math.*;

public class StringInputHandling implements Serializable {

    public static char getFirstValidChar(String input)
    {
        return 'a';
    }

    public static long getSeed(String input)
    {
        int endIndex = seedEndIndex(input);
        int beginIndex = seedBeginIndex(input);
        if (!(input.charAt(0) == 'N' || input.charAt(0) == 'n')){
            return Long.parseLong(input);
        }
        return Long.parseLong(input.substring(beginIndex, endIndex));
    }

    public static int seedEndIndex(String input)
    {
        int IndexOfS = input.indexOf("S");
        int IndexOfs = input.indexOf("s");
        if(IndexOfS == -1 || IndexOfs == -1) {
            return max(IndexOfS, IndexOfs);
        } else{
            return min(IndexOfS, IndexOfs);
        }
    }

    public static int seedBeginIndex(String input)
    {
        int IndexOfS = input.indexOf("N");
        int IndexOfs = input.indexOf("n");
        if(IndexOfS == -1 || IndexOfs == -1) {
            return max(IndexOfS, IndexOfs) + 1;
        } else{
            return min(IndexOfS, IndexOfs) + 1;
        }
    }
}
