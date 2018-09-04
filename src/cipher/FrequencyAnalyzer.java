package cipher;

import cipher.myCiphers.CaesarCipher;

/** Analyses the frequency of different characters in some ciphertext and
 *  constructs a plausible cipher that could have produced that ciphertext.
 */
public class FrequencyAnalyzer {
    int[] frequencies;

    public FrequencyAnalyzer() {
        frequencies = new int[26];
    }

    /**
     * Adds an occurrence of a character to the analyzer if it is a letter,
     * otherwise ignore.
     * @param c the character to be odded
     */
    public void addChar(char c) {
        if (!Character.isLetter(c)) {
            return;
        }

        char upperCaseChar = Character.toUpperCase(c);
        frequencies[upperCaseChar - 'A']++;
    }

    /**
     * Returns the given character's current frequency in the analyzer.
     * @return the frequency of the given character
     */
    public int getFrequency(char c) {
        if (!Character.isLetter(c)) {
            return -1;
        }

        char upperCaseChar = Character.toUpperCase(c);
        return frequencies[upperCaseChar - 'A'];
    }

    /**
     * Returns a Caesar cipher constructed using data from the two
     * analyzers.
     * @param sample an analyzer containing the results of scanning some
     *               plaintext.
     * @param encrypted an analyzer containing the results of scanning
     *                  encrypted text(s) in the encrypted 
     * @return a Caesar cipher that is a best estimate of how {@code
     *         encrypted} was generated.
     */
    public static AbstractCipher getCipher(FrequencyAnalyzer sample,
            FrequencyAnalyzer encrypted) {

        // normally the most frequent char is 'E' in letters, so we can check shiftparam by comparing most frequent char
        int shiftParam = encrypted.mostFrequentChar() - sample.mostFrequentChar();

        return new CaesarCipher(shiftParam);
    }

    private int mostFrequentChar() {
        int mostFrequenctCharIndex = 0;
        int maxFrequency = 0;

        for (int i = 0; i < 26; i++) {
            if (frequencies[i] > maxFrequency) {
                mostFrequenctCharIndex = i;
            }
        }

        return (char) (mostFrequenctCharIndex + 'A');
    }
}
