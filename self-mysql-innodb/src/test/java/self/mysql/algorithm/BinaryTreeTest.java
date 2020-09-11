package self.mysql.algorithm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import self.mysql.algorithm.entity.AlgorithmEnum;
import self.mysql.algorithm.entity.TraversalEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * BinaryTreeTest
 *
 * @author chenzb
 * @date 2020/4/27
 */
public class BinaryTreeTest {

    BinaryTree<Integer> tree;
    List<Integer> nodes;

    @Before
    public void before() {
        nodes = random(10);
//        nodes = Arrays.asList(1, 3, 2);
    }

    @Test
    public void testAdd() {
        tree = new BinaryTree<>();
        nodes.forEach(tree::add);
    }

    @Test
    public void testRemove() {
        BinaryTree<Integer> tree = new BinaryTree<>(nodes);
        tree.remove(nodes.get(1));
        tree.remove(nodes.get(2));
    }

    private List<Integer> random(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> new Double(Math.random() * 100).intValue())
                .collect(Collectors.toList());
    }

    @After
    public void after() {
        out("origin: %s", nodes);
        for (TraversalEnum traversal : TraversalEnum.values()) {
            for (AlgorithmEnum algorithm : AlgorithmEnum.values()) {
                out(String.format("%s - %s ", traversal, algorithm) + ": %s", tree.traversal(traversal, algorithm));
            }
        }
    }

    private void out(String format, List<Integer> nodeList) {
        if (nodeList == null) {
            return;
        }
        String nodeString = nodeList.stream().map(Object::toString).collect(Collectors.joining("、"));
        System.out.println(String.format(format, nodeString));
    }
}
