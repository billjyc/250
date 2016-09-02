/**
 * Created by billjyc on 2015/11/7.
 */
public class Main {
    public static void main(String[] args) {
        Computation computation = new Computation();
        for(int i = 0; i <= 64; i++) {
            System.out.println("Iteration " + (i) + ": " +computation.computeLoglikelihood());
            computation.updatePi();
        }

    }
}
