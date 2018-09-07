package cipher;

import cipher.MyCommandLineUtils.CommandLineUtils;
import cipher.MyCommandLineUtils.FileUtils;
import cipher.myCiphers.RsaCipher;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * 
 * Command line interface to allow users to interact with your ciphers.
 * 
 * We have provided a structure to parse most of the arguments, and it is your
 * responsibility to implement the appropriate actions according to the
 * assignment 2 specifications. You may choose to "fill in the blanks" or
 * rewrite this class.
 *
 * Regardless of which option you choose, remember to minimize repetitive code.
 * You are welcome to add additional methods or alter the provided code to
 * achieve this.
 *
 */
public class Main {
    private static InputStream inputStream;
    private static OutputStream outputStream;

    //TODO: RSA cipher is not performing correctly.
    //TODO: message composition (from ' to ') did not work well in command line

    /**
     * Read in command line functions and perform the specified actions
     *
     * @param args
     *            String array of command line commands
     */
    public static void main(String[] args) {
        CipherFactory cipherFactory = new CipherFactory();

        try {
            CommandLineUtils.validateCommandArgs(args);
            processCommandLine(args, cipherFactory);
        } catch (Exception e) {
            CommandLineUtils.printHelpMessage();
        }
    }

    /**
     * Create a BufferedReader that reads from a file
     * 
     * @param filename
     *            Name of file to read from
     * @return a BufferedReader to read from the given file
     * @throws FileNotFoundException
     */
    public static BufferedReader openFile(String filename) throws FileNotFoundException {
        return new BufferedReader(new FileReader(filename));
    }

    /**
     * Create a BufferedReader that write to a file
     * 
     * @param filename
     *            Name of file to write to
     * @return a BufferedReader to write to the given file
     * @throws FileNotFoundException
     */
    public static BufferedWriter writeFile(String filename) throws IOException {
        return new BufferedWriter(new FileWriter(filename));
    }

    /**
     * Read a given file into a String.
     * Requires: the file contents decode successfully into a String in the default
     *           character encoding.
     * 
     * @param filename
     *            Name of file to be read
     * @return Contents of the file as a String
     */
    public static String readFile(String filename) {
        String line = null;
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error when reading file " + filename + "! " + e.getMessage());
        }
        return result.toString();
    }

    /**
     * Prints a string to a specified file
     * 
     * @param filename
     *            File in which string will be printed
     * @param towrite
     *            String to be printed in file
     * @throws IOException
     */
    public static void writeToFile(String filename, String towrite) throws IOException {
        BufferedWriter w = writeFile(filename);
        w.write(towrite, 0, towrite.length());
        w.close();
    }

    /**
     * Reads the cipher function from command line and performs specified task
     * 
     * @param func
     *            String specifying cipher function to be completed
     * @param c
     *            The cipher created based on first command
     * @param name
     *            The message or file name to be encrypted/decrypted
     */
    public static void cipherFunction(String func, Cipher c, String name) {
        switch (func) {
        case "--em": // Encrypt the given msg
            inputStream = new ByteArrayInputStream(name.getBytes(StandardCharsets.ISO_8859_1));
            c.encrypt(inputStream, outputStream);
            break;
        case "--ef": // Encrypt the given file
            try {
                inputStream = new FileInputStream(new File(name));
                c.encrypt(inputStream, outputStream);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            break;
        case "--dm": // Decrypt the given msg
            inputStream = new ByteArrayInputStream(name.getBytes(StandardCharsets.ISO_8859_1));
            c.decrypt(inputStream, outputStream);
            break;
        case "--df": // Decrypt the given file
            try {
                inputStream = new FileInputStream(new File(name));
                c.decrypt(inputStream, outputStream);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
            break;
        }
    }

    /**
     * Read in remaining output commands and performs specified tasks
     * 
     * @param args
     *            String array of commands
     * @param c
     *            Cipher that has already been created
     * @param start
     *            Index to begin processing arguments at
     */
    public static void outputOptions(String[] args, Cipher c, int start) {
        if (args.length > start) {
            for (int i = start; i < args.length; i++) {
                switch (args[i]) {
                case "--print":
                    try {
                        CommandLineUtils.ensureNoNextArg(args, i);

                        outputStream = System.out;
                        cipherFunction(args[0], c, args[1]);
                        outputStream.write((byte) '\n');
//                        outputStream.close();
                    } catch (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    } catch (IllegalArgumentException iae) {
                        System.out.println("Error: Invalid output command. Hint: [--print]");
                    }
                    break;
                case "--out":
                    try {
                        CommandLineUtils.ensureNextArgIsFileOrMsg(args, i);

                        outputStream = new FileOutputStream(new File(args[i + 1]));
                        cipherFunction(args[0], c, args[1]);
//                        outputStream.close();
                    } catch (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    } catch (IllegalArgumentException iae) {
                        System.out.println("Error: Invalid output command. Hint: [--out <file>]");
                    }
                    break;
                case "--save":
                    try {
                        CommandLineUtils.ensureNextArgIsFileOrMsg(args, i);

                        outputStream = new FileOutputStream(new File(args[i + 1]));
                        c.save(outputStream);
//                        outputStream.close();
                    } catch (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    } catch (IllegalArgumentException iae) {
                        System.out.println("Error: Invalid output command. Hint: [--save <file>]");
                    }
                    break;
                case "--savePu":
                    try {
                        CommandLineUtils.ensureCipherIsRsa(c);
                        CommandLineUtils.ensureNextArgIsFileOrMsg(args, i);

                        outputStream = new FileOutputStream(new File(args[i + 1]));
                        ((RsaCipher) c).savePu(outputStream);
//                        outputStream.close();
                    } catch (IOException ioe) {
                        System.out.println(ioe.getMessage());
                    } catch (IllegalArgumentException iae) {
                        System.out.println("Error: Invalid output command. Hint: [--save <file>]");
                    } catch (UnsupportedOperationException uoe) {
                        System.out.println("Error: Only RSA cipher has public key.");
                    }
                    break;
                }
            }
        }
    }

    private static void processCommandLine(String[] args, CipherFactory cipherFactory) {
        Cipher cipher;

        switch (args[0]) {
            case "--monosub":
                String encryptedAlphabet = readFile(args[1]);
                cipher = cipherFactory.getMonoCipher(encryptedAlphabet);
                processMessageWithCipher(args, cipher, 2); // --monosub file
                break;
            case "--caesar":
                int shiftParam = Integer.parseInt(args[1]);
                cipher = cipherFactory.getCaesarCipher(shiftParam);
                processMessageWithCipher(args, cipher, 2); // --caesar 2
                break;
            case "--random":
                cipher = cipherFactory.getRandomSubstitutionCipher();
                processMessageWithCipher(args, cipher, 1); // --random
                break;
            case "--crackedCaesar": // Decrypt the message
                FrequencyAnalysisResult frequencyAnalysisResult = getCipherFromFreqAnalysis(args, 1);

                int cipherFuncStart = 1 + frequencyAnalysisResult.argsCount;
                if (!args[cipherFuncStart].equals("--df") && !args[cipherFuncStart].equals("--dm")) {
                    throw new UnsupportedOperationException("Error: --rsaPr can only decrypt message or decrypt message!");
                }

                cipher = frequencyAnalysisResult.cipher;
                processMessageWithCipher(args, cipher, cipherFuncStart);
                break;
            case "--vigenere":
                String key = args[1];
                if (key.length() > 128) {
                    throw new IllegalArgumentException("key length cannot be longer than 128");
                }
                cipher = cipherFactory.getVigenereCipher(key);
                processMessageWithCipher(args, cipher, 2); // --vigenere key
                break;
            case "--vigenereL":
                key = readFile(args[1]);
                cipher = cipherFactory.getVigenereCipher(key);
                processMessageWithCipher(args, cipher, 2); // --vigenereL file
                break;
            case "--rsa":
                cipher = cipherFactory.getRSACipher();
                processMessageWithCipher(args, cipher, 1); // --rsa
                break;
            case "--rsaPr": // Decrypt the message
                if (!args[2].equals("--df") && !args[2].equals("--dm")) {
                    throw new UnsupportedOperationException("Error: --rsaPr can only decrypt message or decrypt message!");
                }
                cipher = getRsaCipherFromFile(cipherFactory, args[1]);
                processMessageWithCipher(args, cipher, 2); // --rsaPr file
                break;
            case "--rsaPu": // Encrypt the message
                if (!args[2].equals("--ef") && !args[2].equals("--em")) {
                    throw new UnsupportedOperationException("Error: --rsaPr can only decrypt message or decrypt message!");
                }
                cipher = getRsaCipherFromFile(cipherFactory, args[1]);
                processMessageWithCipher(args, cipher, 2); // --rsaPu file
                break;
        }
    }

    /** Return [n, e, d]. May have null in e or d. */
    private static Cipher getRsaCipherFromFile(CipherFactory cipherFactory, String filename) {
        BigInteger[] rsaKey;

        String file = readFile(filename);
        String[] keyStrs = file.split(","); // n=00000,d=00000

        BigInteger n = new BigInteger(keyStrs[0].substring(2));

        if (keyStrs[1].charAt(0) == 'e') {
            BigInteger e = new BigInteger(keyStrs[1].substring(2));
            rsaKey = new BigInteger[]{n, e, null};
        } else if (keyStrs[1].charAt(0) == 'd') {
            BigInteger d = new BigInteger(keyStrs[1].substring(2));
            rsaKey = new BigInteger[]{n, null, d};
        } else {
            throw new IllegalArgumentException("Invalid RSA Key format! Please use \"n=000,[d,e]=000\" in key file...");
        }

        return cipherFactory.getRSACipher(rsaKey[0], rsaKey[1], rsaKey[2]);
    }

    private static void processMessageWithCipher(String[] args, Cipher cipher, int cipherFuncStart) {
        String[] funcAndOutput = new String[args.length - cipherFuncStart]; // --cipherType fileOrMsg
        funcAndOutput[0] = args[cipherFuncStart]; // cipherFunc
        funcAndOutput[1] = args[cipherFuncStart + 1]; // args[3]
        System.arraycopy(args, cipherFuncStart + 2, funcAndOutput, 2, funcAndOutput.length - 2);

        outputOptions(funcAndOutput, cipher, 2);

        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException ioe) {
            System.out.println("Error: Unable to close outputstream!");
        }
    }

    public static FrequencyAnalysisResult getCipherFromFreqAnalysis(String[] args, int start) {
        FrequencyAnalyzer sampleAnalyzer = new FrequencyAnalyzer();
        FrequencyAnalyzer encryptedAnalyzer = new FrequencyAnalyzer();
        int argsCount = 0;

        if (args.length > start) {
            for (int i = start; i < args.length; i++) {
                switch (args[i]) {
                    case "-t":
                        if (args[i].charAt(0) == '-') {
                            throw new IllegalArgumentException("Error: Invalid command! Hint: [-t <sampleFile>]");
                        }
                        FileUtils.addFileToFrequencyAnalyzer(args[i + 1], sampleAnalyzer);
                        argsCount += 2;
                        break;
                    case "-c":
                        if (args[i].charAt(0) == '-') {
                            throw new IllegalArgumentException("Error: Invalid command! Hint: [-c <sampleFile>]");
                        }
                        FileUtils.addFileToFrequencyAnalyzer(args[i + 1], encryptedAnalyzer);
                        argsCount += 2;
                        break;
                }
            }
        }

        return new FrequencyAnalysisResult(FrequencyAnalyzer.getCipher(sampleAnalyzer, encryptedAnalyzer), argsCount);
    }

    static class FrequencyAnalysisResult {
        Cipher cipher;
        int argsCount;

        public FrequencyAnalysisResult(Cipher c, int count) {
            cipher = c;
            argsCount = count;
        }
    }
}
