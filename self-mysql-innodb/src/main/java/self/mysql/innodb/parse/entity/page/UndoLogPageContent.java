package self.mysql.innodb.parse.entity.page;

import lombok.Data;
import self.mysql.common.PageSpi;
import self.mysql.innodb.parse.PageReader;
import self.mysql.innodb.parse.entity.ListNode;
import self.mysql.innodb.parse.entity.PageType;
import self.mysql.innodb.parse.entity.TrxUndoPageType;

/**
 * @author chenzibin
 * @date 2023/6/26
 */
@Data
@PageSpi(PageType.FIL_PAGE_UNDO_LOG)
public class UndoLogPageContent implements PageContent {

    /* --------------------Undo Page Header-------------------- */

    /**
     *  TRX_UNDO_PAGE_TYPE(2): 本页面存储的undo日志类型
     */
    private TrxUndoPageType trxUndoPageType;

    /**
     * TRX_UNDO_PAGE_START(2)：表示在当前页面中是从什么位置开始存储undo日志的，或者说表示第一条undo日志在本页面中的起始偏移量
     */
    private int trxUndoPageStart;

    /**
     * TRX_UNDO_PAGE_FREE：与上边的TRX_UNDO_PAGE_START对应，表示当前页面中存储的最后一条undo日志结束时的偏移量，或者说从这个位置开始，可以继续写入新的undo日志
     */
    private int trxUndoPageFree;

    /**
     * TRX_UNDO_PAGE_NODE：代表一个List Node结构（链表的普通节点，我们上边刚说的）。
     */
    private ListNode trxUndoPageNode;

    @Override
    public void parse(PageReader reader) {
        throw new UnsupportedOperationException();
    }
}
