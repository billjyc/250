import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

/**
 * Created by billjyc on 2015/11/7.
 */
public class IOHelper {
    public static ArrayList<ArrayList<Integer>> readTxtFiles(String filePath) {
        FileReader fr = null;
        BufferedReader br = null;
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        try {
            fr = new FileReader(filePath);
            br = new BufferedReader(fr);
            String line = br.readLine();
            while(line != null) {
                //System.out.println(line);
                String[] item = line.split("   ");
                ArrayList<Integer> items = new ArrayList<>();
                for(int i = 1; i < item.length; i++) {
                    items.add(Integer.valueOf(item[i]));
                }
                result.add(items);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*public ArrayList<ArrayList<Integer>> getX(String filePath) throws IOException {
        BufferedReader br = readTxtFiles(filePath);
        String line = br.readLine();
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        while(line != null) {
            String[] item = line.split("   ");
            ArrayList<Integer> items = new ArrayList<>();
            for(int i = 0; i < item.length; i++) {
                items.add(Integer.valueOf(item[i]));
            }
            result.add(items);
        }
        return result;
    }*/
}
