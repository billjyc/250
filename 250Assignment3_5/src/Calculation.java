import java.io.IOException;
import java.util.*;

/**
 * Created by billjyc on 2015/10/18.
 */
public class Calculation {
    public static final String VOCAB = "vocab.txt";
    public static final String UNIGRAM = "unigram.txt";
    public static final String BIGRAM = "bigram.txt";
    public void computeUnigramML() throws IOException {
        UnigramFileData unigramFileData = IOHelper.readUnigramFile(VOCAB, UNIGRAM);
        HashMap<String, Integer> countTable = unigramFileData.getCountTables();

        Iterator iter = countTable.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String token = (String)entry.getKey();
            Integer count = (Integer)entry.getValue();

            //compute ML
            double ml = (double)count / unigramFileData.getTotalCount();

            if(token.startsWith("M")) {
                System.out.println(token + "\t" + ml);
            }
        }
    }

    public void computeBigramML(String w1) throws IOException {
        BigramFileData bigramFileData = IOHelper.readBigramFile(VOCAB, BIGRAM, UNIGRAM, w1);

        HashMap<Integer, Integer> countW1W2Map = bigramFileData.getW1W2CountMap();
        HashMap<Integer, String> reverseIndexMap = bigramFileData.getReverseIndexMap();
        int w1Count = bigramFileData.getCountOfW1();

        ByValueComparator byValueComparator = new ByValueComparator(countW1W2Map);
        TreeMap<Integer, Integer> resultMap = new TreeMap<>(byValueComparator);
        resultMap.putAll(countW1W2Map);

        int count = 0;
        for(Integer index : resultMap.keySet()) {
            if(count >= 10) {
                break;
            }
            String word = reverseIndexMap.get(index);
            int w1w2Count = resultMap.get(index);
            double prob = (double)w1w2Count / w1Count;
            System.out.println(word + ": " + prob);
            count++;
        }

    }

    /**
     * compute the log-likelihoods under the unigram models
     * @param sentence
     * @throws IOException
     */
    public void computePu(String sentence) throws IOException {
        String[] words = sentence.split(" ");
        UnigramFileData unigramFileData = IOHelper.readUnigramFile(VOCAB, UNIGRAM);
        int totalCount = unigramFileData.getTotalCount();
        HashMap<String, Integer> countMap = unigramFileData.getCountTables();
        double result = 1.0;
        for(String word: words) {
            word = word.toUpperCase();
            int wordCount = countMap.get(word);
            double prob = (double) wordCount / totalCount;
            result = result * prob;
        }
        result = Math.log(result);
        System.out.println("LOGPu(" + sentence + ") = " + result);
    }



    /**
     * compute the log-likelihoods under the bigram model
     * @param sentence
     * @throws IOException
     */
    public void computePb(String sentence) throws IOException {
        sentence = sentence.toUpperCase();
        sentence = "<s> " + sentence;
        String[] words = sentence.split(" ");

        double result = 1.0;

        for (int i = 0; i < words.length - 1; i++) {
           double pb = computeSinglePb(words, i);

            result = result * pb;
        }
        result = Math.log(result);
        System.out.println("LOGPb(" + sentence + ") = " + result);
    }

    /**
     * compute pb(words[i+1] | words[i])
     * @param words
     * @param i
     * @return
     */
    private double computeSinglePb(String[] words, int i) {
        BigramFileData bigramFileData = IOHelper.readBigramFile(VOCAB, BIGRAM, UNIGRAM, words[i]);
        HashMap<Integer, Integer> countW1W2Map = bigramFileData.getW1W2CountMap();
        HashMap<String, Integer> indexMap = bigramFileData.getIndexMap();
        HashMap<Integer, String> reverseIndexMap = bigramFileData.getReverseIndexMap();
        int w1Count = bigramFileData.getCountOfW1();

        String w2 = words[i + 1];
        int w2Index = indexMap.get(w2);

        int countW1W2;
        if(countW1W2Map.containsKey(w2Index)) {
            countW1W2 = countW1W2Map.get(w2Index);
        } else {
            countW1W2 = 0;
            //System.out.println("Pair(" + words[i] + ", " + words[i+1] + ") is not observed in the training " +
                    //"corpus");
        }

        double pb = (double) countW1W2 / w1Count;
        return pb;
    }

    /**
     * compute log-likelihood under mixture model
     * @param sentence
     * @throws IOException
     */
    public void computePm(String sentence, double ramda) throws IOException {
        if(ramda > 1 || ramda < 0) {
            return;
        }
        sentence = sentence.toUpperCase();
        sentence = "<s> " + sentence;
        String[] words = sentence.split(" ");

        UnigramFileData unigramFileData = IOHelper.readUnigramFile(VOCAB, UNIGRAM);
        int totalCount = unigramFileData.getTotalCount();
        HashMap<String, Integer> countMap = unigramFileData.getCountTables();

        double result = 1.0;

        for(int i = 1; i <= words.length - 1; i++) {
            String w2 = words[i];
            String w1 = words[i - 1];

            int w2Count = countMap.get(w2);

            double pu = (double)w2Count / totalCount;
            double pb = computeSinglePb(words, i - 1);
            double pm = ramda * pu + (1 - ramda) * pb;
            result = result * pm;
        }
        result = Math.log(result);
        System.out.println(ramda + "\t" +result);
    }

    static class ByValueComparator implements Comparator<Integer> {
        HashMap<Integer, Integer> base_map;

        public ByValueComparator(HashMap<Integer, Integer> base_map) {
            this.base_map = base_map;
        }

        public int compare(Integer i, Integer j) {
            if (!base_map.containsKey(i) || !base_map.containsKey(j)) {
                return 0;
            }

            if (base_map.get(i) < base_map.get(j)) {
                return 1;
            } else if (base_map.get(i) == base_map.get(j)) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
