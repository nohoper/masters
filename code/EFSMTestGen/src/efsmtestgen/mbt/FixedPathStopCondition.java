/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package efsmtestgen.mbt;

import org.tigris.mbt.conditions.StopCondition;

/**
 *
 * @author andrewzakonov
 */
public class FixedPathStopCondition extends StopCondition {
    private final PathDescription pathDescr;

    public FixedPathStopCondition(PathDescription pathDescr) {
        this.pathDescr = pathDescr;
    }

    @Override
    public boolean isFulfilled() {
//        System.out.println("completed: " + getFulfilment());
        return !pathDescr.hasNext();
    }

    @Override
    public double getFulfilment() {
        return pathDescr.getCompletedPercent();
    }

}
