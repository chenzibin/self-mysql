package self.mysql.innodb.parse.entity;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * PageType
 *
 * @author chenzb
 * @date 2020/4/20
 */
@Getter
public enum PageType {

    /**
     * hex:  0x45BF
     * desc: B+树叶节点
     */
    FIL_PAGE_INDEX(0x45BF),

    /**
     * hex:  0x0002
     * desc: Undo Log 页
     */
    FIL_PAGE_UNDO_LOG(0x0002),

    /**
     * hex:  0x0003
     * desc: 索引节点
     */
    FIL_PAGE_INODE(0x0003),

    /**
     * hex:  0x0004
     * desc: Insert Buffer 空闲队列
     */
    FIL_PAGE_IBUF_FREE_LIST(0x0004),

    /**
     * hex:  0x0000
     * desc: 该页为最新分配
     */
    FIL_PAGE_TYPE_ALLOCATED(0x0000),

    /**
     * hex:  0x0005
     * desc: Insert Buffer 位图
     */
    FIL_PAGE_IBUF_BITMAP(0x0005),

    /**
     * hex:  0x0006
     * desc: 系统页
     */
    FIL_PAGE_TYPE_SYS(0x0006),

    /**
     * hex:  0x0007
     * desc: 事务系统数据
     */
    FIL_PAGE_TRX_SYS(0x0007),

    /**
     * hex:  0x0008
     * desc: File System Header
     */
    FIL_PAGE_TYPE_FSP_HDR(0x0008),

    /**
     * hex:  0x0009
     * desc: 事务系统数据
     */
    FIL_PAGE_TYPE_XDES(0x0009),

    /**
     * hex:  0x000A
     * desc: BLOB 页
     */
    FIL_PAGE_TYPE_BLOB(0x000A);

    private int value;

    PageType(int value) {
        this.value = value;
    }

    public static PageType ofValue(int value) {
        return Stream.of(PageType.values()).filter(type -> type.value == value).findFirst().orElse(null);
    }
}
