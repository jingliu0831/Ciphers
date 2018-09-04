package cipher.myCiphers;

public class CaesarCipher extends MonoCipher {

    public CaesarCipher(int shftParam) {
        super();
        setEncryptAlphabet(shftParam);
    }

    void setEncryptAlphabet(int shiftParam) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            int shiftedChar = 'A' + i + shiftParam;
            char letter = (char) ((shiftedChar > 'Z') ? shiftedChar - 'Z' - 1 + 'A' : shiftedChar);
            sb.append(letter);
        }

        encryptAlphabet = sb.toString();
    }
}
