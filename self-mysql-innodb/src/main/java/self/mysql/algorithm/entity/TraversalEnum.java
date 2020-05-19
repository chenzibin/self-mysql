package self.mysql.algorithm.entity;

/**
 * TraversalEnum
 *
 * @author chenzb
 * @date 2020/4/26
 */
public enum TraversalEnum {

    /**
     * 前序遍历
     */
    PRE_ORDER,

    /**
     * 中序遍历
     */
    IN_ORDER,

    /**
     * 后序遍历
     */
    POST_ORDER,

    /**
     * 层次遍历
     */
    LEVEL_ORDER,

    /**
     * 广度优先遍历
     * BFS: Breadth First Search
     */
    BREADTH_ORDER,

    /**
     * 深度优先遍历
     * DFS: Deep First Search
     */
    DEEP_ORDER
}
