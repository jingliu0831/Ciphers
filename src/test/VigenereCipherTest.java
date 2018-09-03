package test;

import cipher.myCiphers.VigenereCipher;
import org.junit.Test;

import static org.junit.Assert.*;

public class VigenereCipherTest {
    @Test
    public void testVigenereCipherEncrypt() {
        VigenereCipher vc = new VigenereCipher("ABC");

        String res1 = vc.encrypt("ABC DEF");
        assertEquals("BDF EGI", res1);

        String res2 = vc.encrypt("ABC@ DEF");
        assertEquals("BDF EGI", res2);
    }
}
