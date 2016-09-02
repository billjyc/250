import Jama.Matrix;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by billjyc on 2015/11/11.
 */
public class IOHelper {
    public static final String SPACE = " ";
    public static final String TAB = "\t";
    public static void printMatrix(Matrix matrix) {
        double[][] array = matrix.getArray();
        if(array == null || array.length == 0 || array[0].length == 0) {
            System.err.println("matrix is null!");
            return;
        }
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
    }

    /**
     * get observations data
     * @param filePath
     * @return
     */
    public static int[] getObservations(String filePath) {
        int[] observations = new int[Computation.NUM_OF_OBSERVATIONS];
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            String[] observasionS = line.split(SPACE);
            for(int i = 0; i < observasionS.length; i++) {
                observations[i] = Integer.valueOf(observasionS[i]);
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }
        return observations;
    }

    /**
     * get matrix from txt files
     * @param filePath
     * @param row
     * @param column
     * @return
     */
    public static Matrix getMatrix(String filePath, int row, int column, String split) {
        Matrix matrix = null;
        double[][] array = new double[row][column];
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            int count = 0;
            while(line != null) {
                String[] rowS = line.split(split);
                for(int i = 0; i < column; i++) {
                    array[count][i] = Double.valueOf(rowS[i]);
                }
                line = br.readLine();
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        matrix = new Matrix(array);
        return matrix;
    }
}
