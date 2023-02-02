package self.mysql.innodb.parse.entity;

import lombok.Data;
import self.mysql.innodb.parse.PageReader;

/**
 * @author chenzibin
 * @date 2023/2/1
 */
@Data
public class PageTrailer {

    /**
     * (4): 与FIL_PAGE_SPACE_OR_CHKSUM比较
     */
    private int trailerChecksum;

    /**
     * (4): 与FIL_PAGE_LSN比较
     */
    private int trailerLsn;

    public PageTrailer(PageReader reader) {
        reader.pointTo(8, true);
        this.trailerChecksum = reader.readInt();
        this.trailerLsn = reader.readInt();
    }
}
