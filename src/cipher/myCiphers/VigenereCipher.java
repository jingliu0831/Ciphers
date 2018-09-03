package cipher.myCiphers;

import cipher.AbstractCipher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class VigenereCipher extends AbstractCipher {
    private String encryptKey;

    public VigenereCipher(String key) {
        encryptKey = key;
    }

    @Override
    public void decrypt(InputStream in, OutputStream out) {
        try {
            int data = in.read();
            int index = 0;

            while (data != -1) {
                char character = (char) data;

                // keep space
                if (Character.isDigit(character) || Character.isSpaceChar(character)) {
                    out.write(character);
                } else {
                    // get original char. No non-letter so don't need to worry about non-letter chars.
                    int indexInKey = index % (encryptKey.length());
                    int offsetFromA = encryptKey.charAt(indexInKey) - 'A' + 1;
                    char letter = (char) (Character.toUpperCase(character) - offsetFromA);

                    out.write(letter);
                    index++;
                }

                data = in.read();
            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage()); // TODO: deal with exception
        }
    }

    @Override
    public void encrypt(InputStream in, OutputStream out) {
        try {
            int data = in.read();
            long index = 0;

            while (data != -1) {
                char character = (char) data;

                if (Character.isLetter(character)) {
                    int indexInKey = (int) (index % encryptKey.length());
                    int offsetFromA = encryptKey.charAt(indexInKey) - 'A' + 1;
                    char letter = (char) (Character.toUpperCase(character) + offsetFromA);

                    out.write(letter);
                    index++;
                } else if (Character.isDigit(character) || Character.isSpaceChar(character)) {
                    out.write(character);
                }

                data = in.read();
            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage()); // TODO: deal with exception
        }
    }
}
