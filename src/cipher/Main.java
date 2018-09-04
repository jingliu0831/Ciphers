package cipher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
            // TODO Add an appropriate error message
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
        case "--em":
            // TODO Encrypt the given message
            break;
        case "--ef":
            // TODO Encrypt the given file
            break;
        case "--dm":
            // TODO Decrypt the given message
            break;
        case "--df":
            // TODO Decrypt the given file
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
                    // TODO Print result of applying the cipher to the console
                    break;
                case "--out":
                    // TODO Print result of applying the cipher to a file
                    break;
                case "--save":
                    // TODO Save current cipher to a file
                    break;
                case "--savePu":
                    // TODO If the current cipher is RSA, save the public key to
                    // a file
                    break;
                }
            }
        }
    }

    /**
     * Read in command line functions and perform the specified actions
     * 
     * @param args
     *            String array of command line commands
     */
    public static void main(String[] args) {
        CipherFactory cipherFactory = new CipherFactory();

        switch (args[0]) {
        case "--monosub":
            cipherFactory.getMonoCipher(""); // TODO: given alphabet
            break;
        case "--caesar":
            cipherFactory.getCaesarCipher(0); // TODO: given shift parameter
            break;
        case "--random":
            cipherFactory.getRandomSubstitutionCipher();
            break;
        case "--crackedCaesar":
            // TODO decrypt the given file
            break;
        case "--vigenere":
            cipherFactory.getVigenereCipher(""); // TODO: given key word
            break;
        case "--vigenereL":
            // TODO Create a new Vigenere Cipher with key word from given file
            break;
        case "--rsa":
            cipherFactory.getRSACipher();
            break;
        case "--rsaPr":
            // TODO Create an RSA encrypter/decrypter from private key in a file
            break;
        case "--rsaPu":
            // TODO Create an encryption-only RSA cipher from public key in a
            // file
            break;
        }
    }
}
