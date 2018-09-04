package cipher.myCiphers;

import cipher.AbstractCipher;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

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

                    int shiftedChar = Character.toUpperCase(character) - offsetFromA;
                    char letter = (char) ((shiftedChar < 'A') ? shiftedChar - 'A' + 1 + 'Z' : shiftedChar);

                    out.write(letter);
                    index++;
                }

                data = in.read();
            }

            out.flush();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
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

                    int shiftedChar = Character.toUpperCase(character) + offsetFromA;
                    char letter = (char) ((shiftedChar > 'Z') ? shiftedChar - 'Z' + 'A' : shiftedChar);

                    out.write(letter);
                    index++;
                } else if (Character.isDigit(character) || Character.isSpaceChar(character)) {
                    out.write(character);
                }

                data = in.read();

                out.flush();
            }

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @Override
    public void save(OutputStream out) {
        try {
            out.write(encryptKey.getBytes(StandardCharsets.UTF_8));
            out.write((byte) '\n');

            out.flush();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
