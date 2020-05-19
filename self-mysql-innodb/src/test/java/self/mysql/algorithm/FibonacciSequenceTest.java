package self.mysql.algorithm;

import org.junit.Test;

/**
 * FibonacciSequenceTest
 *
 * @author chenzb
 * @date 2020/5/7
 */
public class FibonacciSequenceTest {

    @Test
    public void testGet() {
        FibonacciSequence fibonacciSequence = new FibonacciSequence();
        System.out.println(fibonacciSequence.get(1000));
        System.out.println(fibonacciSequence.get(1001));
        System.out.println(fibonacciSequence.get(1002));
    }

    @Test
    public void testGetCompute() {
        FibonacciSequence fibonacciSequence = new FibonacciSequence();
        System.out.println(fibonacciSequence.getCompute(10));
    }
}
