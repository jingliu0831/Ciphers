package cipher.myCiphers;

import java.util.Random;

public class RandomSubstitutionCipher extends MonoCipher {

    public RandomSubstitutionCipher() {
        super();
        setRandomKeyAsEncryptedAlphabet();
    }

    private void setRandomKeyAsEncryptedAlphabet() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sbOfKey = new StringBuilder();

        for (int i = 0; i < alphabet.length(); i++) {
            int offset = new Random().nextInt(25);
            char letter = (char) ('A' + offset);
            sbOfKey.append(letter);
        }

        encryptAlphabet = sbOfKey.toString();
    }
}
