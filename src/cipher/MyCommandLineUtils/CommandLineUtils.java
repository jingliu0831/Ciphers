package cipher.MyCommandLineUtils;

import cipher.Cipher;
import cipher.myCiphers.RsaCipher;

public class CommandLineUtils {
    public static void ensureNextArgIsFileOrMsg(String[] args, int index) {
        if (index == args.length - 1 || args[index].charAt(index + 1) == '-') {
            throw new IllegalArgumentException();
        }
    }

    public static void ensureNoNextArg(String[] args, int index) {
        if (index != args.length - 1 && args[index].charAt(index + 1) != '-') {
            throw new IllegalArgumentException();
        }
    }

    public static void ensureCipherIsRsa(Cipher c) {
        if (!(c instanceof RsaCipher)) {
            throw new UnsupportedOperationException();
        }
    }

    public static boolean onlyOneCipherFunc(String[] args) {
        int countOfCipherFuncs = 0;
        for (String arg : args) {
            if (arg.equals("--em") || arg.equals("--ef") || arg.equals("--dm") || arg.equals("--df")) {
                countOfCipherFuncs++;
            }

            if (countOfCipherFuncs > 1) {
                return false;
            }
        }

        return countOfCipherFuncs != 0;
    }

    /** get all msg wihtin ' and ' */
    public static String composeMessage(String[] args, int msgStart) {
        if (args[msgStart].charAt(0) != '\'') {
            return args[msgStart];
        }

        StringBuilder sb = new StringBuilder();
        sb.append(args[msgStart].substring(1));
        sb.append(' ');

        int index = msgStart + 1;
        while (args[index].charAt(args[index].length() - 1) != '\'') {
            sb.append(args[index]);
            sb.append(' ');
            index++;
        }

        sb.append(args[index].substring(0, args[index].length() - 1));

        return sb.toString();
    }
}
