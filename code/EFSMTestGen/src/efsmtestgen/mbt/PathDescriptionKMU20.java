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
public class PathDescriptionKMU20 implements PathDescription {

    public final static int MAX_VALUE = 20000;

    public final static String filename = "../graphs/kmu_atm_efsm.graphml";

    public static int varsNum = 20 + 2;

    private int unsuccessfullSteps = -1;

    String[] path = new String[] {
        "t1",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",

        "t2", "t3",
        
        "t2", "t4", "t9"
    };
    
    List<String> path2go;

    public PathDescriptionKMU20() {
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
        if (unsuccessfullSteps < 0) {
            unsuccessfullSteps = path2go.size();
        }
        double res = ((double)(path.length - (unsuccessfullSteps - 1))) / ((double)path.length);
//        if (res > 1) {
//            int t = 0;
//        }
        unsuccessfullSteps = -1;
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
        if (unsuccessfullSteps < 0) {
            unsuccessfullSteps = path2go.size();
//            if (unsuccessfullSteps < 2) {
//                int t = 0;
//            }
        }
    }

}
