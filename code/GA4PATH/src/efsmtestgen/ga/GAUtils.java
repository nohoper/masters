/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package efsmtestgen.ga;

/**
 *
 * @author andrewzakonov
 */
public class GAUtils {

    private final int MAX_RANDOM;

    public GAUtils(int MAX_RANDOM) {
        this.MAX_RANDOM = MAX_RANDOM;
    }

    public String toBinStr(int val) {
        String str = Integer.toBinaryString(val);
        while (str.length() < 31) {
            str = '0' + str;
        }
        return str;
    }


    public int[] random_int_arr (int size) {
        int[] res = new int[size];
        for (int i = 0; i < size; ++i) {
            double randomNumber = Math.random();
            res[i] = (int) (randomNumber * MAX_RANDOM);
//            res[i] = (int) (randomNumber * 100000);
        }
        return res;
    }

}
