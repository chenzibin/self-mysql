package self.mysql.innodb.parse;

import self.mysql.innodb.parse.entity.Page;

/**
 * PageParse
 *
 * @author chenzb
 * @date 2020/4/20
 */
public class PageParse {

    public static Page parse(byte[] buf) {
        PageReader reader = new PageReader(buf);
        return new Page(reader);
    }
}
