package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator extends SawToothGenerator {

    private double acceleration;
    public AcceleratingSawToothGenerator(int period, double acceleration) {
        super(period);
        this.acceleration = acceleration;
    }

    @Override
    void normalize()
    {
        super.normalize();
        period = (int)(period * acceleration);
    }
}
