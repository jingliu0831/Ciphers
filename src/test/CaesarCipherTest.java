package test;

import cipher.myCiphers.CaesarCipher;
import org.junit.Test;

import static org.junit.Assert.*;

public class CaesarCipherTest {
    @Test
    public void testCaesarCipherEncrypt() {
        CaesarCipher cc = new CaesarCipher(2);

        String res1 = cc.encrypt("ABC DEF");
        assertEquals("CDE FGH", res1);

        String res2 = cc.encrypt("ABC@ DEF");
        assertEquals("CDE FGH", res2);
    }
}
