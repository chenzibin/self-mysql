package self.mysql.innodb.parse.entity;

import lombok.Data;
import self.mysql.innodb.parse.PageReader;

/**
 * @author chenzibin
 * @date 2023/2/1
 */
@Data
public class SegmentHeader {

    /**
     * Space ID of the INODE Entry(4): INODE Entry结构所在的表空间ID
     */
    private int spaceId;

    /**
     * Page Number of the INODE Entry(4): 	INODE Entry结构所在的页面页号
     */
    private int pageNumber;

    /**
     * Byte Offset of the INODE Ent(2): INODE Entry结构在该页面中的偏移量
     */
    private int pageOffset;

    public SegmentHeader(PageReader reader) {
        this.spaceId = reader.readInt();
        this.pageNumber = reader.readInt();
        this.pageOffset = reader.readShort();
    }
}
