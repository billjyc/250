import Jama.Matrix;

import java.util.ArrayList;

/**
 * Created by billjyc on 2015/10/30.
 */
public class Computation {
    public static final int TIMES = 800;
    public static final double ITA = 0.02 / TIMES;
    ArrayList<String[]> x3_t;
    ArrayList<String[]> x5_t;
    private double computeSigmoid(double z) {
        double result = 1.0 / (1 + Math.exp(-z));
        return result;
    }

    /**
     * code for 4.6(a)
     * @return
     */
    public Matrix linearRegression() {
        x3_t = IOHelper.readTxtFiles("new_train3.txt");
        x5_t = IOHelper.readTxtFiles("new_train5.txt");

        //initial w
        double [][] wArray = new double[1][64];
        for(int i = 0; i < wArray.length; i++) {
            for(int j = 0; j < wArray[i].length; j++) {
                wArray[i][j] = 1.0;
            }
        }
        Matrix w = new Matrix(wArray);

        for(int i = 0; i < TIMES; i++) {
            //System.out.println("Iteration " + (i + 1) + ": ");
            w = w.plus(computeGradient(x3_t, x5_t, w).times(ITA));
            double loglikelihood = logLikelihood(w);
            System.out.println(loglikelihood);
            //IOHelper.printMatrix(w.getArray());
        }

        //print w as a 8 * 8 matrix
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                System.out.print(w.getArray()[0][8 * i + j] + "\t");
            }
            System.out.println();
        }
        return w;
    }

    /**
     * calculate gradient of log-likelihood on w
     * sum(t) (Yt - sigmoid(w * xt)) * xt
     * @param x3_t
     * @param x5_t
     * @param w
     * @return
     */
    public Matrix computeGradient(ArrayList<String[]> x3_t, ArrayList<String[]> x5_t, Matrix w) {
        Matrix result = new Matrix(new double[64][1]);
        for(int t = 0; t < x3_t.size() + x5_t.size(); t++) {
            Matrix xt;
            double yt;
            if(t < x3_t.size()) {
                xt = getXt(x3_t.get(t));
                yt = 1.0;
            } else {
                xt = getXt(x5_t.get(t - x3_t.size()));
                yt = 0.0;
            }

            double wTimesxt = computewTimesxt(w, xt);
            Matrix temp = xt.times(yt - computeSigmoid(wTimesxt));
            result = result.plus(temp);
        }
        return result.transpose();
    }

    private Matrix getXt(String[] line) {
        double[][] xtArray = new double[64][1];
        for(int i = 0; i < xtArray.length; i++) {
            for(int j = 0; j < xtArray[i].length; j++) {
                xtArray[i][j] = Double.valueOf(line[i]);
            }
        }
        return new Matrix(xtArray);
    }

    /**
     * w times xt
     * @param w
     * @param xt
     * @return
     */
    private double computewTimesxt(Matrix w, Matrix xt) {
        Matrix result = w.times(xt);
        return result.getArray()[0][0];
    }

    private Matrix computeXtimesXt(double[][] xtArray) {
        Matrix xt = new Matrix(xtArray);
        Matrix result = xt.times(xt.transpose());
        return result;
    }

    /**
     * code for 4.6(b)
     * @param w
     * @return
     */
    public double test(Matrix w) {
        ArrayList<String[]> test3 = IOHelper.readTxtFiles("new_test3.txt");
        ArrayList<String[]> test5 = IOHelper.readTxtFiles("new_test5.txt");
        ArrayList<String[]> test = new ArrayList<>();
        test.addAll(test3);
        test.addAll(test5);


        int error = 0;
        for(int t = 0; t < test.size(); t++) {
            Matrix xt = getXt(test.get(t));
            double p = computeSigmoid(computewTimesxt(w, xt));
            if(t < test3.size()) {
                if(p < 0.5) {  //P(Y=1|X=xt) < 0.5
                    error++;
                }
            } else {
                if(1 - p <= 0.5) {
                    error++;
                }
            }
            //System.out.println(p);
        }
        return (double)error / test.size();
    }

    private double logLikelihood(Matrix w) {
        //ArrayList<String[]> x3_t = IOHelper.readTxtFiles("new_train3.txt");
        //ArrayList<String[]> x5_t = IOHelper.readTxtFiles("new_train5.txt");
        double result = 0.0;
        for(int t = 0; t < x3_t.size() + x5_t.size(); t++) {
            Matrix xt;
            double yt;
            if(t < x3_t.size()) {
                xt = getXt(x3_t.get(t));
                yt = 1.0;
            } else {
                xt = getXt(x5_t.get(t - x3_t.size()));
                yt = 0.0;
            }
            double sigmoid = computeSigmoid(w.times(xt).getArray()[0][0]);
            double sigmoid2 = computeSigmoid(-w.times(xt).getArray()[0][0]);
            double temp = yt * Math.log(sigmoid) + (1 - yt) * Math.log(sigmoid2);
            result += temp;
        }

        return result;
    }
}
