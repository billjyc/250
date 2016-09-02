import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Used for the reading of the txt file
 * Created by billjyc on 2015/10/4.
 */
public class IOHelper {
    public static FileData readTxtFiles(String filePath) throws IOException{
        FileReader fr = new FileReader(filePath);
        BufferedReader textReader = new BufferedReader(fr);

        String line = textReader.readLine();
        FileData fileData = new FileData();
        /*
        read by lines and add each record into fileData
         */
        while(line != null) {
            String[] data = line.split(" ");
            String word = data[0];
            int count = Integer.valueOf(data[1]);

            WordsData wordsData = new WordsData(word, count);
            fileData.setTotalCounts(fileData.getTotalCounts() + count);
            fileData.getWordList().add(wordsData);

            line = textReader.readLine();
        }
        return fileData;
    }
}
