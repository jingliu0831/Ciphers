package cipher.myCiphers;

public class CaesarCipher extends MonoCipher {

    public CaesarCipher(int shftParam) {
        super();
        setEncryptAlphabet(shftParam);
    }

    void setEncryptAlphabet(int shiftParam) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < alphabet.length(); i++) {
            char letter = (char) (alphabet.charAt(i) + shiftParam);
            sb.append(letter);
        }

        encryptAlphabet = sb.toString();
    }
}
