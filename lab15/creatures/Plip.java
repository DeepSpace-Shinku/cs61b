package creatures;
import huglife.*;

import java.awt.Color;
import java.util.List;
import java.util.Map;

/** An implementation of a motile pacifist photosynthesizer.
 *  @author Josh Hug
 */
public class Plip extends Creature {

    /** red color. */
    private int r;
    /** green color. */
    private int gBase;
    /** blue color. */
    private int b;
    /** The linear factor of the value of the green color and energy*/
    protected final int gFactor = (255 - 63) / 2;
    /** The factor of the rate of the energy remains in the original Plip when replicate*/
    protected final double remainFactor = 0.5;
    protected final double MAX_ENERGY = 2.0;

    /** creates plip with energy equal to E. */
    public Plip(double e) {
        super("plip");
        r = 99;
        gBase = 63;
        b = 76;
        energy = e;

    }

    /** creates a plip with energy equal to 1. */
    public Plip() {
        this(1);
    }

    /** Should return a color with red = 99, blue = 76, and green that varies
     *  linearly based on the energy of the Plip. If the plip has zero energy,
     *  it should have a green value of 63. If it has max energy, it should
     *  have a green value of 255. The green value should vary with energy
     *  linearly in between these two extremes. It's not absolutely vital
     *  that you get this exactly correct.
     */
    public Color color() {
        int g = gBase + (int)(energy() * gFactor);
        return color(r, g, b);
    }

    /** Do nothing with C, Plips are pacifists. */
    public void attack(Creature c) {
    }

    /** Plips should lose 0.15 units of energy when moving. If you want to
     *  to avoid the magic number warning, you'll need to make a
     *  private static final variable. This is not required for this lab.
     */
    public void move() {
        energy -= 0.15;
    }


    /** Plips gain 0.2 energy when staying due to photosynthesis. */
    public void stay() {
        energy += 0.2;
        if (energy > MAX_ENERGY){
            energy = MAX_ENERGY;
        }
    }

    /** Plips and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby
     *  Plip.
     */
    public Plip replicate() {
        Plip child = new Plip((1 - remainFactor) * this.energy);
        this.energy *= remainFactor;
        return child;
    }

    /** Plips take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY.
     *  2. Otherwise, if energy >= 1, REPLICATE.
     *  3. Otherwise, if any Cloruses, MOVE with 50% probability.
     *  4. Otherwise, if nothing else, STAY
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        if (empties.isEmpty()){
            return new Action(Action.ActionType.STAY);
        }else{
            if (energy >= 1){
                Direction moveDir = HugLifeUtils.randomEntry(empties);
                return new Action(Action.ActionType.REPLICATE, moveDir);
            }else{
                List<Direction> cloruses = getNeighborsOfType(neighbors, "clorus");
                if (!cloruses.isEmpty()){
                    Direction moveDir = HugLifeUtils.randomEntry(empties);
                    return new Action(Action.ActionType.MOVE, moveDir);
                }else{
                    return new Action(Action.ActionType.STAY);
                }
            }
        }
    }

}
