import java.util.Set;

/**
 * Created by billjyc on 2015/10/4.
 */
public class Evidence {
    private char[] chars; //correct letters and their position
    private Set<Character> wrongLetters; //wrong guessed letters

    public Evidence(char[] chars, Set<Character> wrongLetters) {
        this.chars = chars;
        this.wrongLetters = wrongLetters;
    }

    public char[] getChars() {
        return chars;
    }

    public void setChars(char[] chars) {
        this.chars = chars;
    }

    public Set<Character> getWrongLetters() {
        return wrongLetters;
    }

    public void setWrongLetters(Set<Character> wrongLetters) {
        this.wrongLetters = wrongLetters;
    }
}
