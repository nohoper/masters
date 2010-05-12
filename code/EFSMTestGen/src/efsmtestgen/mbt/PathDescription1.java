/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package efsmtestgen.mbt;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andrewzakonov
 */
public class PathDescription1 implements PathDescription {

    public final static String filename = "../graphs/atm_simple_1.graphml";

    public static int varsNum = 11;

    String[] path = new String[] {
        "t1",

        "t2", "t4", "t5", "t7",

        "t2", "t4", "t5", "t7",

        "t2", "t4", "t5", "t7",

        "t2", "t4", "t5", "t7",

        "t2", "t4", "t5", "t7",

        "t2", "t4", "t5", "t7",

        "t2", "t4", "t5", "t7",

        "t2", "t4", "t5", "t7",

        "t2", "t4", "t5", "t7",

        "t2", "t4", "t5",

        "t8", "t8"
    };
    
    List<String> path2go;

    public PathDescription1() {
        path2go = new ArrayList<String>();
        for (String str : path) {
            path2go.add(str);
        }
    }

    public String getNextStep () {
        return path2go.remove(0);
    }

    public boolean hasNext() {
        return !path2go.isEmpty();
    }

    public double getCompletedPercent() {
        return ((double)(path.length - path2go.size())) / ((double)path.length);
    }

    public void rollBack(String nextStep) {
        path2go.add(0, nextStep);
    }

    public int getVarsNum() {
        return varsNum;
    }

    public String getFileName() {
        return  filename;
    }

    public void stopCountionSuccessfulSteps() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
