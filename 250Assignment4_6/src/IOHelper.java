import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by billjyc on 2015/10/30.
 */
public class IOHelper {
    public static ArrayList<String[]> readTxtFiles(String filePath) {
        ArrayList<String[]> list = null;
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            list = new ArrayList<>();

            String line = br.readLine();
            while (line != null) {
                String[] bitItem = line.split(" ");
                list.add(bitItem);
                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
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
