package self.mysql.innodb.parse.entity;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

/**
 * PageType
 *
 * @author chenzb
 * @date 2020/4/20
 */
@Getter
@RequiredArgsConstructor
public enum PageType {

    /**
     * hex:  0x0000
     * desc: 该页为最新分配，未使用
     */
    FIL_PAGE_TYPE_ALLOCATED(0x0000, "该页为最新分配，未使用"),

    /**
     * hex:  0x0002
     * desc: Undo Log 页
     */
    FIL_PAGE_UNDO_LOG(0x0002, "Undo Log 页"),

    /**
     * hex:  0x0003
     * desc: 索引节点，段信息节点
     */
    FIL_PAGE_INODE(0x0003, "索引节点，段信息节点"),

    /**
     * hex:  0x0004
     * desc: Insert Buffer 空闲队列
     */
    FIL_PAGE_IBUF_FREE_LIST(0x0004, "Insert Buffer 空闲队列"),

    /**
     * hex:  0x0005
     * desc: Insert Buffer 位图
     */
    FIL_PAGE_IBUF_BITMAP(0x0005, "Insert Buffer 位图"),

    /**
     * hex:  0x0006
     * desc: 系统页
     */
    FIL_PAGE_TYPE_SYS(0x0006, "系统页"),

    /**
     * hex:  0x0007
     * desc: 事务系统数据
     */
    FIL_PAGE_TRX_SYS(0x0007, "事务系统数据"),

    /**
     * hex:  0x0008
     * desc: File System Header (表空间头部信息)
     */
    FIL_PAGE_TYPE_FSP_HDR(0x0008, "File System Header (表空间头部信息)"),

    /**
     * hex:  0x0009
     * desc: 事务系统数据，扩展描述页
     */
    FIL_PAGE_TYPE_XDES(0x0009, "事务系统数据，扩展描述页"),

    /**
     * hex:  0x000A
     * desc: BLOB 页
     */
    FIL_PAGE_TYPE_BLOB(0x000A, "BLOB 页"),

    /**
     * hex:  0x45BF
     * desc: B+树叶节点 (索引页，数据页)
     */
    FIL_PAGE_INDEX(0x45BF, "B+树叶节点 (索引页，数据页)");

    private final int value;

    private final String desc;

    public static PageType ofValue(int value) {
        return Stream.of(PageType.values()).filter(type -> type.value == value).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return StrUtil.format("{}({})", this.name(), this.desc);
    }
}
