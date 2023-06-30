package self.mysql.innodb.parse;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.junit.Test;
import self.mysql.innodb.parse.entity.Page;

/**
 * @author chenzibin
 * @date 2023/2/3
 */
public class InnodbParseTest {

    @Test
    public void parseInnodbTableSpace() {
        String idb = "C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Data\\test\\group_stat_10000.ibd";
        parseInnodb(idb);
    }

    @Test
    public void parseInnodbSysTableSpace() {
        String idb = "C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Data\\sys\\sys_config.ibd";
        parseInnodb(idb);
    }

    public void parseInnodb(String ibdPath) {
        int pageSize = 16384;

        File file = new File(ibdPath);
        try (FileInputStream input = new FileInputStream(file)) {
            byte[] buffer = new byte[pageSize];
            while (input.read(buffer) > 0) {
                Page page = PageParse.parse(buffer);
                printPage(page);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printPage(Page page) {
        printPageLimit(page, "开始");
        System.out.println(JSONUtil.toJsonPrettyStr(page));
        printPageLimit(page, "结束");
        System.out.println("");
    }

    private void printPageLimit(Page page, String flag) {
        System.out.println(StrUtil.format("-------------{}：页号-{} 页类型-{}---------------------------", flag, page.getHeader().getOffset(), page.getHeader().getType()));
    }
}
