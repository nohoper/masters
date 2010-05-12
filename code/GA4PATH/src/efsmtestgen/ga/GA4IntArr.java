/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package efsmtestgen.ga;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

/**
 *
 * @author andrewzakonov
 */
public class GA4IntArr {

    GAUtils utils;
    private Logger logger;

    {
        try {
            Layout layout = new SimpleLayout();
            ConsoleAppender appender = new ConsoleAppender(layout);
            FileAppender fileAppender = new FileAppender(layout, "app.log");
            logger = Logger.getRootLogger();
            logger.addAppender(appender);
            logger.addAppender(fileAppender);
            Logger.getRootLogger().setLevel(Level.FATAL);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(GA4IntArr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    //    //settings
    final int RAND_MAX;
    final int GA_POPSIZE; // ga population size
    final int GA_MAXITER; // maximum iterations
    final double GA_ELITRATE; // elitism rate
    final double GA_MUTATIONRATE; // mutation rate
    final int VARS_NUM;
    double GA_MUTATION;

    public GA4IntArr(int RAND_MAX, int GA_POPSIZE, int GA_MAXITER, double GA_ELITRATE, double GA_MUTATIONRATE, int VARS_NUM) {
        this.RAND_MAX = RAND_MAX;
        this.GA_POPSIZE = GA_POPSIZE;
        this.GA_MAXITER = GA_MAXITER;
        this.GA_ELITRATE = GA_ELITRATE;
        this.GA_MUTATIONRATE = GA_MUTATIONRATE;
        this.VARS_NUM = VARS_NUM;
        GA_MUTATION = RAND_MAX * GA_MUTATIONRATE;
        utils = new GAUtils(RAND_MAX);
    }

    private int finess_function(ga_struct tmp) {
        long y = tmp.vals[0];
        long x = tmp.vals[1];
        long z = tmp.vals[2];

        long border = Integer.MAX_VALUE / 3;

        if ((x > border) || (y > border) || (z > border)) {
            return Integer.MAX_VALUE;
        }

        long x_fitness = 0;
        long y_fitness = 0;
        long z_fitness = 0;

        if (x + y + z < 1000) {
            int r = 0;
        }

        x_fitness = Math.abs(x * x + y * y + z * z - 100);

        if (x_fitness == 0) {
            int r = 0;
        }

        if (x_fitness < Integer.MAX_VALUE) {
            return (int) x_fitness;
        }


        border = Integer.MAX_VALUE / 2;

        if ((x_fitness > border) || (y_fitness > border) || (z_fitness > border)) {
            return Integer.MAX_VALUE;
        }

        long abc = x_fitness + y_fitness + z_fitness;
        if (abc < 0 || abc > Integer.MAX_VALUE) {
            abc = Integer.MAX_VALUE;
        }
        return (int) abc;

//        return Math.abs(fitness);
    }

    public List<ga_struct> init_population() {
        List<ga_struct> population = new ArrayList<ga_struct>();

        for (int i = 0; i < GA_POPSIZE; i++) {
            ga_struct citizen = new ga_struct();

            citizen.fitness = 0;
            citizen.vals = utils.random_int_arr(VARS_NUM);

            population.add(citizen);
            logger.info(citizen);
        }

        return population;
    }

    void calc_fitness_for_all(List<ga_struct> population) {
        int fitness = 0;

        ga_struct tmp = null;

        for (int i = 0; i < GA_POPSIZE; i++) {
            tmp = population.get(i);
            population.get(i).fitness = finess_function(tmp);
        }
    }

    void sort_by_fitness(List<ga_struct> population) {
        Collections.sort(population, new Comparator<ga_struct>() {

            public int compare(ga_struct o1, ga_struct o2) {
                return new Integer(o1.fitness).compareTo(o2.fitness);
            }
        });
    }

    List<ga_struct> elitism(List<ga_struct> population, int esize) {
        List<ga_struct> buffer = new ArrayList<ga_struct>(GA_POPSIZE);
        for (int i = 0; i < esize; i++) {
            ga_struct newItem = new ga_struct();
            ga_struct oldItem = population.get(i);
            newItem.vals = oldItem.vals;
            newItem.fitness = oldItem.fitness;
            buffer.add(newItem);
        }
        return buffer;
    }

    void mutate(ga_struct member) {
        logger.info("befor mutation: " + member);

        for (int i = 0; i < VARS_NUM; ++i) {

//            int ipos = (int) (Math.random() * 31); //32 bits in int
//            boolean negative = false;
//            if (member.vals[i] < 0) {
//                negative = true;
//                member.vals[i] *= (-1);
//            }
//
//            String binStr = utils.toBinStr(member.vals[i]);
//            logger.info("mutation pos: " + ipos);
//
//
//            char[] tmp = binStr.toCharArray();
//
//            tmp[ipos] = (tmp[ipos] == '0' ? '1' : '0');
//
//            int newInt = Integer.parseInt(new String(tmp), 2);
            double randomNumber = Math.random();
            int newInt = (int) (randomNumber * RAND_MAX);
            if (newInt > RAND_MAX) {
                newInt = RAND_MAX;
            }
            member.vals[i] = newInt;
//            if (negative) {
//                member.vals[i] *= (-1);
//            }
        }

        logger.info("after mutation: " + member);
    }

    ga_struct mate2items(ga_struct a, ga_struct b) {
//        int spos = (int) (Math.random() * 31);
        int spos = (int) (Math.random() * VARS_NUM);
        int[] arr = new int[VARS_NUM];
        for (int i = 0; i < VARS_NUM; ++i) {
            if (i <= spos)
                arr[i] = a.vals[i];
            else
                arr[i] = b.vals[i];
//            String str1 = utils.toBinStr(a.vals[i]);
//            String str2 = utils.toBinStr(b.vals[i]);
//
//            String newStr = str1.substring(0, spos) + str2.substring(spos, 31);
//
//            arr[i] = Integer.parseInt(newStr, 2);


//            arr[i] = (a.vals[i] + b.vals[i]) / 2;

//            Random rand = new Random();
//            if (rand.nextBoolean()) {
//                arr[i] = a.vals[i];
//            } else {
//                arr[i] = b.vals[i];
//            }
        }
        return new ga_struct(arr);
    }

    List<ga_struct> mate(List<ga_struct> population) {
        int esize = (int) (GA_POPSIZE * GA_ELITRATE);
        int spos, i1, i2;

        List<ga_struct> buffer = elitism(population, esize);

//        for (int i = 0; i < esize; ++i) {
//            if (Math.random() < GA_MUTATIONRATE) {
//                mutate(buffer.get(i));
//            }
//        }

        // Mate the rest
        for (int i = esize; i < GA_POPSIZE; i++) {
            i1 = (int) (Math.random() * (GA_POPSIZE / 2));
            i2 = (int) (Math.random() * (GA_POPSIZE / 2));
            logger.trace("i1 and i2: " + i1 + " " + i2);

            buffer.add(mate2items(population.get(i1), population.get(i2)));

            if (Math.random() < GA_MUTATIONRATE) {
                mutate(buffer.get(i));
            }
        }
        return buffer;
    }

    void go() {

        List<ga_struct> pop_alpha, pop_beta;
        List<ga_struct> population, buffer;

        population = init_population();

        for (int i = 0; i < GA_MAXITER; i++) {
            logger.trace("step: " + i);
//            System.out.println("step: " + i);
            calc_fitness_for_all(population);		// calculate fitness
            sort_by_fitness(population);	// sort them
//		print_best(population);		// print the best one
            System.out.println(" " + i + ": " + population.get(0));

            if (population.get(0).fitness == 0) {
                break;
            }

            buffer = mate(population);		// mate the population together
            population = buffer;		// swap buffers
        }

//	return 0;
    }

    public List<ga_struct> makeNextPopulation(List<ga_struct> input) {
        List<ga_struct> output;

        sort_by_fitness(input);	// sort them
        output = mate(input);		// mate the population together

        return output;
    }
}
