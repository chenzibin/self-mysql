package self.mysql.innodb.parse;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @author chenzibin
 * @date 2023/2/2
 */
@Data

public class BitReader {

    private int[] datas;
    private int offset;

    public BitReader(int data) {
        this.datas = new int[]{data};
    }

    public BitReader(int[] datas) {
        this.datas = datas;
    }

    public boolean readBool() {
        return readInt(1) > 0;
    }

    public int readInt(int len) {
        int i = readData() >> (32 - len - offset) & (BigDecimal.valueOf(2).pow(len).intValue() - 1);
        offset += len;
        return i;
    }

    public int readData() {
        return datas[offset / 32];
    }
}
