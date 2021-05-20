package creatures;

import huglife.Action;
import huglife.Direction;
import huglife.HugLifeUtils;
import huglife.Occupant;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {

    protected final Color color = new Color(34, 0, 231);
    protected final double remainFactor = 0.5;

    public Clorus(double energy){
        super("colrus");
        this.energy = energy;
    }
    @Override
    public void move() {
        this.reduceEnergy(0.03);
    }

    @Override
    public void attack(Creature c) {
        this.energy += c.energy();
        c.reduceEnergy(c.energy());
    }

    @Override
    public Clorus replicate() {
        Clorus child = new Clorus((1 - remainFactor) * this.energy);
        this.energy *= remainFactor;
        return child;
    }

    @Override
    public void stay() {
        this.reduceEnergy(0.01);
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        java.util.List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if (empties.isEmpty()){
            return new Action(Action.ActionType.STAY);
        }else{
            List<Direction> plips = getNeighborsOfType(neighbors, "plip");
            if (!plips.isEmpty()){
                Direction moveDir = HugLifeUtils.randomEntry(plips);
                return new Action(Action.ActionType.ATTACK, moveDir);
            }else{
                Direction moveDir = HugLifeUtils.randomEntry(empties);
                if (energy() >= 1){
                    return new Action(Action.ActionType.REPLICATE, moveDir);
                }else{
                    return new Action(Action.ActionType.MOVE, moveDir);
                }
            }
        }
    }

    @Override
    public Color color() {
        return new Color(34, 0, 231);
    }
}
