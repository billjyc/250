import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by billjyc on 2015/10/18.
 */
public class IOHelper {
    public static UnigramFileData readUnigramFile(String vocabFilePath, String unigramFilePath) throws IOException {
        FileReader vocabReader = new FileReader(vocabFilePath);
        FileReader unigramReader = new FileReader(unigramFilePath);

        BufferedReader unigramBf = new BufferedReader(unigramReader);
        BufferedReader vocabBf = new BufferedReader(vocabReader);
        int totalCount = 0;
        HashMap<String, Integer> map = new HashMap<>();

        //calculate the total count of the tokens
        String line = unigramBf.readLine();
        String vocabLine = vocabBf.readLine();
        while(line != null && vocabLine != null) {
            int count = Integer.valueOf(line);
            totalCount += count;

            map.put(vocabLine, count);

            line = unigramBf.readLine();
            vocabLine = vocabBf.readLine();
        }
        //System.out.println(totalCount);

        UnigramFileData unigramFileData = new UnigramFileData(totalCount, map);
        return unigramFileData;
    }

    /**
     *
     * @param vocabFilePath
     * @param bigramPath
     * @param unigramPath
     * @param w1 the word that is followed
     * @return
     */
    public static BigramFileData readBigramFile(String vocabFilePath, String bigramPath, String unigramPath,
                                                String w1) {
        FileReader vocabFileReader;
        FileReader bigramFileReader;
        BufferedReader vocabBf = null;
        BufferedReader bigramBf = null;
        HashMap<String, Integer> countMap = null;
        //key: token; value: index of the token
        HashMap<String, Integer> indexMap = new HashMap<>();
        HashMap<Integer, String> reverseIndexMap = new HashMap<>();
        int w1Count = 0;

        //key: (index of w2), value: (#count that w1 is followed by w2)
        HashMap<Integer, Integer> w1w2CountMap = new HashMap<>();
        int index = 0;
        try {
            UnigramFileData unigramFileData = readUnigramFile(vocabFilePath, unigramPath);
            countMap = unigramFileData.getCountTables();

            vocabFileReader = new FileReader(vocabFilePath);
            bigramFileReader = new FileReader(bigramPath);

            vocabBf = new BufferedReader(vocabFileReader);
            bigramBf = new BufferedReader(bigramFileReader);

            /**
             * link each token and its index
             */
            String vocabLine = vocabBf.readLine();
            while(vocabLine != null) {
                index++;
                indexMap.put(vocabLine, index);
                reverseIndexMap.put(index, vocabLine);
                vocabLine = vocabBf.readLine();
            }

            w1Count = countMap.get(w1);
            int w1Index = indexMap.get(w1);
            //System.out.println(w1Index);

            String bigramLine = bigramBf.readLine();
            while(bigramLine != null) {

                String[] bigrams = bigramLine.split("\t");
                int indexOfW1 = Integer.valueOf(bigrams[0]);
                int indexOfW2 = Integer.valueOf(bigrams[1]);
                int count = Integer.valueOf(bigrams[2]);

                if(indexOfW1 == w1Index) {
                    w1w2CountMap.put(indexOfW2, count);
                }
                bigramLine = bigramBf.readLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        BigramFileData bigramFileData = new BigramFileData(indexMap, reverseIndexMap, w1w2CountMap, w1Count);
        return bigramFileData;
    }
}
