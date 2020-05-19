package self.mysql.innodb.parse.entity;

import lombok.Data;
import self.mysql.innodb.parse.PageReader;

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

    private int nextRecord;

    private String record;

    public Row(PageReader reader) {
        final int startOffset = reader.getOffset();
        byte[] recordHeader = reader.readBytes(RECORD_HEADER_SIZE);
        this.unknown1 = recordHeader[0] >> 7;
        this.unknown2 = (recordHeader[0] & 0x40) >> 6;
        this.deletedFlag = (recordHeader[0] & 0x20) >> 5;
        this.minRecordFLag = (recordHeader[0] & 0x10) >> 4;
        this.ownedNum = recordHeader[0] & 0x0f;
        this.heapNo = recordHeader[1] << 5 + recordHeader[2] >> 3;
        this.recordType = RecordType.ofValue(recordHeader[2] & 0x07);
        this.nextRecord = (Byte.toUnsignedInt(recordHeader[3]) << 8) + Byte.toUnsignedInt(recordHeader[4]);
//        if (this.recordType == RecordType.INFIMUM || this.recordType == RecordType.SUPREMUM) {
//            this.record = reader.readString(8);
//        }
        this.record = reader.readStringToPosition(this.nextRecord + startOffset);
    }

}
