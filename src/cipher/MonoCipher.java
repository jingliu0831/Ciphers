package cipher;

import java.io.InputStream;
import java.io.OutputStream;

public class MonoCipher extends AbstractCipher {
    // "ABC...YZ" -> "BCDE....ZA"
    String encryptAlphabet = null;

    MonoCipher(String encrAlp) {
        this.encryptAlphabet = encrAlp;
    }

    MonoCipher() {
        encryptAlphabet = "";
    }



    @Override
    public void decrypt(InputStream in, OutputStream out) {

    }

    @Override
    public String decrypt(String ciphertext) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            int offsetFromA = encryptAlphabet.indexOf(ciphertext.charAt(i));
            sb.append('A' + offsetFromA);
        }
        return sb.toString();
    }

    @Override
    public void encrypt(InputStream in, OutputStream out) {

    }

    @Override
    public String encrypt(String msg) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < msg.length(); i++) {
            int offsetFromA = msg.charAt(i) - 'A';
            sb.append(encryptAlphabet.charAt(offsetFromA));
        }
        return sb.toString();
    }

    @Override
    public void save(OutputStream out) {

    }
}
