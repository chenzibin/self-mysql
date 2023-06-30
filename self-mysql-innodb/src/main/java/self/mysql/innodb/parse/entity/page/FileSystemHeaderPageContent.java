package self.mysql.innodb.parse.entity.page;

import lombok.Data;
import self.mysql.common.PageSpi;
import self.mysql.innodb.parse.PageReader;
import self.mysql.innodb.parse.entity.ExtentDescriptorEntry;
import self.mysql.innodb.parse.entity.ListBaseNode;
import self.mysql.innodb.parse.entity.PageType;
import self.mysql.innodb.parse.entity.SpaceFlags;

/**
 * 表空间头部信息，表空间的第一个页面, 第一个组的第一个页面，整个表空间仅一个该类型页面
 *
 * @author chenzibin
 * @date 2023/2/1
 */
@Data
@PageSpi(PageType.FIL_PAGE_TYPE_FSP_HDR)
public class FileSystemHeaderPageContent implements PageContent {

    /* --------------------File Space Header (112) -------------------- */

    /**
     * Space ID(4): 表空间ID
     */
    private int spaceId;

    /**
     * Not Used(4): 未使用字节，可忽略
     */
    private int notUsed;

    /**
     * Size(4): 当前表空间占有的页面数
     */
    private int size;

    /**
     * FREE Limit(4): 尚未被初始化的最小页号，大于或等于这个页号的区对应的XDES Entry结构都没有被加入FREE链表
     * 在该字段表示的页号之前的区都被初始化了，之后的区尚未被初始化-按区初始化，默认1区64页=1MB
     */
    private int freeLimit;

    /**
     * Space Flags(4): 表空间的一些占用存储空间比较小的属性
     */
    private SpaceFlags spaceFlags;

    /**
     * FRAG_N_USED(4): FREE_FRAG链表中已使用的页面数量
     */
    private int freeFragUsedNum;

    /**
     * List Base Node for FREE List(16): FREE链表的基节点
     */
    private ListBaseNode freeBaseNode;

    /**
     * List Base Node for FREE_FRAG List(16): FREE_FRAG链表的基节点
     */
    private ListBaseNode freeFragBaseNode;

    /**
     * List Base Node for FULL_FRAG List(16): FULL_FRAG链表的基节点
     */
    private ListBaseNode fullFragBaseNode;

    /**
     * Next Unused Segment ID(8): 当前表空间中下一个未使用的 Segment ID
     */
    private long nextUnusedSegmentId;

    /**
     * List Base Node for SEG_INODES_FULL List(16): SEG_INODES_FULL链表的基节点。该链表中的INODE类型的页面中已经没有空闲空间来存储额外的INODE Entry结构了。
     */
    private ListBaseNode segInodesFullBaseNode;

    /**
     * List Base Node for SEG_INODES_FREE List(16): SEG_INODES_FREE链表的基节点.该链表中的INODE类型的页面中还有空闲空间来存储额外的INODE Entry结构了。
     */
    private ListBaseNode segInodesFreeBaseNode;

    /* --------------------XDES Entry (10240) -------------------- */

    /**
     * XDES Entry集合（40b * 256 = 10240 = 10kb）: 管理一组（256个区）的条目信息
     */
    private ExtentDescriptorEntry[] extents = new ExtentDescriptorEntry[256];

    /**
     * Empty Space(5986): 用于页结构的填充，没啥实际意义
     */
    private String emptySpace;

    @Override
    public void parse(PageReader reader) {
        this.spaceId = reader.readInt();
        this.notUsed = reader.readInt();
        this.size = reader.readInt();
        this.freeLimit = reader.readInt();
        this.spaceFlags = reader.readSpaceFlags();
        this.freeFragUsedNum = reader.readInt();
        this.freeBaseNode = reader.readListBaseNode();
        this.freeFragBaseNode = reader.readListBaseNode();
        this.fullFragBaseNode = reader.readListBaseNode();
        this.nextUnusedSegmentId = reader.readLong();
        this.segInodesFullBaseNode = reader.readListBaseNode();
        this.segInodesFreeBaseNode = reader.readListBaseNode();

        for (int i = 0; i < 256; i++) {
            extents[i] = reader.readExtentDescriptorEntry();
        }

        this.emptySpace = reader.readString(5986);
    }
}