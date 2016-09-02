/**
 * Created by billjyc on 2015/11/7.
 */
public class Computation {
    public static final int CONVERGE_TIME = 50;

    /**
     * code for 5.3(f)
     * @param x0
     */
    public void converage(int x0) {
        double xn = x0;
        for(int i = 0; i < CONVERGE_TIME; i++) {
            double temp = (Math.exp(2 * xn) - 1) / (Math.exp(2 * xn) + 1);
            xn = xn - temp;
            System.out.println(xn);
        }
    }

    /**
     * code for 5.3(g), newton method
     * @param x0
     */
    public void converge2(int x0) {
        double xn = x0;
        for(int i = 0; i < CONVERGE_TIME; i++) {
            double temp = (Math.exp(4 * xn) - 1) / (4 * Math.exp(2 * xn));
            xn = xn - temp;
            System.out.println(xn);
        }
    }

    /**
     * code for 5.3(k)
     * @param x0
     */
    public void converge3(int x0) {
        double xn = x0;
        for(int i = 0; i < CONVERGE_TIME; i++) {
            System.out.println(gx(xn));
            double temp = 0.0;
            for(int j = 1; j <= 10; j++) {
                temp += (Math.exp(2*xn + 2 / Math.sqrt(j)) - 1) / (Math.exp(2*xn + 2 / Math.sqrt(j)) + 1);
            }
            xn = xn - temp / 10;
            System.out.println(xn);
        }
    }

    /**
     * code for 5.3(h)
     * @param x
     * @return
     */
    public double gx(double x) {
        double result = 0.0;
        for(int i = 1; i <= 10; i++) {
           result += Math.log((Math.exp(x + 1 / Math.sqrt(i)) + Math.exp(-x - 1 / Math.sqrt(i))) / 2);
        }
        result /= 10;
        return result;
    }
}
