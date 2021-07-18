package byog.Core.Object;

import java.io.Serializable;
import java.util.Random;

import static java.lang.Math.abs;

public class NumberGenerator implements Serializable{
    /**
     * Using the given seed to generate a number serial.
     */
    Random rand;

    public NumberGenerator(long seed)
    {
       rand = new Random(seed);
    }


    public int next(int max)
    {
       return rand.nextInt(max);
    }

    public int next(int min, int max)
    {
        return rand.nextInt(max - min) + min;
    }
}


