package self.mysql.innodb.parse;

import java.util.stream.Collectors;
import lombok.Data;

import java.util.Arrays;
import java.util.stream.IntStream;
import self.mysql.innodb.parse.entity.ExtentDescriptorEntry;
import self.mysql.innodb.parse.entity.ListBaseNode;
import self.mysql.innodb.parse.entity.SegmentHeader;
import self.mysql.innodb.parse.entity.SpaceFlags;

/**
 * Buffer
 *
 * @author chenzb
 * @date 2020/4/20
 */
@Data
public class PageReader {

    private byte[] data;
    private int offset;

    public PageReader(byte[] data) {
        this.data = data;
    }

    public byte readByte() {
        byte b = data[offset];
        offset += 1;
        return b;
    }

    public byte readByte(int offset) {
        return data[offset];
    }

    public byte[] readBytes(int len) {
        byte[] bytes = Arrays.copyOfRange(data, offset, offset + len);
        offset += len;
        return bytes;
    }

    public byte[] readBytes(int from, int len) {
        return Arrays.copyOfRange(data, from, from + len);
    }

    public int readShort() {
        return readNumber(2);
    }

    public int readInt() {
        return readNumber(4);
    }

    public int[] readInts(int size) {
        int[] values = new int[size];
        IntStream.range(0, size).forEach(i -> values[i] = readInt());
        return values;
    }

    public long readLong() {
        long big = (long) readInt() << 32;
        int little = readInt();
        return little + big ;
    }

    private int readNumber(int len) {
        int i = IntStream.range(0, len).map(index -> Byte.toUnsignedInt(data[index + offset]) << (8 * (len - index - 1))).sum();
        offset += len;
        return i;
    }

    public String readString(int len) {
        byte[] bytes = readBytes(len);
        return new String(bytes);
    }

    public String readString(int from, int len) {
        byte[] bytes = readBytes(from, len);
        return new String(bytes);
    }

    public String readStringToPosition(int position) {
        int len = position - offset;
        return readString(len);
    }

    public SegmentHeader readSegmentHeader() {
        return new SegmentHeader(this);
    }

    public ListBaseNode readListBaseNode() {
        return new ListBaseNode(this);
    }

    public SpaceFlags readSpaceFlags() {
        return new SpaceFlags(this);
    }

    public ExtentDescriptorEntry readExtentDescriptorEntry() {
        return new ExtentDescriptorEntry(this);
    }

    public void pointTo(int offset, boolean reverse) {
        this.offset = reverse ? data.length - offset : offset;
    }

}
