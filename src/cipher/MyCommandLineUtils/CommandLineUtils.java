package cipher.MyCommandLineUtils;

import cipher.Cipher;
import cipher.myCiphers.RsaCipher;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CommandLineUtils {
    private static String[] cipherFuncs = new String[]{"--em", "--ef", "--dm", "--df"};
    private static String[] cipherTypes = new String[]{"--caesar", "--monosub", "--random", "--vigenere", "--rsa", "--rsaPr", "--rsaPu"};
    private static Set<String> supportedCipherFuncs = new HashSet<>(Arrays.asList(cipherFuncs));
    private static Set<String> supportedCipherTypes = new HashSet<>(Arrays.asList(cipherTypes));

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

    private static boolean isUnique(String[] args, Set<String> supported) {
        int count = 0;
        for (String arg : args) {
            if (supported.contains(arg)) {
                count++;
            }

            if (count > 1) {
                return false;
            }
        }

        return count != 0;
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

    public static void validateCommandArgs(String[] args) {
        if (!isUnique(args, supportedCipherTypes)) {
            throw new IllegalArgumentException("Error:\n" +
                    "You must have 1 and only 1 cipher type. Supported cipher types:\n" +
                    "[ --monosub <file> | --caesar <shiftParam> | --crackedCaesar [-t <examples> | -c <encrypted>] |\n" +
                    "  --random|--vigenere <key> | --vigenereL <file> | --rsa | --rsaPr <file> | --rsaPu <file> ]");
        }

        if (!isUnique(args, supportedCipherFuncs)) {
            throw new IllegalArgumentException("Error: You must have 1 and only 1 cipher function [--em|--ef|--dm|--df]!");
        }
    }

    public static void printHintMessage() {
        System.out.println("Usage of this cipher:\n\n" +
                "java -jar ciphers.jar <CIPHER TYPE> <CIPHER FUNCTION> <OUTPUT OPTIONS>\n");
    }

    public static void printHelpMessage() {
        System.out.println("\nUsage of this cipher:\n\n" +
                "java -jar ciphers.jar <CIPHER TYPE> <CIPHER FUNCTION> <OUTPUT OPTIONS>\n");

        System.out.println("Supported cipher types (unique):\n" +
                "--monosub <file>                                   Monosubstitution cipher\n" +
                "--caesar <shiftParam>                              Caesar cipher\n" +
                "--crackedCaesar [-t <examples> | -c <encrypted>]   A Caesar cipher cracked from example file and encrypted files\n" +
                "--random                                           Random substitution cipher\n" +
                "--vigenere <key>                                   Vigenere cipher\n" +
                "--vigenereL <file>                                 Vigenere cipher from a key file\n" +
                "--rsa                                              RSA cipher\n" +
                "--rsaPr <file>                                     RSA cipher for decryption from a private key file\n" +
                "--rsaPu <file>                                     RSA cipher for encryption from a public key file\n");

        System.out.println("Supported cipher functions (unique):\n" +
                "--em <msg>   Encrypt message\n" +
                "--ef <file>  Encrypt file\n" +
                "--dm <msg>   Decrypt message\n" +
                "--df <file>  Decrypt file\n");

        System.out.println("Supported output options (multiple):\n" +
                "--print              Print output to console\n" +
                "--out <file>         Save output to file\n" +
                "--save <file>        Save cipher to file, for RSA cipher this saves private key to file\n" +
                "--savePu <file>      Saves public key to file (Only for RSA cipher)\n");
    }
}
