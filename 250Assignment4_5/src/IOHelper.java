import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Created by billjyc on 2015/10/30.
 */
public class IOHelper {
    public static ArrayList<Double> readTxtFiles(String filePath) throws IOException {
        ArrayList<Double> stocksList = new ArrayList<>();
        if(filePath == null || filePath == "") {
            System.err.println("file path is required!");
            return stocksList;
        }
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine();


        while(line != null) {
            double stock = Double.valueOf(line);
            stocksList.add(stock);
            line = br.readLine();
        }
        return stocksList;
    }

    public static void printMatrix(double[][] matrixArray) {
        for(int i = 0; i < matrixArray.length; i++) {
            for(int j = 0; j < matrixArray[i].length; j++) {
                System.out.print(matrixArray[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
