import java.io.IOException;
import java.util.*;

/**
 * Created by billjyc on 2015/10/4.
 */
public class Counting {
    private FileData readData(String filePath) {
        FileData fileData = null;
        try {
            fileData = IOHelper.readTxtFiles(filePath);
        }catch (IOException ie) {
            ie.printStackTrace();
        }
        return fileData;
    }

    /**
     * print out 8 least frequent and 8 most frequent words
     * @param filePath
     */
   public void printData(String filePath) {
        FileData fileData = readData(filePath);
        System.out.println("eight least frequent words: ");
        Collections.sort(fileData.getWordList());  //sort the word list by their count in ascending order
        Iterator iterator = fileData.getWordList().iterator();

        //print out eight least-frequent words
        for(int i = 0; i < 8; i++) {
            WordsData data = (WordsData) iterator.next();
            System.out.println(data.getWord() + " " + (double) data.getCount() / fileData.getTotalCounts());
        }

        System.out.println("eight most frequent words: ");
        Collections.sort(fileData.getWordList(), new DescCountComparator());
        Iterator iterator2 = fileData.getWordList().iterator();
        //print out eight least-frequent words
        for(int i = 0; i < 8; i++) {
            WordsData data = (WordsData) iterator2.next();
            System.out.println(data.getWord() + " " + (double) data.getCount() / fileData.getTotalCounts());
        }
    }

    /**
     * CALCULATE posterior probability
     * @param evidence
     * @param wd
     * @param wordList
     * @param totalCount
     */
     private double calPosteriorProb(Evidence evidence, WordsData wd, int totalCount, ArrayList<WordsData> wordList) {
        double result;

        //summation of P(E|W=w')P(W=w')
        double sum1 = calSummation(evidence, wordList, totalCount);
        //P(E|W=w)
        double evidenceGivenWord = cal2(wd, evidence, totalCount);

        result = evidenceGivenWord / sum1;
        return result;
    }

    /**
     * predictive probability
     * cal summation of P(Li=l for some i={1,2,3,4,5} | W=w)P(W=w| E)
     * @param e
     * @param filePath
     */
    public void calPredictiveProb(Evidence e, String filePath) {
        FileData fileData = readData(filePath);
        ArrayList<WordsData> wordList = fileData.getWordList();
        char[] c = e.getChars();
        Set<Character> wrongLetters = e.getWrongLetters();
        //the letters which have been guessed correctly
        Set<Character> guessedLetters = new HashSet<>();

        for (int i = 0; i < c.length; i++) {
            if (c[i] != '_') {
                guessedLetters.add(c[i]);
            }
        }

        for(int j = 0; j < 26; j++) {
            double result = 0.0;
            char cc = (char) ('A' + j);
            for (WordsData wd : wordList) {

                //P(Li=l for some i={1,2,3,4,5} | W=w)
                double former = 0.0;
                boolean isCompatible = false;

                if(guessedLetters.contains(cc) || wrongLetters.contains(cc)) {
                    isCompatible = false;
                } else {
                    for (int i = 0; i < wd.getWord().length(); i++) {
                        if (c[i] != '_') {
                            if (c[i] != wd.getWord().charAt(i)) {  //if the letter which has been filled is not corresponding
                                isCompatible = false;
                                break;
                            }
                        } else {
                            if (guessedLetters.contains(wd.getWord().charAt(i)) &&
                                    wrongLetters.contains(wd.getWord().charAt(i))) {
                                isCompatible = false;
                                break;
                            }
                            if(wd.getWord().charAt(i) == cc) {
                                isCompatible = true;
                                break;
                            }
                        }

                    }
                }
                if (isCompatible) {
                    former = 1.0;
                }

                //posterior probability
                double postProb = calPosteriorProb(e, wd, fileData.getTotalCounts(), wordList);
                result += former * postProb;

            }
            System.out.println(cc + ": " + result);
        }
    }

    /**
     * calculate summation of P(E|W=w')P(W=w')
     * @param e
     * @param wordList
     * @param totalCount
     */
    private double calSummation(Evidence e, ArrayList<WordsData> wordList, int totalCount) {
        //summation of P(E|W=w')P(W=w')
        double sum = 0.0;

        for(WordsData wd : wordList) {
            sum += cal2(wd, e, totalCount);
        }
        return sum;
    }

    /**
     * calculate P(E|W=w)P(W=w)
     * @param wd
     * @param e
     * @param totalCount
     * @return
     */
    private double cal2(WordsData wd, Evidence e, int totalCount) {
        char[] c = e.getChars();
        //P(W=w)
        double wordProb = (double) wd.getCount() / totalCount;
        //P(E|W = w)
        double evidenceGivenWord = 0.0;
        //determine whether the evidence is compatible with the word
        boolean isCompatible = false;
        int compaCount = 0;
        for(int i = 0; i < c.length; i++) {
            //if the word contains letters that are incorrectly guessed
            if(e.getWrongLetters().contains(wd.getWord().charAt(i))) {
                break;
            }
            if((c[i] != '_' && wd.getWord().charAt(i) == c[i]) || (c[i] == '_')) {
               compaCount++;
            }
        }
        if(compaCount == c.length) {
            isCompatible = true;
        }
        //if isCompatible = true, P(E|W = w') = 1
        if(isCompatible) {
            evidenceGivenWord = 1.0;
        }
        return evidenceGivenWord * wordProb;
    }
}
