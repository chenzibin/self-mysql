package self.mysql.innodb.parse.entity;

import lombok.Data;
import self.mysql.innodb.parse.BitReader;

/**
 * Page State(2bit): XDES Entry中记录页的状态
 *
 * @author chenzibin
 * @date 2023/2/2
 */
@Data
public class PageState {

    /**
     * Unused(1bit): 未使用位
     */
    private int unused;

    /**
     * Page Idle(1bit): 页是否已使用
     */
    private boolean idle;

    public PageState(BitReader bitReader) {
        this.unused = bitReader.readInt(1);
        this.idle = bitReader.readBool();
    }
}
