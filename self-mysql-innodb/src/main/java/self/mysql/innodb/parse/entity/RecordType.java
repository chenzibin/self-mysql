package self.mysql.innodb.parse.entity;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * PageType
 *
 * @author chenzb
 * @date 2020/4/20
 */
@Getter
public enum RecordType {

    /**
     * bin:  000
     * desc: 普通
     */
    NORMAL(0),

    /**
     * bin:  001
     * desc: B+树节点指针
     */
    BNODE(1),

    /**
     * bin:  010
     * desc: Infimum
     */
    INFIMUM(2),

    /**
     * bin:  011
     * desc: Supremum
     */
    SUPREMUM(3),

    /**
     * bin:  1xx
     * desc: 保留
     */
    RETAIN(4);

    private final int value;

    RecordType(int value) {
        this.value = value;
    }

    public static RecordType ofValue(int value) {
        return Stream.of(RecordType.values()).filter(type -> type.value == value).findFirst().orElse(RETAIN);
    }
}
