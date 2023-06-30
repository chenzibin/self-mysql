package self.mysql.innodb.parse.entity;

import lombok.Data;
import self.mysql.common.SpiSelector;
import self.mysql.innodb.parse.PageReader;

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
        this.content = SpiSelector.getPageInstance(this.header.getType());
        this.content.parse(reader);
        this.trailer = new PageTrailer(reader);
    }

}
