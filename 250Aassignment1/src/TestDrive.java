import java.util.HashSet;
import java.util.Set;

/**
 * Created by billjyc on 2015/10/4.
 */
public class TestDrive {
    public static void main(String[] args){
        Counting counting = new Counting();
        char[] c = {'_','_','_','_','_'};
        Set<Character> wrongLetter = new HashSet<>();

        Set<Character> wrongLetter2 = new HashSet<>();
        wrongLetter2.add('E');
        wrongLetter2.add('O');

        char[] c3 = {'D','_','_','I','_'};

        Set<Character> wrongLetter4 = new HashSet<>();
        wrongLetter4.add('A');

        char[] c5 = {'_','U','_','_','_'};
        Set<Character> wrongLetter5 = new HashSet<>();
        wrongLetter5.add('A');
        wrongLetter5.add('I');
        wrongLetter5.add('E');
        wrongLetter5.add('O');
        wrongLetter5.add('S');

        char[] c6 = {'N','E','_','E','_','_'};
        wrongLetter.add('R');
        wrongLetter.add('S');

        Evidence e = new Evidence(c6,wrongLetter);
        //counting.printData("hw1_word_counts_05.txt");
        counting.calPredictiveProb(e, "hw1_word_counts_06.txt");
    }
}
