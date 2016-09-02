import Jama.Matrix;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by billjyc on 2015/12/3.
 */
public class IOHelper {
    private static BufferedReader readTxtFiles(String filePath) {
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader(filePath);
            br = new BufferedReader(fr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return br;
    }

    /**
     * get the transition matrix P(s' | s, a)
     * @param filePath
     * @return
     * @throws IOException
     */
    public static SparseMatrix getTransitionMatrices(String filePath) throws IOException {
        BufferedReader br = readTxtFiles(filePath);
        String line = br.readLine();
        SparseMatrix sparseMatrix = new SparseMatrix(Computation.S + 1);
        while(line != null) {
            String[] splits = line.split("  ");
            /*
               e.g. 12 3 0.925
             */
            int i = Integer.valueOf(splits[0]);
            int j = Integer.valueOf(splits[1]);
            double value = Double.valueOf(splits[2]);
            sparseMatrix.put(i, j, value);
            line = br.readLine();
        }
        return sparseMatrix;
    }

    public static Matrix getTransitionMatrix(String filePath) throws IOException {
        BufferedReader br = readTxtFiles(filePath);
        String line = br.readLine();
        double[][] matrix = new double[Computation.S][Computation.S];
        while(line != null) {
            String[] splits = line.split("  ");
            /*
               e.g. 12 3 0.925
             */
            int i = Integer.valueOf(splits[0]);
            int j = Integer.valueOf(splits[1]);
            double value = Double.valueOf(splits[2]);
            matrix[i - 1][j - 1] = value;
            line = br.readLine();
        }
        return new Matrix(matrix);
    }

    /**
     * get the rewards of each state
     * @param filePath
     * @throws IOException
     */
    public static int[] getRewards(String filePath) throws IOException {
        BufferedReader br = readTxtFiles(filePath);
        int[] rewards = new int[Computation.S];
        String line = br.readLine();
        int count = 0;
        while(line != null) {
            int reward = Integer.valueOf(line);
            rewards[count++] = reward;
            line = br.readLine();
        }
        return rewards;
    }
}
