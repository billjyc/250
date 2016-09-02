import Jama.Matrix;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by billjyc on 2015/12/3.
 */
public class Computation {
    public static final int S = 81;
    public static final int A = 4;
    public static final double GAMMA = 0.99;
    public static final double ERROR = 0.001;

    public static final int WEST = 0;
    public static final int NORTH = 1;
    public static final int EAST = 2;
    public static final int SOUTH = 3;

    public int[] rewards;
    public SparseMatrix action1;
    public SparseMatrix action2;
    public SparseMatrix action3;
    public SparseMatrix action4;
    public ArrayList<SparseMatrix> list = new ArrayList<>();
    double[] value = new double[S];

    Matrix transitionMatrix1;
    Matrix transitionMatrix2;
    Matrix transitionMatrix3;
    Matrix transitionMatrix4;
    public ArrayList<Matrix> matrixList = new ArrayList<>();

    Matrix identityMatrix;
    Matrix rewardsMatrix;

    public Computation() {
        try {
            rewards = IOHelper.getRewards("rewards.txt");
            action1 = IOHelper.getTransitionMatrices("prob_a1.txt");
            action2 = IOHelper.getTransitionMatrices("prob_a2.txt");
            action3 = IOHelper.getTransitionMatrices("prob_a3.txt");
            action4 = IOHelper.getTransitionMatrices("prob_a4.txt");
            list.add(action1);
            list.add(action2);
            list.add(action3);
            list.add(action4);
            transitionMatrix1 = IOHelper.getTransitionMatrix("prob_a1.txt");
            transitionMatrix2 = IOHelper.getTransitionMatrix("prob_a2.txt");
            transitionMatrix3 = IOHelper.getTransitionMatrix("prob_a3.txt");
            transitionMatrix4 = IOHelper.getTransitionMatrix("prob_a4.txt");
            matrixList.add(transitionMatrix1);
            matrixList.add(transitionMatrix2);
            matrixList.add(transitionMatrix3);
            matrixList.add(transitionMatrix4);
            double[][] identityArray = new double[S][S];
            //initialize identity matrix
            for(int i = 0; i < identityArray.length; i++) {
                for(int j = 0; j < identityArray[0].length; j++) {
                    if(i == j) {
                        identityArray[i][j] = 1;
                    }
                }
            }

            identityMatrix = new Matrix(identityArray);

            //initialize rewards matrix
            double[][] rewardsArray = new double[S][1];
            for(int i = 0; i < identityArray.length; i++) {
                   rewardsArray[i][0] = rewards[i];
            }
            rewardsMatrix = new Matrix(rewardsArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * answer for (a), (b)
     */
    public void valueIteration() {
        double error = Double.MAX_VALUE;
        while(error > ERROR) {
            double[] newValue = new double[S];
            for (int i = 0; i < value.length; i++) {
                //sum(s') p(s' | s, a) * Vk(s')
                double[] p = new double[4];
                //compute sum(s') p(s' | s, a) * Vk(s')
                for (int j = 0; j < A; j++) {
                    double d = 0.0;
                    for (int k = 1; k <= S; k++) {
                        //P(s' | s, a)
                        double pp = list.get(j).get(i + 1, k);
                        // p(s' | s, a) * Vk(s')
                        d += pp * value[k - 1];
                    }
                    p[j] = d;
                }
                double max = getMax(p);
                newValue[i] = rewards[i] + GAMMA * max;
            }
            error = Math.abs(newValue[78] - value[78]);
            for(int l = 0; l < value.length; l++) {
                value[l] = newValue[l];
            }
        }
        printArray(value);

        //compute pi*(s) = argmax(a)[sum(s') P(s' | s,a) * V*(s')
        int[] piStarS = policyImrovement(value);
        printArray(piStarS, value);
    }

    /**
     * CODE for 7.2(c)
     */
    public void policyIteration() {
        //policy for each action, initialize to 0
        int[] piS = new int[S];
        //value function array
        double[] values = new double[S];
        boolean noChange = false;

        int times = 0;
        while(!noChange) {
            noChange = true;
            //SOLVE V(S)
            double[] newValue = new double[S];
            for(int j = 0; j < S; j++) {
                Matrix matrix = matrixList.get(piS[j]);
                double d = 0.0;
                for(int m = 0; m < S; m++) {
                    d+= values[m] * matrix.get(j, m);
                }
                d = d * GAMMA;
                d = d + rewards[j];
                newValue[j] = d;
            }

            for(int n = 0; n < S; n++) {
                values[n] = newValue[n];
            }
            //policy improvement
            for(int i = 0; i < S; i++) {
                double qBest = (values[i] - rewards[i]) / GAMMA;
                for(int k = 0; k < A; k++) {
                    double qsa = 0.0;
                    for(int l = 0; l < S; l++) {
                        qsa += matrixList.get(k).get(i, l) * values[l];
                    }
                    if(qsa - qBest > 0.8) {
                        piS[i] = k;
                        qBest = qsa;
                        noChange = false;
                    }
                }
            }
            times++;
        }
        printArray(piS, values);
    }

    private double getMax(double[] p) {
        double max = -Double.MAX_VALUE;
        for(int i = 0; i < p.length; i++) {
            if(p[i] > max) {
                max = p[i];
            }
        }
        return max;
    }

    private int[] policyImrovement(double[] value) {
        int[] piStarS = new int[S];
        for(int k = 0; k < S; k++) {
            double max = -Double.MAX_VALUE;
            int maxA = -1;
            for (int i = 0; i < A; i++) {
                double a = 0.0;
                for (int j = 0; j < S; j++) {
                    a += list.get(i).get(k + 1, j + 1) * value[j];
                }
                if(a > max) {
                    max = a;
                    maxA = i;
                }
            }
            piStarS[k] = maxA;
        }
        return piStarS;
    }

    private void printArray(double[] array) {
        System.out.println("optimal state value function: ");
        for(int i = 0; i < array.length; i++) {
            if(array[i] != 0)
                System.out.println("State " + (i + 1) + "\t" + array[i]);
        }
    }

    private void printArray(int[] array, double[] value) {
        System.out.println("optimal policy pi: ");
        for(int i = 0; i < array.length; i++) {
            if (value[i] != 0) {
                if (array[i] == WEST) {
                    System.out.println("State " + (i + 1) + "\tWEST");
                } else if (array[i] == NORTH) {
                    System.out.println("State " + (i + 1) + "\tNORTH");
                } else if (array[i] == EAST) {
                    System.out.println("State " + (i + 1) + "\tEAST");
                } else if (array[i] == SOUTH) {
                    System.out.println("State " + (i + 1) + "\tSOUTH");
                }
            }
        }
    }
}
