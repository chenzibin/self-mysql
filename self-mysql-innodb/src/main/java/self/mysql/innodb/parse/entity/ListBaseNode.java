package self.mysql.innodb.parse.entity;

import lombok.Data;
import self.mysql.innodb.parse.PageReader;

/**
 * 链表的基节点, 指向ListNode
 * 这个结构中包含了链表的头节点和尾节点的指针以及这个链表中包含了多少节点的信息
 *
 * @author chenzibin
 * @date 2023/2/2
 */
@Data
public class ListBaseNode {

    /**
     * List length(4): 表明该链表一共有多少节点
     */
    private int listLength;

    /**
     * First Node Page Number(4): 该链表的头节点所在页号
     */
    private int firstNodePageNumber;

    /**
     * First Node Offset(4): 该链表的头节点所在页中的偏移量
     */
    private int firstNodeOffset;

    /**
     * Last Node Page Number(4): 该链表的尾节点所在页号
     */
    private int lastNodePageNumber;

    /**
     * Last Node Offset(4): 该链表的尾节点所在页中的偏移量
     */
    private int lastNodeOffset;

    public ListBaseNode(PageReader reader) {
        this.listLength = reader.readInt();
        this.firstNodePageNumber = reader.readInt();
        this.firstNodeOffset = reader.readShort();
        this.lastNodePageNumber = reader.readInt();
        this.lastNodeOffset = reader.readShort();
    }
}
