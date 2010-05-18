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
public class PathDescriptionConsole implements PathDescription {

    private String filename;
    private int varsNum;
    private String[] path;

    public static int MAX_VALUE = 50000;
    private int unsuccessfullSteps = -1;

    private List<String> path2go;

    public PathDescriptionConsole(String filename, String[] path, int varsNum) {
        this.filename = filename;
        this.path = path;
        this.varsNum = varsNum;

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
            unsuccessfullSteps = path2go.size() + 1; //need to plus one step that was right now failed
//            if (unsuccessfullSteps < 2) {
//                int t = 0;
//            }
        }
    }

}
