import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32;

public class useIoCrc {
    public static long checkBufferedInputStream(Path fileName) {
        try (InputStream in = new BufferedInputStream(Files.newInputStream(fileName))) {
            CRC32 crc = new CRC32();
            int c;
            while ((c = in.read()) != -1) {
                crc.update(c);
            }
            return crc.getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static long checkRandomAccessFile(Path fileName) {
        try (RandomAccessFile file = new RandomAccessFile(fileName.toFile(), "r")) {
            CRC32 crc = new CRC32();
            for (long p = 0; p < file.length(); ++p) {
                file.seek(p);
                crc.update(file.readByte());
            }
            return crc.getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static long checkMappedFile(Path fileName) {
        try (FileChannel channel = FileChannel.open(fileName)) {
            CRC32 crc = new CRC32();
            long length = channel.size();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);
            for (int p = 0; p < length; ++p) {
                crc.update(buffer.get(p));
            }
            return crc.getValue();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void main(String[] args) {

    }
}