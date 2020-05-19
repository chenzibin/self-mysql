package self.mysql.innodb.parse;

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
}
