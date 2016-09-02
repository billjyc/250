import Jama.Matrix;

/**
 * Created by billjyc on 2015/10/31.
 */
public class TestDrive {
    public static void main(String[] args) {
        Computation computation = new Computation();
        Matrix w = computation.linearRegression();
        System.out.println(computation.test(w));
    }
}
