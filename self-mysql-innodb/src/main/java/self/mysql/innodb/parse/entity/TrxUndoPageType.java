package self.mysql.innodb.parse.entity;

import cn.hutool.core.util.StrUtil;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 这个TRX_UNDO_PAGE_TYPE属性可选的值就是上边的两个，用来标记本页面用于存储哪个大类的undo日志，不同大类的undo日志不能混着存储，比如一个Undo页面的TRX_UNDO_PAGE_TYPE属性值为TRX_UNDO_INSERT，那么这个页面就只能存储类型为TRX_UNDO_INSERT_REC的undo日志，其他类型的undo日志就不能放到这个页面中了
 * 之所以把undo日志分成两个大类，是因为类型为TRX_UNDO_INSERT_REC的undo日志在事务提交后可以直接删除掉，而其他类型的undo日志还需要为所谓的MVCC服务，不能直接删除掉，对它们的处理需要区别对待
 *
 * @author chenzibin
 * @date 2023/6/26
 */
@Getter
@RequiredArgsConstructor
public enum TrxUndoPageType {

    /**
     * hex:  0x0001
     * desc: 类型为TRX_UNDO_INSERT_REC的undo日志属于此大类，一般由INSERT语句产生，或者在UPDATE语句中有更新主键的情况也会产生此类型的undo日志
     */
    TRX_UNDO_INSERT(0x0001, ""),

    /**
     * hex:  0x0002
     * desc: 除了类型为TRX_UNDO_INSERT_REC的undo日志，其他类型的undo日志都属于这个大类，比如我们前边说的TRX_UNDO_DEL_MARK_REC、TRX_UNDO_UPD_EXIST_REC啥的，一般由DELETE、UPDATE语句产生的undo日志属于这个大类
     */
    TRX_UNDO_UPDATE(0x0002, "");

    private final int value;

    private final String desc;

    public static TrxUndoPageType ofValue(int value) {
        return Stream.of(TrxUndoPageType.values()).filter(type -> type.value == value).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return StrUtil.format("{}({})", this.name(), this.desc);
    }
}
