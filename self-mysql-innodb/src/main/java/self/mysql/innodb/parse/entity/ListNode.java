package self.mysql.innodb.parse.entity;

import lombok.Data;
import self.mysql.innodb.parse.PageReader;

/**
 * List Node(12b):这个部分可以将若干个相同state的XDES Entry串联成一个链表。
 * 每种类型{@link ExtentState}有对应的链表，通过ListBaseNode定位头尾ListNode, 再通过ListNode定位前后的ListNode
 *
 * @author chenzibin
 * @date 2023/2/2
 */
@Data
public class ListNode {

    /**
     * Prev Node Page Number(4): 前一个XDES Entry所在页号
     */
    private int prevNodePageNumber;

    /**
     * Prev Node Offset(4): 前一个XDES Entry所在页的偏移量
     */
    private int prevNodeOffset;

    /**
     * Next Node Page Number(4): 后一个XDES Entry所在页号
     */
    private int nextNodePageNumber;

    /**
     * Next Node Offset(4): 后一个XDES Entry所在页的偏移量
     */
    private int nextNodeOffset;

    public ListNode(PageReader reader) {
        this.prevNodePageNumber = reader.readInt();
        this.prevNodeOffset = reader.readShort();
        this.nextNodePageNumber = reader.readInt();
        this.nextNodeOffset = reader.readShort();
    }
}
