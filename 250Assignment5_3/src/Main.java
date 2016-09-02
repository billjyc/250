/**
 * Created by billjyc on 2015/11/7.
 */
public class Main {
    public static void main(String[] args) {
        Computation computation = new Computation();
        //computation.converage(-2);
        //computation.converage(3);
        //computation.converge2(3);
        computation.converge3(3);

        /*for(int i = 0; i <= 40; i++) {
            double x = -2 + 0.1 * i;
            System.out.println((x) + "\t" + computation.gx(x));
        }*/
    }
}
