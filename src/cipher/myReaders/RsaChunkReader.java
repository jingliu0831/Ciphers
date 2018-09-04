package cipher.myReaders;

import cipher.ChunkReader;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

public class RsaChunkReader implements ChunkReader {
    private InputStream inputStream;
    private int chunkSize;

    public RsaChunkReader(InputStream in, int chunkSize) {
        this.inputStream = in;
        this.chunkSize = chunkSize;
    }

    @Override
    public int chunkSize() {
        return chunkSize;
    }

    @Override
    public boolean hasNext() {
        try {
            return inputStream.available() > 0;
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        return false;
    }

    @Override
    public int nextChunk(byte[] data) throws EOFException {
        if (!hasNext()) {
            throw new EOFException();
        }

        try {
            int ch = inputStream.read();
            int count = 0;
            LinkedList<Byte> byteList = new LinkedList<>();

            while (ch != -1 && count < chunkSize) {
                byteList.add((byte) ch);
                count++;

                ch = inputStream.read();
            }

            int index = data.length - 1;
            for (Byte b : byteList) {
                data[index] = b;
                index--;
            }

            return count;

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        return 0;
    }
}
