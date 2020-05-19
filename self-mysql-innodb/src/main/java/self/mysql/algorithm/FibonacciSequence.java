package self.mysql.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * FibonacciSequence
 *
 * @author chenzb
 * @date 2020/5/7
 */
public class FibonacciSequence {

    List<Long> data;

    public FibonacciSequence() {
        this.data = new ArrayList<>();
        this.data.add(1L);
        this.data.add(1L);
    }

    public long get(int n) {
        fullData(n);
        return data.get(n - 1);
    }

    public long getCompute(int n) {
        if (n < 3) {
            return 1;
        }
        return getCompute(1, 1, n);
    }

    public long getCompute(int a, int b, int n) {
        return n == 3 ? a + b : getCompute(b, a + b, n - 1);
    }

    private void fullData(int n) {
        final int curSize = data.size();
        if (n > curSize) {
            long value = data.get(curSize - 1) + data.get(curSize - 2);
            data.add(value);
            fullData(n);
        }
    }
}
