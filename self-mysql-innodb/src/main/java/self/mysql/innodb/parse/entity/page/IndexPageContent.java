package self.mysql.innodb.parse.entity.page;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import self.mysql.common.PageSpi;
import self.mysql.innodb.parse.PageReader;
import self.mysql.innodb.parse.entity.PageDirection;
import self.mysql.innodb.parse.entity.PageType;
import self.mysql.innodb.parse.entity.RecordType;
import self.mysql.innodb.parse.entity.Row;
import self.mysql.innodb.parse.entity.SegmentHeader;

/**
 * B+树叶节点 (索引页，数据页)
 *
 * @author chenzibin
 * @date 2023/2/1
 */
@Data
@PageSpi(PageType.FIL_PAGE_INDEX)
public class IndexPageContent implements PageContent {

    /* --------------------Page Header-------------------- */

    /**
     * PAGE_N_DIR_SLOTS(2): 在Page Directory(页目录)中的Slot(槽)数
     */
    private int slots;

    /**
     * PAGE_HEAP_TOP(2): 空闲空间的起始地址, 堆中第一个记录的指针, 记录在页中是根据堆的形式存放的
     */
    private int heapTop;

    /**
     * PAGE_N_HEAP(2): 堆中的记录数, 一共占2字节, 但是第15位表示行记录格式
     */
    private int heapNum;

    /**
     * PAGE_FREE(2): 指向可重用空间的首指针
     */
    private int free;

    /**
     * PAGE_GARBAGE(2): 已删除记录的字节数, 即行记录结构中delete flag为1的记录大小的总数
     */
    private int garbage;

    /**
     * PAGE_LAST_INSERT(2): 最后插入记录的位置
     */
    private int lastInsert;

    /**
     * PAGE_DIRECTION(2): 最后插入的方向, 可能的取值{@link PageDirection}
     * @see PageDirection
     */
    private PageDirection direction;

    /**
     * PAGE_N_DIRECTION(2): 一个方向连续插入记录的数量
     */
    private int directionNum;

    /**
     * PAGE_N_RECS(2): 该页中记录的数量
     */
    private int recordNum;

    /**
     * PAGE_MAX_TRX_ID(8): 修改当前页的最大事务ID, 该值仅在Secondary Index 中定义
     */
    private long maxTrxId;

    /**
     * PAGE_LEVEL(2): 当前页在B+树中所处的层级, 0x00代表叶节点, 即叶结点总是在第0层
     */
    private int level;

    /**
     * PAGE_INDEX_ID(2): 索引ID, 表示当前页属于哪个索引
     */
    private long indexId;

    /**
     * PAGE_BTR_SEG_LEAF(10): B+树数据页叶节点所在段的segment header, 该值仅在B+Tree的Root页中定义
     */
    private SegmentHeader leaf;

    /**
     * PAGE_BTR_SEG_TOP(10):B+树数据页非叶节点所在段的segment header, 该值仅在B+Tree的Root页中定义
     */
    private SegmentHeader top;

    /* --------------------Infimum & Supremum Records-------------------- */
    private Row infimum;

    private List<Row> records;

    private Row supremum;

    @Override
    public void parse(PageReader reader) {
        this.slots = reader.readShort();
        this.heapTop = reader.readShort();
        this.heapNum = reader.readShort();
        this.free = reader.readShort();
        this.garbage = reader.readShort();
        this.lastInsert = reader.readShort();
        this.direction = PageDirection.ofValue(reader.readShort());
        this.directionNum = reader.readShort();
        this.recordNum = reader.readShort();
        this.maxTrxId = reader.readLong();
        this.level = reader.readShort();
        this.indexId = reader.readLong();
        this.leaf = reader.readSegmentHeader();
        this.top = reader.readSegmentHeader();

        records = new ArrayList<>();
        boolean hasNextRecord = true;
        int nextRecordOffset = reader.getOffset();
        // 改为链式读取，当读取到 SUPREMUM 时停止
        while (hasNextRecord) {
            Row record = new Row(reader, nextRecordOffset);
            nextRecordOffset = record.getNextRecord();
            if (record.getRecordType() == RecordType.INFIMUM) {
                this.infimum = record;
            } else if (record.getRecordType() == RecordType.SUPREMUM) {
                this.supremum = record;
                hasNextRecord = false;
            } else {
                records.add(record);
            }
        }
    }
}
