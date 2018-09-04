package cipher.myCiphers;

import java.util.ArrayList;
import java.util.Random;

public class RandomSubstitutionCipher extends MonoCipher {

    private static RandomSubstitutionCipher instance = null;

    private RandomSubstitutionCipher() {
        super();
        encryptAlphabet = generateRandomKey();
    }

    private String generateRandomKey() {
        StringBuilder sb = new StringBuilder();

        ArrayList<Integer> availableShifts = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            availableShifts.add(i);
        }

        while (!availableShifts.isEmpty()) {
            int randomIndex = new Random().nextInt(availableShifts.size());
            int shiftedChar = 'A' + availableShifts.get(randomIndex);
            sb.append((char) ((shiftedChar > 'Z') ? shiftedChar - 'Z' - 1 + 'A' : shiftedChar));

            availableShifts.remove(randomIndex);
        }

        return sb.toString();
    }

    public static RandomSubstitutionCipher getInstance() {
        if (instance == null) {
            instance = new RandomSubstitutionCipher();
        }

        return instance;
    }
}
