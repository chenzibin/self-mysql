package self.mysql.innodb.parse.entity.page;

import lombok.Data;
import self.mysql.innodb.parse.PageReader;

/**
 * @author chenzibin
 * @date 2023/2/3
 */
@Data
public class DataDictionaryHeaderPageContent implements PageContent {

    @Override
    public void parse(PageReader reader) {

    }
}
