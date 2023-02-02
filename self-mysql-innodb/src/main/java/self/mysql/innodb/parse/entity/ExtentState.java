package self.mysql.innodb.parse.entity;

import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

/**
 * @author chenzibin
 * @date 2023/2/2
 */
@RequiredArgsConstructor
public enum ExtentState {

    /**
     * desc: 未知的区
     */
    UNKNOWN(0),

    /**
     * desc: 空闲的区：现在还没有用到这个区中的任何页面
     */
    FREE(1),

    /**
     * desc: 有剩余空间的碎片区：表示碎片区中还有可用的页面
     */
    FREE_FRAG(2),

    /**
     * desc: 没有剩余空间的碎片区：表示碎片区中的所有页面都被使用，没有空闲页面
     */
    FULL_FRAG(3),

    /**
     * desc: 附属于某个段的区。每一个索引都可以分为叶子节点段和非叶子节点段，除此之外InnoDB还会另外定义一些特殊作用的段，在这些段中的数据量很大时将使用区来作为基本的分配单位。
     */
    FSEG(4);

    private final int value;

    public static ExtentState ofValue(int value) {
        return Stream.of(ExtentState.values()).filter(type -> type.value == value).findFirst().orElse(null);
    }
}
