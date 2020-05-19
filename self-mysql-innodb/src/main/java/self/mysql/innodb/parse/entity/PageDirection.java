package self.mysql.innodb.parse.entity;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * PageDirection
 *
 * @author chenzb
 * @date 2020/4/21
 */
@Getter
public enum PageDirection {

    PAGE_LEFT(0x01),
    PAGE_RIGHT(0x02),
    PAGE_SAME_REC(0x03),
    PAGE_SAME_PAGE(0x04),
    PAGE_NO_DIRECTION(0x05);

    private int value;

    PageDirection(int value) {
        this.value = value;
    }

    public static PageDirection ofValue(int value) {
        return Stream.of(PageDirection.values()).filter(direction -> direction.value == value).findFirst().orElse(null);
    }
}
