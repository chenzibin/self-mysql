package self.mysql.innodb.parse.entity;

import lombok.Data;
import self.mysql.innodb.parse.PageReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Page
 *
 * @author chenzb
 * @date 2020/4/20
 */
@Data
public class Page {

    /* --------------------File Header-------------------- */

    /**
     * FIL_PAGE_SPACE_OR_CHKSUM(4)
     */
    private int checksum;

    /**
     * FIL_PAGE_OFFSET(4): 表空间中页的偏移值
     * 如某独立表空间a.ibd的大小为1GB, 如果页的大小为16KB, 那么总共有65536个页, 该值表示该页在所有页中的位置
     * 若此表空间的ID为10, 那么搜索页(10, 1)就表示找表a中的第2个页
     */
    private int offset;

    /**
     * FIL_PAGE_PREV(4): 当前页的上一个页, B+Tree特性决定了叶子节点必须是双向列表
     */
    private int prev;

    /**
     * FIL_PAGE_NEXT(4): 当前页的下一个页, B+Tree特性决定了叶子节点必须是双向列表
     */
    private int next;

    /**
     * FIL_PAGE_LSN(8): 该值代表该页最后被修改的日志序列位置Log Sequence Number
     */
    private long lsn;

    /**
     * FIL_PAGE_TYPE(2): InnoDB存储引擎页的类型, 常见的类型{@link PageType}
     */
    private PageType type;

    /**
     * FIL_PAGE_FILE_FLUSH_LSN(8): 该值仅在系统表空间中的一个页中定义, 代表文件至少被更新到了该LSN值.
     * 对于独立表空间, 该值都为0
     */
    private long flushLsn;

    /**
     * FIL_PAGE_ARCH_LOG_NO_OR_SPACE_ID(4): 该值代表页属于哪个表空间
     */
    private int spaceId;

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
     * PAGE_LEVEL(2): 当前页在索引树中的位置, 0x00代表叶节点, 即叶结点总是在第0层
     */
    private int level;

    /**
     * PAGE_INDEX_ID(2): 索引ID, 表示当前页属于哪个索引
     */
    private long indexId;

    /**
     * PAGE_BTR_SEG_LEAF(10): B+树数据页非叶节点所在段的segment header, 该值仅在B+Tree的Root页中定义
     */
    private String leaf;

    /**
     * PAGE_BTR_SEG_TOP(10):B+树数据页所在段的segment header, 该值仅在B+Tree的Root页中定义
     */
    private String top;

    /* --------------------Infimum & Supremum Records-------------------- */
    private Row infimum;

    private List<Row> records;

    private Row supremum;
    /* --------------------User Records-------------------- */
    /* --------------------Free Space-------------------- */
    /* --------------------Page Directory-------------------- */
    /* --------------------File Trailer-------------------- */

    /**
     * 与FIL_PAGE_SPACE_OR_CHKSUM比较
     */
    private int trailerChecksum;

    /**
     * 与FIL_PAGE_LSN比较
     */
    private int trailerLsn;

    public Page(PageReader reader) {
        this.checksum = reader.readInt();
        this.offset = reader.readInt();
        this.prev = reader.readInt();
        this.next = reader.readInt();
        this.lsn = reader.readLong();
        this.type = PageType.ofValue(reader.readShort());
        this.flushLsn = reader.readLong();
        this.spaceId = reader.readInt();

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
        this.leaf = reader.readString(10);
        this.top = reader.readString(10);

        if (this.type == PageType.FIL_PAGE_INDEX) {
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

}
