package byog.Core;
import static java.lang.Math.*;

public class InputHandling {
    public static long getSeed(String input)
    {
        int endIndex = seedEndIndex(input);
        if (!(input.charAt(0) == 'N' || input.charAt(0) == 'n')){
            return Long.parseLong(input);
        }
        return Long.parseLong(input.substring(1, endIndex));
    }

    public static int seedEndIndex(String input)
    {
        int IndexOfS = input.indexOf("S");
        int IndexOfs = input.indexOf("s");
        if(IndexOfS == -1|| IndexOfs == -1) {
            return max(IndexOfS, IndexOfs);
        } else{
            return min(IndexOfS, IndexOfs);
        }
    }
}
