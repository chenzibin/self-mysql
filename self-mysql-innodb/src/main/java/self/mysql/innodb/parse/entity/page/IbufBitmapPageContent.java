package self.mysql.innodb.parse.entity.page;

import lombok.Data;
import self.mysql.common.PageSpi;
import self.mysql.innodb.parse.PageReader;
import self.mysql.innodb.parse.entity.PageType;

/**
 * 记录Change Buffer相关信息，每个分组的第二个页面
 *
 * @author chenzibin
 * @date 2023/2/3
 */
@Data
@PageSpi(PageType.FIL_PAGE_IBUF_BITMAP)
public class IbufBitmapPageContent implements PageContent{

    @Override
    public void parse(PageReader reader) {

    }
}
