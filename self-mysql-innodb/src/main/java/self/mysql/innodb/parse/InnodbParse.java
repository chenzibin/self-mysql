package self.mysql.innodb.parse;

import self.mysql.innodb.parse.entity.Page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

        File file = new File("self-mysql-innodb/src/main/resources/t.ibd");
        try (FileInputStream input = new FileInputStream(file)) {
            byte[] buffer = new byte[pageSize];
            while (input.read(buffer) > 0) {
                Page page = PageParse.parse(buffer);
                System.out.println(page);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
