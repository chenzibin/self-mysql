package self.mysql.innodb.parse;

import cn.hutool.core.lang.Opt;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.Test;

/**
 * ByteTest
 *
 * @author chenzb
 * @date 2020/4/22
 */
public class ByteTest {

    @Test
    public void testByteToBitString() {
        byte b = 0x1f;
        System.out.println(Byte.toString(b));
    }

    @Test
    public void testCharAtBit() {
        byte b = -63;
        int i = b & 0x40 >> 6;
        System.out.println(i);
    }

    @Test
    public void test() {
        List<String> list = Arrays.asList("1", "2", "3", "4");

        System.out.println(list.subList(0, 1).size());
        System.out.println(list.subList(1, 1).size());
    }
}
