package self.mysql.innodb.parse.entity.page;

import self.mysql.innodb.parse.PageReader;

/**
 * @author chenzibin
 * @date 2023/2/1
 */
public interface PageContent {

    void parse(PageReader reader);
}
