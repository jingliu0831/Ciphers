package cipher.myCiphers;

import cipher.AbstractCipher;
import cipher.ChunkReader;
import cipher.myReaders.RsaChunkReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RsaCipher extends AbstractCipher {
    private static final int PLAIN_TEXT_CHUNK_SIZE = 117;
    private static final int CIPHER_TEXT_CHUNK_SIZE = 128;

    private static final String SAMPLE_N_STR = "1093733205710804998199890334068767166455920362828704765160762785" +
            "7499483958630357076021298990643044443078830573207441251383385554" +
            "0595306533636002574137640210725086799272942906615619662751138490" +
            "7081299171319768555238149258992942399208388665959233718761302432" +
            "26847351820673092753217938229621222197931912046784793";
    private static final String SAMPLE_E_STR = "157307166947463324293191";
    private static final String SAMPLE_D_STR = "5051344722702808401081282712266153060988904798305842766107188088" +
            "5908654445646155458691942425898014706411932503960880426195998687" +
            "5931918963365832849565604072385256625773752537810929752797947864" +
            "1740341834159319848666223848546326674700490685819268998953869293" +
            "3170796325885997807827620143533305258355518889959511";

    private BigInteger n;
    private BigInteger e;
    private BigInteger d;

    public RsaCipher() {
        this.n = new BigInteger(SAMPLE_N_STR);
        this.e = new BigInteger(SAMPLE_E_STR);
        this.d = new BigInteger(SAMPLE_D_STR);
    }

    public RsaCipher(BigInteger n, BigInteger e, BigInteger d) {
        this.n = n;
        this.e = e;
        this.d = d;
    }

    @Override
    public void decrypt(InputStream in, OutputStream out) {
        ChunkReader chunkReader = new RsaChunkReader(in, CIPHER_TEXT_CHUNK_SIZE);

        try {
            while (chunkReader.hasNext()) {
                byte[] data = new byte[CIPHER_TEXT_CHUNK_SIZE];

                chunkReader.nextChunk(data);
                BigInteger cipherText = new BigInteger(data);

                BigInteger plainBigInt = cipherText.modPow(d, n);

                byte[] plainBytes = reverseByteArray(plainBigInt.toByteArray());
                out.write(Arrays.copyOfRange(plainBytes, 0, plainBytes[PLAIN_TEXT_CHUNK_SIZE]));
            }

            out.flush();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @Override
    public void encrypt(InputStream in, OutputStream out) {
        ChunkReader chunkReader = new RsaChunkReader(in, PLAIN_TEXT_CHUNK_SIZE);

        try {
            while (chunkReader.hasNext()) {
                byte[] data = new byte[CIPHER_TEXT_CHUNK_SIZE];

                int byteCount = chunkReader.nextChunk(data);
                data[data.length - 1 - PLAIN_TEXT_CHUNK_SIZE] = (byte) byteCount;

                BigInteger plainText = new BigInteger(data);

                BigInteger cipherBigInt = plainText.modPow(e, n);

                byte[] cipherBytes = reverseByteArray(cipherBigInt.toByteArray());
                out.write(cipherBytes);
            }

            out.flush();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @Override
    public void save(OutputStream out) {
        try {
            String nKeyStr = "n=" + n.toString();
            String dKeyStr = "d=" + d.toString();

            out.write(nKeyStr.getBytes(StandardCharsets.UTF_8));
            out.write((byte) '\n');

            out.write(dKeyStr.getBytes(StandardCharsets.UTF_8));
            out.write((byte) '\n');

            out.flush();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void savePu(OutputStream out) {
        try {
            String nKeyStr = "n=" + n.toString();
            String eKeyStr = "e=" + e.toString();

            out.write(nKeyStr.getBytes(StandardCharsets.UTF_8));
            out.write((byte) '\n');

            out.write(eKeyStr.getBytes(StandardCharsets.UTF_8));
            out.write((byte) '\n');

            out.flush();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    private byte[] reverseByteArray(byte[] original) {
        int len = original.length;
        byte[] result = new byte[len];

        for (int i = 0; i < original.length; i++) {
            result[len - 1 - i] = original[i];
        }

        return result;
    }
}
