package test;

import cipher.myCiphers.RsaCipher;
import org.junit.Test;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class RsaCipherTest {
    @Test
    public void testRsaCipherEncrypt() {
        RsaCipher rsaCipher = new RsaCipher();

        String input1 = "Inheritance is cool!";

        try {
            InputStream in = new ByteArrayInputStream(input1.getBytes(StandardCharsets.UTF_8));

            File f = new File("output/rsaEncrypted");
            FileOutputStream fop = new FileOutputStream(f);

            rsaCipher.encrypt(in, fop);

            fop.flush();
            fop.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }

    @Test
    public void testRsaCipherDecrypt() {
        RsaCipher rsaCipher = new RsaCipher();

        try {
            File f = new File("output/rsaEncrypted");
            InputStream in = new FileInputStream(f);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            rsaCipher.decrypt(in, out);

            System.out.println(out.toString());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }

    @Test
    public void testFunc() {
        BigInteger bigInt1 = new BigInteger("128");
        byte[] bytes1 = bigInt1.toByteArray();

        System.out.println("\n1:");
        for (byte b : bytes1) System.out.println(b);

        BigInteger bigInt2 = new BigInteger("12");
        byte[] bytes2 = bigInt2.toByteArray();

        System.out.println("\n2:");
        for (byte b : bytes2) System.out.println(b);

        byte[] testBytes3 = new byte[]{0, 0, 12};
        BigInteger bigInt3 = new BigInteger(testBytes3);

        System.out.println("\n3:");
        System.out.println(bigInt3);

        byte[] testBytes4 = new byte[]{0, 12, 0};
        BigInteger bigInt4 = new BigInteger(testBytes4);

        System.out.println("\n4:");
        System.out.println(bigInt4);

        byte[] testBytes5 = new byte[]{12};
        BigInteger bigInt5 = new BigInteger(testBytes5);

        System.out.println("\n5:");
        System.out.println(bigInt5);

        byte[] testBytes6 = new byte[]{65, 66};
        BigInteger bigInt6 = new BigInteger(testBytes6);

        System.out.println("\n6:");
        System.out.println(bigInt6);
    }
}
