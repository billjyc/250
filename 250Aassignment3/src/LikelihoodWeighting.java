
/**
 * Created by billjyc on 2015/10/16.
 */
public class LikelihoodWeighting {

    private int[] generateSamples(int N, int lengthOfBits) {
        int[] samples = new int[N];
        for(int i = 0; i < N; i++) {
            int sample = (int) (Math.random() * Math.pow(2, lengthOfBits));
            samples[i] = sample;
        }
        return samples;
    }

    /**
     * sum(P(Z=z|b1,b2...b10)
     * @param z
     * @param alpha
     * @return
     */
    public double calDenominator(int z, double alpha, int[] samples) {
        double middleResult = 0.0;

        for(int i = 0; i < samples.length; i++) {
            middleResult += Math.pow(alpha, Math.abs(z - samples[i])); // (alpha) ^ (|z-f(B)|)
        }
        double result = 1.0;
        result = result * middleResult;
        double dd = (1-alpha) / (1+alpha);
        result = result * dd;
        return result;
    }

    public double calNominator(int z, double alpha, int[] samples) {
        double middleResult = 0.0;
        for(int i = 0; i < samples.length; i++) {
            int boo = samples[i] & 64;
            if(boo == 0) {
                continue;
            }
            middleResult += Math.pow(alpha, Math.abs(z - samples[i])); // (alpha) ^ (|z-f(B)|)
        }
        double result = 1.0;
        result = result * middleResult;
        double dd = (1-alpha) / (1+alpha);
        result = result * dd;
        return result;
    }

    public static void main(String[] args) {
        LikelihoodWeighting likelihoodWeighting = new LikelihoodWeighting();
        for(int i = 1; i <= 100; i++) {
            int[] samples = likelihoodWeighting.generateSamples(10000 * i, 10);
            double result = likelihoodWeighting.calNominator(64, 0.35, samples) /
                    likelihoodWeighting.calDenominator(64, 0.35, samples);
            System.out.println(10000 * i + "\t" + result);
        }

    }
}
