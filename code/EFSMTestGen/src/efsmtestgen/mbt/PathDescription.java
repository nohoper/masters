/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package efsmtestgen.mbt;

/**
 *
 * @author andrewzakonov
 */
public interface PathDescription {

    public String getNextStep ();

    public boolean hasNext();

    double getCompletedPercent();

    void rollBack(String nextStep);

    public int getVarsNum();

    public String getFileName();

    public void stopCountionSuccessfulSteps();

}
