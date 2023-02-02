package self.mysql.innodb.parse.entity;

import java.util.stream.IntStream;
import lombok.Data;
import self.mysql.innodb.parse.BitReader;
import self.mysql.innodb.parse.PageReader;

/**
 * XDES Entry(40b): 区描述条目
 *
 * @author chenzibin
 * @date 2023/2/2
 */
@Data
public class ExtentDescriptorEntry {

    /**
     * Segment ID(8b): 表示该区所在的段。当然前提是该区已经被分配给某个段了，不然的话该字段的值没啥意义。（有直属于表空间的区）
     */
    private long segmentId;

    /**
     * List Node(12b):这个部分可以将若干个XDES Entry结构串联成一个链表
     */
    private ListNode listNode;

    /**
     * State(4b): 这个字段表明区的状态。{@link ExtentState}
     */
    private ExtentState state;

    /**
     * Page State Bitmap(16b): 这个部分共占用16个字节，也就是128个比特位。我们说一个区默认有64个页，这128个比特位被划分为64个部分，每个部分2个比特位，对应区中的一个页。
     * 比如Page State Bitmap部分的第1和第2个比特位对应着区中的第1个页面，第3和第4个比特位对应着区中的第2个页面，依此类推，Page State Bitmap部分的第127和128个比特位对应着区中的第64个页面。
     * 这两个比特位的第一个位表示对应的页是否是空闲的，第二个比特位还没有用
     */
    private PageState[] pageStates = new PageState[64];

    public ExtentDescriptorEntry(PageReader reader) {
        this.segmentId = reader.readLong();
        this.listNode = new ListNode(reader);
        this.state = ExtentState.ofValue(reader.readInt());

        BitReader bitReader = new BitReader(reader.readInts(4));
        IntStream.range(0, 64).forEach(j -> pageStates[j] = new PageState(bitReader));
    }
}
