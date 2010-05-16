/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testexecuter;

import efsmtestgen.mbt.ActionExecuter;
import efsmtestgen.mbt.FixedPathGenerator;
import efsmtestgen.mbt.PathDescription;
import efsmtestgen.mbt.PathDescriptionConsole;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tigris.mbt.CLI;
import org.tigris.mbt.generators.PathGenerator;
import org.tigris.mbt.machines.MBTActionExecuter;

/**
 *
 * @author andrewzakonov
 */
public class TestExecuterMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BufferedReader in = null;
        try {
            //read path from file
            in = new BufferedReader(new FileReader(args[1]));
            String line = in.readLine();
            StringTokenizer tokenizer = new StringTokenizer(line, " ");
            String[] pathString = new String[tokenizer.countTokens()];
            int i = 0;
            while (tokenizer.hasMoreTokens()) {
                pathString[i++] = tokenizer.nextToken();
            }

            //read vars from file
            in = new BufferedReader(new FileReader(args[2]));
            line = in.readLine();
            tokenizer = new StringTokenizer(line, " ");
            List<Integer> varsList = new ArrayList<Integer>();
            while (tokenizer.hasMoreTokens()) {
                varsList.add(Integer.parseInt(tokenizer.nextToken()));
            }


            //create path file
            PathDescription path = null;
            path = new PathDescriptionConsole(args[0], pathString, -1);
            if (path == null) {
                return;
            }

            CLI cli = new CLI();
            cli.getMbt().reset();
            cli.getMbt().readGraph(path.getFileName());
            cli.getMbt().enableJsScriptEngine(false);
            cli.getMbt().enableExtended(true);
            cli.getMbt().setWeighted(false);
            double best_percent_filfulled = -1;
            String bestVarsEngineState = "";
            int iteration = 0;
            ActionExecuter engine = null;
            engine = new ActionExecuter(varsList);
            MBTActionExecuter.setExecEngine(engine);
            MBTActionExecuter.resetFitness();
            cli.getMbt().enableExtended(true);
            PathGenerator pathGenerator = new FixedPathGenerator(path);
            cli.getMbt().setGenerator(pathGenerator);
            boolean res = cli.getMbt().writePath();
            float branch_distance = (float) MBTActionExecuter.getFitness(); //==branch distance
            if (branch_distance == 0) {
                System.out.println("fitness is 0!!!! : " + branch_distance);
            }
            double percent_filfulled = pathGenerator.getConditionFulfilment();
            best_percent_filfulled = percent_filfulled;
            bestVarsEngineState = engine.azPrintLastInfo();
            System.out.println("Filfulled steps: " + best_percent_filfulled);
            System.out.println("Eng state: " + bestVarsEngineState);
            if (best_percent_filfulled >= 0.99) {
                System.out.println("condition filfulled!!!!!!!");
                return;
            }
        } catch (IOException ex) {
            Logger.getLogger(TestExecuterMain.class.getName()).log(Level.SEVERE, "failed to read path", ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(TestExecuterMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
