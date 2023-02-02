package self.mysql.innodb.parse.entity.page;

import lombok.Data;
import self.mysql.innodb.parse.PageReader;

/**
 * @author chenzibin
 * @date 2023/2/1
 */
@Data
public class DefaultPageContent implements PageContent {

    @Override
    public void parse(PageReader reader) {

    }
}
