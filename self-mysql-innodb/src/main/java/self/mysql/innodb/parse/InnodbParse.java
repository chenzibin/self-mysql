package self.mysql.innodb.parse;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import self.mysql.innodb.parse.entity.Page;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * InnodbParse
 *
 * @author chenzb
 * @date 2020/4/20
 */
public class InnodbParse {

    public static void main(String[] args) {
        int pageSize = 16384;

        File file = new File("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Data\\test\\group_stat_10000.ibd");
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

    private static void printPage(Page page) {
        printPageLimit(page, "开始");
        System.out.println(JSONUtil.toJsonPrettyStr(page));
        printPageLimit(page, "结束");
        System.out.println("");
    }

    private static void printPageLimit(Page page, String flag) {
        System.out.println(StrUtil.format("-------------{}：页号-{} 页类型-{}---------------------------", flag, page.getHeader().getOffset(), page.getHeader().getType()));
    }

}
