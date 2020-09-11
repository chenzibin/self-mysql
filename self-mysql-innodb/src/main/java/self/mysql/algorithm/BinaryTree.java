package self.mysql.algorithm;

import self.mysql.algorithm.entity.AlgorithmEnum;
import self.mysql.algorithm.entity.TraversalEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * BST--Binary Search Tree
 *
 * @author chenzb
 * @date 2020/4/26
 */
public class BinaryTree<E extends Comparable<E>> implements Tree<E> {

    Node<E> root;

    public BinaryTree() {
    }

    public BinaryTree(List<E> list) {
        if (list != null && !list.isEmpty()) {

        }
    }

    @Override
    public boolean add(E e) {
        if (root == null) {
            root = new Node<>(e);
            return true;
        }
        root.join(e);
        return false;
    }

    @Override
    public boolean remove(E e) {
        if (root == null) {
            return false;
        }
        // 获取要删除的节点
        Node deleteNode = get(e);
        if (deleteNode == null) {
            return false;
        }

        // 1、如果删除的节点无子节点
        // 2、如果删除的节点仅一个子节点
        // 3、如果删除的节点有两个子节点
        return false;
    }

    private Node<E> get(E e) {
        if (root == null) {
            return null;
        }
        return root.get(e);
    }

    @Override
    public int size() {
        return 0;
    }

    /**
     * example
     * *******************************
     *          2
     *        /  \
     *       1    3
     * *******************************
     * preOrder : 2 -> 1 -> 3
     * inOrder  : 1 -> 2 -> 3
     * postOrder: 1 -> 3 -> 2
     * *******************************
     *
     *
     * @param traversal 遍历方式
     * @param algorithm 算法
     * @return
     */
    @Override
    public List<E> traversal(TraversalEnum traversal, AlgorithmEnum algorithm) {
        if (root == null) {
            return null;
        }
        switch (algorithm) {
            case RECURSIVE:
                return recursiveTraversal(traversal);
            case ITERATE:
                return iterateTraversal(traversal);
            case MORRIS:
                return morrisTraversal(traversal);
            default:
                throw new IllegalArgumentException("not available algorithm: " + algorithm);
        }
    }

    private List<E> recursiveTraversal(TraversalEnum traversal) {
        switch (traversal) {
            case PRE_ORDER:
                return root.preOrderTraversal();
            case IN_ORDER:
                return root.inOrderTraversal();
            case POST_ORDER:
                return root.postOrderTraversal();
            default:
                throw new IllegalArgumentException("not available traversal: " + traversal);
        }
    }

    private List<E> iterateTraversal(TraversalEnum traversal) {
        switch (traversal) {
            case PRE_ORDER:
                return preOrderTraversal();
            case IN_ORDER:
                return inOrderTraversal();
            case POST_ORDER:
                return postOrderTraversal();
            default:
                throw new IllegalArgumentException("not available traversal: " + traversal);
        }
    }

    private List<E> morrisTraversal(TraversalEnum traversal) {
        switch (traversal) {
            case PRE_ORDER:
                return preOrderMorrisTraversal();
            case IN_ORDER:
                return inOrderMorrisTraversal();
            case POST_ORDER:
                return postOrderMorrisTraversal();
            default:
                throw new IllegalArgumentException("not available traversal: " + traversal);
        }
    }

    private List<E> preOrderMorrisTraversal() {
        return null;
    }

    private List<E> inOrderMorrisTraversal() {
        /*
         * 空间复杂度: O(1)
         * 遍历顺序： left -> root -> right
         *
         * 深度读取左节点-对所有读取到的左节点，通过其右节点链接上父节点
         * 左节点-通过链接回父节点-右节点-通过链接回父节点的父节点
         */
        List<E> nodes = new ArrayList<>();
        Node<E> current = root;
        while (current != null) {
            while (current.left != null) {
                Node<E> parent = current;
                current = parent.left;
                while (current.right != parent && current.right != null) {
                    current = current.right;
                }
                if (current.right == parent) {
                    current.right = null;
                    current = parent;
                    break;
                }
                current.right = parent;
                current = parent.left;
            }
            nodes.add(current.data);
            current = current.right;
        }
        return nodes;
    }

    private List<E> postOrderMorrisTraversal() {
        return null;
    }

    private List<E> preOrderTraversal() {
        List<E> nodes = new ArrayList<>();
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            Node<E> node = stack.pop();
            nodes.add(node.data);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return nodes;
    }

    private List<E> inOrderTraversal() {
        List<E> nodes = new ArrayList<>();
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        Node<E> rootNode = root;
        while (rootNode.left != null) {
            stack.push(rootNode.left);
            rootNode = rootNode.left;
        }
        while (!stack.empty()) {
            Node<E> node = stack.pop();
            nodes.add(node.data);
            if (node.right != null) {
                stack.push(node.right);
                node = node.right;
                while (node.left != null) {
                    stack.push(node.left);
                    node = node.left;
                }
            }
        }
        return nodes;
    }

    private List<E> postOrderTraversal() {
        List<E> nodes = new ArrayList<>();
        return nodes;
    }

    class Node<E extends Comparable<E>> {

        E data;
        Node<E> left;
        Node<E> right;

        Node(E e) {
            this.data = e;
        }

        void join(E e) {
            if (e.compareTo(this.data) > 0) {
                if (right != null) {
                    right.join(e);
                } else {
                    right = new Node<>(e);
                }
            } else {
                if (left != null) {
                    left.join(e);
                } else {
                    left = new Node<>(e);
                }
            }
        }

        public Node<E> get(E e) {
            int result = data.compareTo(e);
            if (result == 0) {
                return this;
            } else if (result > 0) {
                if (left != null) {
                    return left.get(e);
                }
            } else {
                if (right != null) {
                    return right.get(e);
                }
            }
            return null;
        }

        public List<E> preOrderTraversal() {
            List<E> nodes = new ArrayList<>();
            nodes.add(data);
            if (left != null) {
                nodes.addAll(left.preOrderTraversal());
            }
            if (right != null) {
                nodes.addAll(right.preOrderTraversal());
            }
            return nodes;
        }

        public List<E> inOrderTraversal() {
            List<E> nodes = new ArrayList<>();
            if (left != null) {
                nodes.addAll(left.inOrderTraversal());
            }
            nodes.add(data);
            if (right != null) {
                nodes.addAll(right.inOrderTraversal());
            }
            return nodes;
        }

        public List<E> postOrderTraversal() {
            List<E> nodes = new ArrayList<>();
            if (left != null) {
                nodes.addAll(left.postOrderTraversal());
            }
            if (right != null) {
                nodes.addAll(right.postOrderTraversal());
            }
            nodes.add(data);
            return nodes;
        }

        public int getExistChild() {
            int exist = 0;
            if (left != null) {
                exist += 1;
            }
            if (right != null) {
                exist += 2;
            }
            return exist;
        }
    }
}
