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
public class PathDescription_xy implements PathDescription {

    public static int varsNum = 6;

    public final static String filename = "../graphs/xy_path.graphml";

    private int successfullSteps = -1;

    String[] path = new String[] {
        "t1", "t2", "t3",
        "t4",
        
        "t1", "t2", "t3",
        "t4",
        
        "t1", "t2", "t3",
        "t4",

        "t3"
    };
    
    List<String> path2go;

    public PathDescription_xy() {
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
        if (successfullSteps < 0) {
            successfullSteps = path2go.size();
        }
        double res = ((double)(path.length - (successfullSteps - 1))) / ((double)path.length);
        successfullSteps = -1;
        return res;
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
        if (successfullSteps < 0) {
            successfullSteps = path2go.size();
        }
    }

}
