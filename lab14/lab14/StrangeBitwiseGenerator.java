package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    int period;
    private int phase;
    final static int MIN = -1, MAX = 1;
    public StrangeBitwiseGenerator(int period){
        this.period = period;
        this.phase = 0;
    }

    @Override
    public double next() {
        int oldPhase = phase;
        phase += 1;
        int weirdPhase = oldPhase & (oldPhase >>> 3) % period;
        weirdPhase = oldPhase & (oldPhase >> 3) & (oldPhase >> 8) % period;
        weirdPhase = oldPhase & (oldPhase >> 7) % period;
        if (phase >= period){
            //normalize();
        }
        return ((double) weirdPhase / period) * (MAX - MIN) + MIN;
    }

    void normalize()
    {
        phase -= period;
    }
}
