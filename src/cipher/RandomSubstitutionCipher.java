package cipher;

import java.util.Random;

public class RandomSubstitutionCipher extends MonoCipher {

    RandomSubstitutionCipher() {
        super();
        setRandomKeyAsEncryptedAlphabet();
    }

    private void setRandomKeyAsEncryptedAlphabet() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sbOfKey = new StringBuilder();

        for (int i = 0; i < alphabet.length(); i++) {
            int offset = new Random().nextInt(25);
            sbOfKey.append('A' + offset);
        }

        encryptAlphabet = sbOfKey.toString();
    }
}
