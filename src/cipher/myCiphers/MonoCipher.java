package cipher.myCiphers;

import cipher.AbstractCipher;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MonoCipher extends AbstractCipher {
    // "ABC...YZ" -> "BCDE....ZA"
    String encryptAlphabet;

    public MonoCipher() {
        encryptAlphabet = "";
    }

    public MonoCipher(String encrAlp) {
        this.encryptAlphabet = encrAlp;
    }

    @Override
    public void decrypt(InputStream in, OutputStream out) {
        try {
            int data = in.read();

            while (data != -1) {
                char character = (char) data;

                // keep space
                if (Character.isDigit(character) || Character.isSpaceChar(character)) {
                    out.write(character);
                } else {
                    // get original char. No non-letter so don't need to worry about non-letter chars.
                    int offsetFromA = encryptAlphabet.indexOf(data);
                    out.write('A' + offsetFromA);
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

            while (data != -1) {
                char character = (char) data;

                if (Character.isLetter(character)) {
                    char upperCaseChar = Character.toUpperCase(character);

                    int offsetFromA = upperCaseChar - 'A';
                    out.write(encryptAlphabet.charAt(offsetFromA));
                } else if (Character.isDigit(character) || Character.isSpaceChar(character)) {
                    out.write(character);
                }

                data = in.read();
            }

            out.flush();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @Override
    public void save(OutputStream out) {
        try {
            out.write(encryptAlphabet.getBytes(StandardCharsets.ISO_8859_1));
            out.write((byte) '\n');

            out.flush();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
