package efsmtestgen.mbt;

import java.lang.IllegalArgumentException;
import org.tigris.mbt.generators.*;
import java.util.Set;

import org.apache.log4j.Logger;
import org.tigris.mbt.Util;
import org.tigris.mbt.conditions.StopCondition;
import org.tigris.mbt.exceptions.FoundNoEdgeException;
import org.tigris.mbt.graph.Edge;
import org.tigris.mbt.machines.MBTActionExecuter;

public class FixedPathGenerator extends PathGenerator {

    static Logger logger = Util.setupLogger(FixedPathGenerator.class);
    private final PathDescription pathDescr;

    public FixedPathGenerator(PathDescription path) {
        pathDescr = path;
        stopCondition = new FixedPathStopCondition(pathDescr);
    }

    @Override
    public void setStopCondition(StopCondition stopCondition) {
//        this.stopCondition = new FixedPathStopCondition(pathDescr);
        if (this.machine != null) {
            this.stopCondition.setMachine(this.machine);
        }
    }

    public String[] getNext() {
        String nextStep = pathDescr.getNextStep();
        Set<Edge> availableEdges = null;

        MBTActionExecuter.resetFailed();
        try {
            availableEdges = getMachine().getCurrentOutEdges(nextStep);
            if (availableEdges.size() > 1) {
                throw new IllegalArgumentException("more then one transition available for :" + nextStep);
            } else if (availableEdges.size() < 1) {
                pathDescr.rollBack(nextStep); //failed
                throw new IllegalArgumentException("next step not found");
            }
        } catch (FoundNoEdgeException e) {
            throw new RuntimeException("No possible edges available for path", e);
        }
        
        if (MBTActionExecuter.isAlreadyFailed()) {
            pathDescr.stopCountionSuccessfulSteps();
        }

        Edge edge = getRandomEdge(availableEdges);
        boolean res = getMachine().walkEdge(edge);
        if (!res) {
            throw new IllegalArgumentException("wrong vars. can't make path");
        }
        logger.debug(edge.getFullLabelKey());
//        System.out.println("______________using edge: " + edge);
        String[] retur = {getMachine().getEdgeName(edge), getMachine().getCurrentStateName()};
        return retur;
    }

    private Edge getRandomEdge(Set<Edge> availableEdges) { 
        for (Object obj : availableEdges.toArray()) {
            Edge e = (Edge) obj;
            return e;
        }
        return null;
    }

    @Override
    public String toString() {
        return "RANDOM{" + super.toString() + "}";
    }
}
