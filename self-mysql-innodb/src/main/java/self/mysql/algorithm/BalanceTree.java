package self.mysql.algorithm;

import self.mysql.algorithm.entity.AlgorithmEnum;
import self.mysql.algorithm.entity.TraversalEnum;

import java.io.Serializable;
import java.util.List;

/**
 * B+ Tree
 *
 * @author chenzb
 * @date 2020/4/26
 */
public class BalanceTree implements Tree {

    @Override
    public boolean add(Comparable comparable) {
        return false;
    }

    @Override
    public boolean remove(Comparable comparable) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public List traversal(TraversalEnum traversal, AlgorithmEnum algorithm) {
        return null;
    }
}
