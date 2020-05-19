package self.mysql.algorithm;

import self.mysql.algorithm.entity.AlgorithmEnum;
import self.mysql.algorithm.entity.TraversalEnum;

import java.util.List;

/**
 * Tree
 *
 * @author chenzb
 * @date 2020/4/26
 */
public interface Tree<E extends Comparable<E>> {

    /**
     * 添加指定节点到树中
     *
     * @param e 添加的节点
     * @return 是否添加成功
     */
    boolean add(E e);

    /**
     * 从树中移除指定节点
     *
     * @param e 移除的节点
     * @return 是否移除成功
     */
    boolean remove(E e);

    /**
     * 统计树的节点数
     *
     * @return 节点数
     */
    int size();

    /**
     * 按指定顺序{@link TraversalEnum}遍历树节点
     * @see TraversalEnum
     *
     * @param traversal 遍历方式
     * @param algorithm 算法
     * @return 遍历结果
     */
    List<E> traversal(TraversalEnum traversal, AlgorithmEnum algorithm);
}
