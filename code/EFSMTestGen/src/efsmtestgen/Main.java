/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package efsmtestgen;

import efsmtestgen.ga.GA4IntArr;
import efsmtestgen.ga.ga_struct;
import java.io.IOException;
import java.math.BigDecimal;
import efsmtestgen.mbt.ActionExecuter;
import efsmtestgen.mbt.FixedPathGenerator;
import efsmtestgen.mbt.PathDescription;
import efsmtestgen.mbt.PathDescriptionConsole;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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
public class Main {

//    public final static String[] mbtArgs = new String[]{
//        "offline",
//        "-f",
//        "../graphs/atm_no_ext.graphml",
//        "-g",
//        "ANDREW",
//        "-s",
//        "REACHED_EDGE:e_3_depoisted_last",
//        "-x"
//    };

//    public final static String generatorName = "ANDREW";


    static int attempt = 1;

    private final static int POPULATION_SIZE = 1024;

    private final static int MAX_VALUE = 50000;
    private static List<String> lastSettedVarNames;
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
            
            //number of variables to guess
            int varsNum = Integer.parseInt(args[2]);
            PathDescription path = null;
            path = new PathDescriptionConsole(args[0], pathString, varsNum);
            if (path == null) {
                return;
            }

            GA4IntArr gaEngine = new GA4IntArr(MAX_VALUE, POPULATION_SIZE, 16000, 0.1, 0.5, path.getVarsNum());
            List<ga_struct> population = gaEngine.init_population();
            CLI cli = new CLI();
            cli.getMbt().reset();
            cli.getMbt().readGraph(path.getFileName());
            cli.getMbt().enableJsScriptEngine(false);
            cli.getMbt().enableExtended(true);
            cli.getMbt().setWeighted(false);
            double min_fitness = Integer.MAX_VALUE;
            double best_raw_fitness = -1;
            double best_percent_filfulled = -1;
            int best_weighted_fitness = -1;
            List<Integer> bestVarsList = new ArrayList<Integer>();
            String bestVarsEngineState = "";
            int iteration = 0;
            ActionExecuter engine = null;
            while (min_fitness > 0.1f) {
                double summary_fitness = 0;
                for (ga_struct item : population) {
                    List<Integer> varsList = new ArrayList<Integer>();
                    for (int t : item.vals) {
                        varsList.add(t);
                    }
                    engine = new ActionExecuter(varsList);
                    MBTActionExecuter.setExecEngine(engine);
                    MBTActionExecuter.resetFitness();
                    cli.getMbt().enableExtended(true);
                    PathGenerator pathGenerator = new FixedPathGenerator(new PathDescriptionConsole(args[0], pathString, path.getVarsNum()));
                    cli.getMbt().setGenerator(pathGenerator);
                    boolean res = cli.getMbt().writePath();
                    //                    BigDecimal bd = new BigDecimal(pathGenerator.getConditionFulfilment());
                    //                    BigDecimal bd = new BigDecimal(1.0 / ((float) MBTActionExecuter.getFitness()) );
                    float branch_distance = (float) MBTActionExecuter.getFitness(); //==branch distance
                    double percent_filfulled = pathGenerator.getConditionFulfilment();
                    BigDecimal bd;
                    if (branch_distance == 0) {
                        bd = new BigDecimal(percent_filfulled);
                    } else {
                        bd = new BigDecimal(percent_filfulled / branch_distance);
                    }
                    item.fitness = (int) (1.0/(bd.floatValue()));
                    int weitedFitness = item.fitness;
                    //                    System.out.println("weighted:" + weitedFitness);
                    //                    item.fitness = MBTActionExecuter.getFitness();
                    //                    System.out.println("f: " + item.fitness);
                    //                    bd = bd.setScale(2, RoundingMode.UP);
                    float fitness = 1.0f - bd.floatValue();
                    summary_fitness += fitness / POPULATION_SIZE;
                    if (fitness < min_fitness) {
                        min_fitness = fitness;
                        best_raw_fitness = branch_distance;
                        best_weighted_fitness = weitedFitness;
                        best_percent_filfulled = percent_filfulled;
                        bestVarsList.clear();
                        for (int t : item.vals) {
                            bestVarsList.add(t);
                        }
                        bestVarsEngineState = engine.azPrintLastInfo();
                        lastSettedVarNames = engine.azGetLastVarNamesList();
                    }
                    engine.resetSettedVars();
                }
                System.out.println("Iteration: " + (++iteration));
                System.out.println("Avg. fitness: " + summary_fitness);
                System.out.println("Filfulled steps: " + best_percent_filfulled);
                System.out.println("Min fitness: " + min_fitness + "(raw:" + best_raw_fitness + ", weigthed:" + best_weighted_fitness + ") : " + bestVarsList);
                System.out.println("Eng state: " + bestVarsEngineState);
                
                if (best_percent_filfulled >= 0.99) {
                    System.out.println("condition filfulled!!!!!!!");
                    writeVarsToFile(bestVarsList, MBTActionExecuter.getEventsList(), MBTActionExecuter.getActionsList(), lastSettedVarNames);
                    return;
                }

                if (best_raw_fitness == 0) {
                    System.out.println("fitness is 0!!!! : ");
                    System.out.println("condition filfulled!!!!!!!");
                    writeVarsToFile(bestVarsList, MBTActionExecuter.getEventsList(), MBTActionExecuter.getActionsList(), lastSettedVarNames);
                    return;
                }

                if (summary_fitness <= 0.1) {
                    int r = 0;
                }
                population = gaEngine.makeNextPopulation(population);
                System.gc();
                if (iteration > 200) {
                    System.out.println("Failed to find suitable values in 200 iterations.");
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "failed to read path", ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private static void writeVarsToFile(List<Integer> varsList, List<String> eventsList, List<String> actionList, List<String> settedVars) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("variables.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            for (int item : varsList) {
                bw.write("" + item + " ");
            }
            bw.newLine();
            bw.close();

            fw = new FileWriter("events.txt");
            bw = new BufferedWriter(fw);
            for (String item : eventsList) {
                bw.write("" + item + " ");
            }
            bw.newLine();
            bw.close();

            fw = new FileWriter("actions.txt");
            bw = new BufferedWriter(fw);
            for (String item : actionList) {
                bw.write(item);
                bw.newLine();
            }
            bw.newLine();
            bw.close();

            fw = new FileWriter("settedVars.txt");
            bw = new BufferedWriter(fw);
            for (String item : settedVars) {
                bw.write(item + " ");
            }
            bw.newLine();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
