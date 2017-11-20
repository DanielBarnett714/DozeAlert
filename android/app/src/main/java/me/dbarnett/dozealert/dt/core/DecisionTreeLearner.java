package me.dbarnett.dozealert.dt.core;


import me.dbarnett.dozealert.dt.core.*;

import me.dbarnett.dozealert.dt.util.ArraySet;

import java.util.*;

/**
 * Created by daniel on 4/25/17.
 */
public class DecisionTreeLearner {
    /**
     * Construct and return a new dt.core.DecisionTreeLearner for the given Problem.
     *
     * @param problem
     */

    protected Problem problem;

    public DecisionTreeLearner(Problem problem) {

        this.problem = problem;
    }

    public DecisionTree learn(Set<Example> examples) {
        return learn(examples, problem.getInputs(), Collections.<Example>emptySet());
    }


    protected DecisionTree learn(Set<Example> examples, List<Variable> attributes, Set<Example> parent_examples) {
        if (examples.isEmpty()){
            return new DecisionTree(pluralityValue(parent_examples));
        }else if (!uniqueOutputValue(examples).equals("")){
            return new DecisionTree(uniqueOutputValue(examples));
        }else if (attributes.isEmpty()){
            return new DecisionTree(pluralityValue(examples));
        }else{
            Variable argmax = mostImportantVariable(attributes, examples);
            DecisionTree tree = new DecisionTree(argmax);

            for (String vk : argmax.domain) {
                Set<Example> exs = examplesWithValueForAttribute(examples, argmax, vk);

                List<Variable> newAttributes = new ArrayList<>();
                for (Variable v : attributes) {
                    newAttributes.add(v);
                }
                newAttributes.remove(argmax);

                DecisionTree subtree = learn(exs, newAttributes, examples);

                tree.children.add(subtree);
            }

            return tree;
        }
    }


    protected String pluralityValue(Set<Example> examples) {
        Hashtable<String, Integer> outputs = new Hashtable<>();
        int max = -1;
        String pluralityValue = "";
        for (Example e : examples) {

            if (outputs.containsKey(e.outputValue)){
                int count = outputs.get(e.outputValue);
                count ++;
                if (count > max){
                    pluralityValue = e.getOutputValue();
                }
                outputs.put(e.getOutputValue(), count);

            }else{
                outputs.put(e.getOutputValue(), 1);
                if (max == -1){
                    pluralityValue = e.getOutputValue();
                }
            }
        }
        return pluralityValue;
    }


    protected String uniqueOutputValue(Set<Example> examples) {
        String outputValue = "";
        for (Example e : examples) {
            if (outputValue.equals("")){
                outputValue = e.outputValue;
            }else{
                if (!outputValue.equals(e.outputValue)){
                    return "";
                }
            }
        }
        return outputValue;
    }


    protected Set<Example> examplesWithValueForAttribute(Set<Example> examples, Variable a, String vk) {
        Set<Example> examplesWithValue = new ArraySet<>();
        for (Example e : examples) {
            if (e.getInputValue(a).equals(vk)) {
                examplesWithValue.add(e);
            }
        }



        return examplesWithValue;
    }


    protected int countExamplesWithValueForAttribute(Set<Example> examples, Variable a, String vk) {
        int count = 0;
        for (Example e : examples) {
            if (e.getInputValue(a).equals(vk)){
                count++;
            }
        }

        return count;
    }


    protected int countExamplesWithValueForOutput(Set<Example> examples, String vk) {
        int count = 0;
        for (Example e : examples) {
            if (e.getOutputValue().equals(vk)){
                count++;
            }
        }

        return count;
    }

    protected Variable mostImportantVariable(List<Variable> attributes, Set<Example> examples) {
        Variable maxvar = null;
        double maxgain = 0;
        for (Variable a : attributes) {
            double g = gain(a, examples);
            System.out.println(a.name + " gain: " + g);
            if (maxvar == null || g > maxgain) {
                maxvar = a;
                maxgain = g;
            }
        }
        return maxvar;
    }

    protected double H(Set<Example> examples) {
        double result = 0;
        int n = examples.size();
        for (String vk : problem.getOutput().domain) {
            int nk = countExamplesWithValueForOutput(examples, vk);
            if (nk > 0) {
                double pk = (double)nk / n;
                result += pk*log2(pk);
            }
        }
        return -result;
    }

    protected double remainder(Variable a, Set<Example> examples) {
        double result = 0;
        for (String vk : a.domain) {
            Set<Example> Ek = examplesWithValueForAttribute(examples, a, vk);
            if (Ek.size() == 0) {
                continue;
            }
            result += (double)Ek.size()/examples.size() * H(Ek);
        }
        return result;
    }

    /**
     * Return log base 2 of the given number.
     */
    protected double log2(double x) {
        return Math.log(x) / Math.log(2.0);
    }

    /**
     * For set of examples T, attribute (variable) a with values vk,
     * and Ek = subset of T for which a=vk:
     * IG(T,a) = H(T) - \sum_vk { |Ek|/|T| * H(Ek) }
     * This is the general case of the Boolean version done in AIMA.
     */
    protected double gain(Variable a, Set<Example> examples) {
        return H(examples) - remainder(a, examples);
    }


    public void prune(DecisionTree tree, Set<Example> validation, DecisionTree top, Problem problem, double range){
        List<DecisionTree> children = tree.children;


            for (DecisionTree child : children) {


                    double test = top.validate(validation);


                    if (child.value == null) {
                        List<DecisionTree> tempChildren = new ArrayList<>();
                        for (DecisionTree childTree : child.children) {
                            tempChildren.add(childTree);
                        }

                        Variable var = child.variable;

                        child.variable = null;
                        child.children.clear();

                        boolean remove = false;
                        String trueVal = "";
                        double min = 1;
                        for (String output : problem.getOutput().domain) {
                            child.value = output;
                            double test2 = top.validate(validation);




                            if ((test / test2) > (1-range) && (test / test2) < (1+range)) {
                                double checkMin = Math.abs(1 - (test / test2));
                                if (checkMin < min){
                                    min = checkMin;
                                    trueVal = output;
                                }

                                remove = true;
                            }
                        }
                        if (!remove){
                            child.children.addAll(tempChildren);
                            child.value = null;
                            child.variable = var;
                            prune(child, validation, top, problem, range);
                        }else{
                            child.value = trueVal;
                        }
                    }

            }

    }
}
