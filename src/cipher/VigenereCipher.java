package cipher;

import java.io.InputStream;
import java.io.OutputStream;

public class VigenereCipher extends AbstractCipher {
    private String encryptKey = null;

    VigenereCipher(String key) {
        encryptKey = key;
    }

    @Override
    public void decrypt(InputStream in, OutputStream out) {

    }

    @Override
    public String decrypt(String ciphertext) {
        StringBuilder sb = new StringBuilder();

        int index = 0;
        while (index < ciphertext.length()) {
            int indexInKey = index % (encryptKey.length());
            sb.append(ciphertext.charAt(index) - encryptKey.charAt(indexInKey));
        }
        return sb.toString();
    }

    @Override
    public void encrypt(InputStream in, OutputStream out) {

    }

    @Override
    public String encrypt(String msg) {
        StringBuilder sb = new StringBuilder();

        int index = 0;
        while (index < msg.length()) {
            int indexInKey = index % (encryptKey.length());
            sb.append(msg.charAt(index) + encryptKey.charAt(indexInKey));
        }
        return sb.toString();
    }

    @Override
    public void save(OutputStream out) {

    }
}
