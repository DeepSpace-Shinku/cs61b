package byog.Core;
import static java.lang.Math.*;

public class InputHandling {
    public static int getSeed(String input)
    {
        int endIndex = seedEndIndex(input);
        assert (input.charAt(0) == 'N' || input.charAt(0) == 'n') && endIndex > 1: "Bad input";
        return Integer.parseInt(input.substring(1, endIndex));
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
