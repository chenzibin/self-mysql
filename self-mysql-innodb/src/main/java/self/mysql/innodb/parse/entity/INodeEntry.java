package self.mysql.innodb.parse.entity;

import java.util.stream.IntStream;
import lombok.Data;
import self.mysql.innodb.parse.PageReader;

/**
 * INODE Entry(192): 段描述信息
 *
 * @author chenzibin
 * @date 2023/2/2
 */
@Data
public class INodeEntry {

    /**
     * Segment ID(8): 就是指这个INODE Entry结构对应的段的编号（ID）
     */
    private long segmentId;

    /**
     * NOT_FULL_N_USED(4): 这个字段指的是在NOT_FULL链表中已经使用了多少个页面。
     */
    private int notFullUsedNum;

    /**
     * List Base Node For Free List(16): 该段FREE链表的基节点
     */
    private ListBaseNode freeListBaseNode;

    /**
     * List Base Node For Not Full List(16): 该段Not Full链表的基节点
     */
    private ListBaseNode notFullListBaseNode;

    /**
     * List Base Node For Full List(16): 该段Full链表的基节点
     */
    private ListBaseNode fullListBaseNode;

    /**
     * Magic Number(4): 这个值是用来标记这个INODE Entry是否已经被初始化了（初始化的意思就是把各个字段的值都填进去了）。如果这个数字是值的97937874，表明该INODE Entry已经初始化，否则没有被初始化。
     */
    private int magicNumber;

    /**
     * Fragment Array Entry(4 * 32 = 128): 段是一些零散页面和一些完整的区的集合，每个Fragment Array Entry结构都对应着一个零散的页面，这个结构一共4个字节，表示一个零散页面的页号。
     */
    private int[] fragmentArrayEntries = new int[32];

    public INodeEntry(PageReader reader) {
        this.segmentId = reader.readLong();
        this.notFullUsedNum = reader.readInt();
        this.freeListBaseNode = reader.readListBaseNode();
        this.notFullListBaseNode = reader.readListBaseNode();
        this.fullListBaseNode = reader.readListBaseNode();
        this.magicNumber = reader.readInt();
        IntStream.range(0, 32).forEach(i -> fragmentArrayEntries[i] = reader.readInt());
    }
}
