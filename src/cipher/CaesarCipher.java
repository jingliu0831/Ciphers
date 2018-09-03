package cipher;

public class CaesarCipher extends MonoCipher {

    CaesarCipher(int shftParam) {
        super();
        setEncryptAlphabet(shftParam);
    }

    void setEncryptAlphabet(int shiftParam) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < alphabet.length(); i++) {
            sb.append(alphabet.charAt(i) + shiftParam);
        }

        encryptAlphabet = sb.toString();
    }
}
