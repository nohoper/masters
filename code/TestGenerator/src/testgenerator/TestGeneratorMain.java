/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testgenerator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author andrewzakonov
 */
public class TestGeneratorMain {
    private static ArrayList<Integer> valuesList;
    private static HashSet<String> varNames;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BufferedReader in = null;
        try {
            //read actions from file
            in = new BufferedReader(new FileReader(args[0]));
            List<String> actions = new ArrayList<String>();
            String line = in.readLine();
            while (line != null) {
                actions.add(line);
                line = in.readLine();
            }

            //read vars from file
            in = new BufferedReader(new FileReader(args[1]));
            line = in.readLine();
            StringTokenizer tokenizer = new StringTokenizer(line, " ");
            valuesList = new ArrayList<Integer>();
            while (tokenizer.hasMoreTokens()) {
                valuesList.add(Integer.parseInt(tokenizer.nextToken()));
            }

            //read var names from file
            in = new BufferedReader(new FileReader(args[2]));
            line = in.readLine();
            tokenizer = new StringTokenizer(line, " ");
            varNames = new HashSet<String>();
            while (tokenizer.hasMoreTokens()) {
                varNames.add(tokenizer.nextToken());
            }


            writeTest(actions);


        } catch (IOException ex) {
            Logger.getLogger(TestGeneratorMain.class.getName()).log(Level.SEVERE, "failed to read path", ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(TestGeneratorMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    private static final String TAB = "    ";

    private static void writeTest(List<String> actionList) {
        Set<String> existingVars = new HashSet<String>();

        FileWriter fw = null;
        try {
            fw = new FileWriter("GeneratedTest.java");
            int stepNum = 1;
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("public class GeneratedTest {");
            bw.newLine();
            for (String name : varNames) {
                bw.write("private static int " + name + ";");
                bw.newLine();
            }
            bw.newLine();

            for (String item : actionList) {
                bw.write("private static void step" + (stepNum++) + "() {");
                bw.newLine();


                StringTokenizer tokenizer = new StringTokenizer(item, ";");
                while (tokenizer.hasMoreTokens()) {
                    String statement = tokenizer.nextToken();
                    statement = handleStatement(statement);
                    bw.write(TAB + statement + ";");
                    bw.newLine();
                }
                


                bw.newLine();
                bw.write("}");
                bw.newLine();
                bw.newLine();
            }
            bw.newLine();
            bw.newLine();

            bw.write("public static void main(String[] args) {");
            bw.newLine();
            for (int i =1; i < stepNum; ++i) {
                bw.write(TAB + "step" + i + "();");
                bw.newLine();
            }
            bw.write("}");
            bw.newLine();
            bw.newLine();

            bw.write("}");
            bw.close();


        } catch (IOException ex) {
            Logger.getLogger(TestGeneratorMain.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(TestGeneratorMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static String handleStatement(String statement) {
        if (statement.endsWith("_")) {
            statement = statement.substring(0, statement.length()-1) + " = " + valuesList.remove(0);
        }
//        StringTokenizer tokenizer = new StringTokenizer(statement, " ");
//        String varname = tokenizer.nextToken();
//        if (varNames.)
        return  statement;
    }

}
