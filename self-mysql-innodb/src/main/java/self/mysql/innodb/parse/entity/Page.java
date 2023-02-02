package self.mysql.innodb.parse.entity;

import lombok.Data;
import self.mysql.innodb.parse.PageReader;

import self.mysql.innodb.parse.entity.page.DefaultPageContent;
import self.mysql.innodb.parse.entity.page.FileSystemHeaderPageContent;
import self.mysql.innodb.parse.entity.page.IndexPageContent;
import self.mysql.innodb.parse.entity.page.PageContent;

/**
 * Page
 *
 * @author chenzb
 * @date 2020/4/20
 */
@Data
public class Page {

    private PageHeader header;

    private PageContent content;

    private PageTrailer trailer;



    /* --------------------User Records-------------------- */
    /* --------------------Free Space-------------------- */
    /* --------------------Page Directory-------------------- */
    /* --------------------File Trailer-------------------- */



    public Page(PageReader reader) {
        this.header = new PageHeader(reader);
        this.content = initPageContent(this.header.getType());
        this.content.parse(reader);
        this.trailer = new PageTrailer(reader);
    }

    private PageContent initPageContent(PageType type) {
        if (type == null) {
            return new DefaultPageContent();
        }
        switch (type) {
            case FIL_PAGE_TYPE_FSP_HDR:
                return new FileSystemHeaderPageContent();
            case FIL_PAGE_INDEX:
                return new IndexPageContent();
            default:
                return new DefaultPageContent();
        }
    }

}
