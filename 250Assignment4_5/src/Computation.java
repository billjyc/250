import Jama.Matrix;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by billjyc on 2015/10/30.
 */
public class Computation {
    ArrayList<Double> actual2000;
    ArrayList<Double> actual2001;

    public void init() {
        try {
            actual2000 = IOHelper.readTxtFiles("nasdaq00.txt");
            actual2001 = IOHelper.readTxtFiles("nasdaq01.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void computeNasdaq() {
        init();
        Matrix w = computePosteriorProb(actual2000);
        double[] result = computeSquaredError(w);
        System.out.println(result[0] + " " + result[1]);
    }

    /**
     * code for 4.5(a)
     * compute w = A^(-1) * b
     * @param stocks
     */
    public Matrix computePosteriorProb(ArrayList<Double> stocks) {
        double[][] arrayA = new double[3][3];
        Matrix A = new Matrix(arrayA);
        double[][] arrayB = new double[3][1];
        Matrix b = new Matrix(arrayB);

        for(int t = 3; t < stocks.size(); t++){
            double[][] XtArray = new double[3][1];
            double Xt = stocks.get(t);
            XtArray[0][0] = stocks.get(t - 1);
            XtArray[1][0] = stocks.get(t - 2);
            XtArray[2][0] = stocks.get(t - 3);

            Matrix XtMatrix = new Matrix(XtArray);
            A = A.plus(XtMatrix.times(XtMatrix.transpose()));
            b = b.plus(XtMatrix.times(Xt));
        }

        Matrix w = A.inverse().times(b);
        IOHelper.printMatrix(w.getArray());

        return w;
    }

    /**
     * code for 4.5(b)
     * Predict the nasdac in 2001, using the coefficients estimated in part(a)
     * Xt = (a1 * Xt-1 + a2 * Xt-2 + a3 * Xt-3)
     * @param w a1, a2, a3 computed in part(a)
     */
    public ArrayList<Double> predictNasdac2001(Matrix w, ArrayList<Double> actual) throws IOException {
        //int length = stocks.size();
        ArrayList<Double> estimated = new ArrayList<>();
        int length = actual.size();
        //we need data on last 3 days in 2000 for prediction
        for(int i = 0; i < 3; i++) {
            //System.out.println(stocks.get(length - i));
            estimated.add(actual2000.get(i));
        }

        double[][] wArray = w.getArray();
        //IOHelper.printMatrix(wArray);
        for(int i = 3; i < length; i++) {
            double temp = 0.0;
            for(int j = 0; j < wArray.length; j++) {
                temp += wArray[j][0] * actual.get(i - j - 1);
            }
            estimated.add(temp);
        }

       /* for(int k = 0; k < estimated.size(); k++) {
            System.out.println(estimated.get(k));
        }*/

        return estimated;
    }

    public double[] computeSquaredError(Matrix w) {
        double[] result = new double[2];
        //ArrayList<Double> actual2001 = new ArrayList<>();
        ArrayList<Double> estimate = new ArrayList<>();
        ArrayList<Double> actual = new ArrayList<>();
        actual.addAll(actual2000);
        actual.addAll(actual2001);
        //double result = 0.0;
        int length = 0;
        try {
            estimate = predictNasdac2001(w, actual);

            length = actual.size();
            for(int i = 0; i < length; i++) {
                double error = Math.pow(actual.get(i) - estimate.get(i), 2);
                if(i < actual2000.size()) {
                    result[0] += error;
                }else {
                    result[1] += error;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        result[0] /= length;
        result[1] /= length;
        return result;
    }
}
