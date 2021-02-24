package self.mysql.innodb.parse.entity;

import lombok.Data;
import self.mysql.innodb.parse.PageReader;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Row
 *
 * @author chenzb
 * @date 2020/4/22
 */
@Data
public class Row {

    public static final int RECORD_HEADER_SIZE = 5;

    private int unknown1;

    private int unknown2;

    private int deletedFlag;

    private int minRecordFLag;

    private int ownedNum;

    private int heapNo;

    private RecordType recordType;

    /**
     * nextRecord指向下一条记录的RECORD_HEADER_SIZE位置
     *
     * 往前读取变长字段长度和空值标志位
     *      空值标志位的位数为所有字段的个数
     *      先读取空值标志位，变长字段长度中仅记录不为空且字段类型可变长的字段的长度
     *      可变长字段的长度为逆序存储，可以理解为通过nextRecord指向记录后，往前读取
     * 往后正常解析行数据格式
     */
    private int nextRecord;

    private String record;

    private int[] colSizes;

    private String[] colDatas;

    public Row(PageReader reader, int nextRecordOffset) {
        final int startOffset = nextRecordOffset;

        byte[] recordHeader = reader.readBytes(startOffset, RECORD_HEADER_SIZE);
        this.unknown1 = recordHeader[0] >> 7;
        this.unknown2 = (recordHeader[0] & 0x40) >> 6;
        this.deletedFlag = (recordHeader[0] & 0x20) >> 5;
        this.minRecordFLag = (recordHeader[0] & 0x10) >> 4;
        this.ownedNum = recordHeader[0] & 0x0f;
        this.heapNo = recordHeader[1] << 5 + recordHeader[2] >> 3;
        this.recordType = RecordType.ofValue(recordHeader[2] & 0x07);
        this.nextRecord = startOffset + (Byte.toUnsignedInt(recordHeader[3]) << 8) + Byte.toUnsignedInt(recordHeader[4]);
        this.nextRecord = this.nextRecord % 65536;
        if (this.recordType == RecordType.INFIMUM || this.recordType == RecordType.SUPREMUM) {
            this.record = reader.readString(startOffset + 5, 8);
        } else {
//            int colNum = 4;
//            this.colSizes = new int[colNum];
//            IntStream.range(0, 4).forEach(i -> this.colSizes[colNum - 1 - i] = reader.readByte());
//            int nullFlags = reader.readByte(startOffset - 1);
//
//            this.colDatas = new String[colSizes.length];
//            // 主键, 或Row ID
//            this.colDatas[0] = reader.readString(colSizes[0]);
//            // Transaction ID
//            reader.readBytes(6);
//            // Roll Pointer
//            reader.readBytes(7);
//            this.colDatas[1] = reader.readString(colSizes[1]);
//            this.colDatas[2] = reader.readString(colSizes[2]);
//            this.colDatas[3] = reader.readString(colSizes[3]);
//            this.record = Stream.of(this.colDatas).collect(Collectors.joining(", "));
////            this.record = reader.readStringToPosition(this.nextRecord + startOffset);
        }
    }

}
