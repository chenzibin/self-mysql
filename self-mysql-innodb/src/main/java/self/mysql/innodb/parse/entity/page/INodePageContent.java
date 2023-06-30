package self.mysql.innodb.parse.entity.page;

import java.util.stream.IntStream;
import lombok.Data;
import self.mysql.common.PageSpi;
import self.mysql.innodb.parse.PageReader;
import self.mysql.innodb.parse.entity.INodeEntry;
import self.mysql.innodb.parse.entity.ListNode;
import self.mysql.innodb.parse.entity.PageType;

/**
 * 第一个分组的第三个页面
 *
 * @author chenzibin
 * @date 2023/2/3
 */
@Data
@PageSpi(PageType.FIL_PAGE_INODE)
public class INodePageContent implements PageContent {

    /**
     * List Node For INODE Page List(12): 通用链表节点
     */
    private ListNode listNode;

    /**
     * INODE Entry(192 * 85 = 16320): 段描述信息
     */
    private INodeEntry[] nodes = new INodeEntry[85];

    /**
     * Empty Space(6): 未使用空间。用于页结构的填充，没啥实际意义
     */
    private String emptySpace;

    @Override
    public void parse(PageReader reader) {
        this.listNode = new ListNode(reader);
        IntStream.range(0, 85).forEach(i -> nodes[i] = new INodeEntry(reader));
        this.emptySpace = reader.readString(6);
    }
}
