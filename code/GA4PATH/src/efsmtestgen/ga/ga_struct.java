/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package efsmtestgen.ga;

/**
 *
 * @author andrewzakonov
 */
public class ga_struct {
        public int[] vals;
        public int fitness;

        public ga_struct(int[] val) {
            this.vals = val;
        }

        public ga_struct() {
        }

        @Override
        public String toString() {
            String str = "";
            for (int v : vals) {
                str += v + " ";
            }
            return str + " (" + fitness + ")";
        }
    }
