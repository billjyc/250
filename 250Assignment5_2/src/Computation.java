import java.util.ArrayList;

/**
 * Created by billjyc on 2015/11/7.
 */
public class Computation {
    double[] p = new double[10];
    ArrayList<ArrayList<Integer>> x = new ArrayList<>();
    ArrayList<ArrayList<Integer>> y = new ArrayList<>();
    public static final int NUM_OF_X = 10;

    public Computation() {
        for(int i = 0; i < p.length; i++) {
            p[i] = 0.5;
        }
        x = IOHelper.readTxtFiles("X.txt");
        y = IOHelper.readTxtFiles("Y.txt");
    }

    public double computeLoglikelihood() {
        double loglikelihood = 0.0;
        for(int i = 0; i < y.size(); i++) {
            int yy = y.get(i).get(0);
            ArrayList<Integer> xi = x.get(i);
            double pp = 1.0; //P(Y = y | X = x)
            //product(i): (1 - pi) ^ xi
            for(int j = 0; j < xi.size(); j++) {
                pp = pp * Math.pow(1 - p[j], xi.get(j));
            }
            if(yy == 1) {
                pp = 1 - pp;
            }
            loglikelihood += Math.log(pp);
        }
        return loglikelihood / y.size();
    }

    public void updatePi() {
        double[] newP = new double[10];
        for(int i = 0; i < NUM_OF_X; i++) {
            double result = 0.0;
            for(int j = 0; j < x.size(); j++) {
                //double pp = 1.0;
                if(y.get(j).get(0) == 0) {
                    continue;
                }
                double pp = y.get(j).get(0) * x.get(j).get(i) * p[i];  //y * xi * pi
                double d = 1.0; //d = product(j) (1 - pj) ^ xj
                for(int k = 0; k < NUM_OF_X; k++) {
                    d *= Math.pow(1 - p[k], x.get(j).get(k));

                }
                d = 1 - d;
                pp = pp / d;
                result += pp;
            }
            int ti = computeTi(i);
            newP[i] = result / ti;
        }
        for(int k = 0; k < NUM_OF_X; k++) {
           p[k] = newP[k];
        }
    }

    /**
     * the number of examples in which Xi = 1
     * @param i
     * @return
     */
    private int computeTi(int i) {
        int count = 0;
        for(int j = 0; j < x.size(); j++) {
            if(x.get(j).get(i) == 1) {
                count++;
            }
        }
        return count;
    }
}
