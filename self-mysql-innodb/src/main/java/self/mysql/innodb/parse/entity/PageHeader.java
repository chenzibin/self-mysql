package self.mysql.innodb.parse.entity;

import lombok.Data;
import self.mysql.innodb.parse.PageReader;

/**
 * File Page Header(38b): 页头部信息，包含每个页的通用信息
 *
 * @author chenzibin
 * @date 2023/2/1
 */
@Data
public class PageHeader {

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

    public PageHeader(PageReader reader) {
        this.checksum = reader.readInt();
        this.offset = reader.readInt();
        this.prev = reader.readInt();
        this.next = reader.readInt();
        this.lsn = reader.readLong();
        this.type = PageType.ofValue(reader.readShort());
        this.flushLsn = reader.readLong();
        this.spaceId = reader.readInt();
    }
}
