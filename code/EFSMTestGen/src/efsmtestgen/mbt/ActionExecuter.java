/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package efsmtestgen.mbt;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.NameSpace;
import bsh.Primitive;
import bsh.UtilEvalError;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import org.tigris.mbt.filters.AccessableEdgeFilter;
import org.tigris.mbt.machines.IActionExecuter;

/**
 *
 * @author andrewzakonov
 */
public class ActionExecuter implements IActionExecuter {

    private List<String> settedVars = new ArrayList();

    private List<Integer> predefinedVars;

    private Interpreter lastEngineInst = null;

    enum InputModeTypes {
        Console, Random, Predefined
    }

    public ActionExecuter(List<Integer> predefinedVars) {
        this.predefinedVars = predefinedVars;
    }

    public ActionExecuter() {
    }

    private final InputModeTypes inputMode = InputModeTypes.Predefined;

    public String azExecAction(Interpreter beanShellEngine, String action) throws EvalError {
        String res = "";

        String[] words = action.split(" ");
        List<String> extvars = new ArrayList<String>();
        for (String w : words) {
            if (w.startsWith("ext_")) {
                extvars.add(w);
            }
        }

        for (String var : extvars) {
            if (var.endsWith("_;")) {
                String varName = var.substring(0, var.length()-2);
                int value = azAskValue(varName);
                beanShellEngine.set(varName, value);
                settedVars.add(varName + " = " + value);

                action.replace(var, "");
            }
        }

        Object obj = beanShellEngine.eval(action);
//        res = obj.toString();
        lastEngineInst = beanShellEngine;
        return res;
    }

    private int azAskValue(String varName) {
        switch (inputMode) {
            case Random:
                return (int) (Math.random() * 2000);
            case Console:
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(System.in);
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    System.out.println("Type value for " + varName + ":");
                    String value = reader.readLine();
                    return Integer.parseInt(value);
                } catch (Exception ex) {
                    java.util.logging.Logger.getLogger(AccessableEdgeFilter.class.getName()).log(Level.SEVERE, null, ex);
                    throw new RuntimeException("input failed");
                }
            case Predefined:
                return predefinedVars.remove((int)0);
            default:
                return 1;
        }
        

    }

    public String azPrintInfo(Interpreter beanShellEngine) {
        String res = "";
        try {
            Hashtable<String, Object> retur = new Hashtable<String, Object>();

            int i = 0;
            NameSpace ns = beanShellEngine.getNameSpace();
            String[] variableNames = beanShellEngine.getNameSpace().getVariableNames();
            try {
                for (; i < variableNames.length; i++) {
                    if (!variableNames[i].equals("bsh")) {
                        retur.put(variableNames[i], Primitive.unwrap(ns.getVariable(variableNames[i])));
                    }
                }
            } catch (UtilEvalError e) {
                throw new RuntimeException("Malformed model data: " + variableNames[i] + "\nBeanShell error message: '" + e.getMessage() + "'");
            }

            for (Entry<String, Object> ent : retur.entrySet()) {
                res += "____ " + ent.getKey() + " :: " + ent.getValue();
            }
        } catch (Exception ex) {
            System.out.println("buggg");
        }
        return res;
    }

    public String azPrintLastInfo() {
        if (lastEngineInst != null) {
            return azPrintInfo(lastEngineInst);
        } else {
            return "no last engine found";
        }
    }

    public Hashtable<String, Object> azGetCurrentBeanShellData(Interpreter beanShellEngine) { //@todo P2 code duplicate!!
        Hashtable<String, Object> retur = new Hashtable<String, Object>();

        int i = 0;
        NameSpace ns = beanShellEngine.getNameSpace();
        String[] variableNames = beanShellEngine.getNameSpace().getVariableNames();
        try {
            for (; i < variableNames.length; i++) {
                if (!variableNames[i].equals("bsh")) {
                    retur.put(variableNames[i], Primitive.unwrap(ns.getVariable(variableNames[i])));
                }
            }
        } catch (UtilEvalError e) {
            throw new RuntimeException("Malformed model data: " + variableNames[i] + "\nBeanShell error message: '" + e.getMessage() + "'");
        }
        return retur;
    }


    public void resetSettedVars() {
        settedVars.clear();
    }

    public String printSettedVars() {
        String res = "";
        for (String str : settedVars) {
            res += str + "; ";
        }
        return res;
    }
}





