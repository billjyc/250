import java.io.IOException;

/**
 * Created by billjyc on 2015/10/18.
 */
public class TestDrive {
    public static void main(String[] args) {
        Calculation calculation = new Calculation();
        String sen1 = "The stock market fell by one hundred points last week";
        String sen2 = "The sixteen officials sold fire insurance";
        try {
            //calculation.computeUnigramML();
            //calculation.computeBigramML("THE");
            //calculation.computePu(sen1);
            //calculation.computePb(sen1);
            //calculation.computePu(sen2);
            //calculation.computePb(sen2);
            for(int i = 0; i <= 100; i++) {
                calculation.computePm(sen2, 0.0 + 0.01 * i);
            }
            //calculation.computePm(sen2, 0.0);
            //calculation.computePm(sen2, 1.0);
            //calculation.computePm(sen2, 0.5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
