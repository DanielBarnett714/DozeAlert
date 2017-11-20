package me.dbarnett.dozealert;

/**
 * Created by daniel on 11/18/17.
 */
import java.io.File;
import java.io.IOException;
import java.util.Set;

import me.dbarnett.dozealert.dt.core.*;
import me.dbarnett.dozealert.dt.util.ArraySet;

public class DataTraining extends Problem {

    public DataTraining() {
        super();

        this.inputs.add(new Variable("buying", new Domain("vhigh", "high", "med", "low")));
        this.inputs.add(new Variable("maint", new Domain("vhigh", "high", "med", "low")));
        this.inputs.add(new Variable("doors", new Domain("2", "3", "4", "5more")));
        this.inputs.add(new Variable("persons", new Domain("2", "4", "more")));
        this.inputs.add(new Variable("lug_boot", new Domain("small", "med", "big")));
        this.inputs.add(new Variable("safety",  new Domain("low", "med", "high")));

        this.output = new Variable("acceptability", new Domain("unacc", "acc", "good", "vgood"));
    }

    public static void train(String filename) throws IOException {
        Problem problem = new DataTraining();
        problem.dump();
        File file = new File(filename);
        System.out.println(file.exists());
        Set<Example> examples = problem.readExamplesFromCSVFile(file);

        Set<Example> training = new ArraySet<>();
        Set<Example> validation = new ArraySet<>();
        int odd = 0;
        for (Example e : examples) {
            if (odd == 0){
                training.add(e);
                odd = 1;
            }else if (odd == 1){
                validation.add(e);
                odd = 0;
            }
        }
        DecisionTreeLearner learner = new DecisionTreeLearner(problem);
        DecisionTree tree = learner.learn(training);
        tree.dump();
        tree.test(validation);



    }
}