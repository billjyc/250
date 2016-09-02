import Jama.Matrix;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RefineryUtilities;

/**
 * Created by billjyc on 2015/11/11.
 */
public class Computation {
    public static final int NUM_OF_STATES = 26;
    public static final int NUM_OF_OBSERVATIONS_STATE = 2;
    public static final int NUM_OF_OBSERVATIONS = 75000;

    public Matrix initialStateMatrix;
    public Matrix transitionMatrix;
    public Matrix emissionMatrix;
    public int[] observations = new int[NUM_OF_OBSERVATIONS];
    public char[] mostLikelyState = new char[NUM_OF_OBSERVATIONS];

    public Computation() {
        initialStateMatrix = IOHelper.getMatrix("initialStateDistribution.txt",
                NUM_OF_STATES, 1, IOHelper.SPACE);
        transitionMatrix = IOHelper.getMatrix("transitionMatrix.txt",
                NUM_OF_STATES, NUM_OF_STATES, IOHelper.SPACE);
        emissionMatrix = IOHelper.getMatrix("emissionMatrix.txt",
                NUM_OF_STATES, NUM_OF_OBSERVATIONS_STATE, IOHelper.TAB);
        observations = IOHelper.getObservations("observations.txt");
    }

    /**
     * Recursion in Lit, start from Li1
     * @return
     */
    public double[][] computeLit() {
        double[][] Lit = new double[NUM_OF_STATES][NUM_OF_OBSERVATIONS];
        //Li1
        for(int i = 0; i < NUM_OF_STATES; i++) {
            //pi_i
            double pi_i = initialStateMatrix.get(i, 0);
            //bi_o1
            double bi_o1 = emissionMatrix.get(i, observations[0]);
            //System.out.println(pi_i + "\t" + bi_o1);
            Lit[i][0] = Math.log(pi_i) + Math.log(bi_o1);
            //System.out.println(Lit[i][1]);
        }
        //recursion from Li2 to LiT (T = 75000)
        for(int i = 1; i < NUM_OF_OBSERVATIONS; i++) {
            for(int j = 0; j < NUM_OF_STATES; j++) {
                double max = getMaxInLitRecursion(i - 1, Lit, j);
                Lit[j][i] = max + Math.log(emissionMatrix.get(j, observations[i]));
            }
        }
        return Lit;
    }

    //compute S*
    public void computeSStar() {
        double[][] Lit = computeLit();
        int maxIInTimeT = getArgmaxIInStateTransition(Lit, Lit[0].length - 1, -1);
        mostLikelyState[Lit[0].length - 1] = (char) (maxIInTimeT + 'a');
        //System.out.println(maxI);
        for(int i = Lit[0].length - 2; i >= 0; i--) {
            int maxI = getArgmaxIInStateTransition(Lit, i, (int)(mostLikelyState[i + 1] - 'a'));
            mostLikelyState[i] = (char)(maxI + 'a');
        }
        /*for(int i = 0; i < mostLikelyState.length; i++) {
            System.out.print(mostLikelyState[i] + " ");
        }*/

        //draw the plot
        LineChart chart = new LineChart(createDataset());
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }

    /**
     * prepare data for line chart
     * @return
     */
    private XYDataset createDataset() {
        final XYSeries series = new XYSeries("First");
        for(int i = 0; i < mostLikelyState.length; i++) {
            series.add(i+1, (int)(mostLikelyState[i] - 'a' + 1));
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }

    /**
     * get max(i){Lit + log aij}
     * @param t time
     * @param Lit
     * @param j state in time t
     * @return
     */
    private double getMaxInLitRecursion(int t, double[][] Lit, int j) {
        double max = Lit[0][t] + Math.log(transitionMatrix.get(0, j));
        for(int i = 0; i < NUM_OF_STATES; i++) {
            double d = Lit[i][t] + Math.log(transitionMatrix.get(i, j));
            max = Double.max(max, d);
        }
        return max;
    }

    /**
     * argmax(i) (Lit)
     * @param Lit
     * @param t
     * @param j most likely state at time t+1
     * @return
     */
    private int getArgmaxIInStateTransition(double[][] Lit, int t, int j) {
        int maxIndex = 0;

        if(j == -1) {
            double max = Lit[0][t];
            for(int i = 0; i < NUM_OF_STATES; i++) {
                double d = Lit[i][t];
                if(d > max) {
                    max = d;
                    maxIndex = i;
                }
            }
        } else {
            double max = Lit[0][t] + Math.log(transitionMatrix.get(0, j));
            for(int i = 0; i < NUM_OF_STATES; i++) {
                double d = Lit[i][t] + Math.log(transitionMatrix.get(i, j));
                if(d > max) {
                    max = d;
                    maxIndex = i;
                }
            }
        }
        return maxIndex;
    }
}
