package cipher;

import java.io.*;
import java.nio.charset.StandardCharsets;

/** A place to put some inherited code? */
public abstract class AbstractCipher implements Cipher {

    @Override
    public String decrypt(String ciphertext) {
        InputStream in = new ByteArrayInputStream(ciphertext.getBytes(StandardCharsets.UTF_8));
        OutputStream out = new ByteArrayOutputStream();

        decrypt(in, out);
        return out.toString();
    }

    @Override
    public String encrypt(String msg) {
        InputStream in = new ByteArrayInputStream(msg.getBytes(StandardCharsets.UTF_8));
        OutputStream out = new ByteArrayOutputStream();

        encrypt(in, out);
        return out.toString();
    }

    @Override
    public void save(OutputStream out) {
        try {
            out.flush();
            out.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}
