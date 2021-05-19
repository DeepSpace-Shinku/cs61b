package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    int period;
    private int phase;
    final static int MIN = -1, MAX = 1;
    public SawToothGenerator(int period){
        this.period = period;
        this.phase = 0;
    }

    @Override
    public double next() {
      int oldPhase = phase;
      phase += 1;
      if (phase >= period){
          normalize();
      }
      return ((double) oldPhase / period) * (MAX - MIN) + MIN;
    }

    void normalize()
    {
        phase -= period;
    }
}
