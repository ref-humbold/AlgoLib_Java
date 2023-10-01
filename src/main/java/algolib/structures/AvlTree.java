package algolib.structures;

import java.util.*;
import java.util.function.BiFunction;

/** Structure of AVL tree. */
public class AvlTree<E>
        extends AbstractSet<E>
{
    private final Comparator<? super E> comparator_;
    private AvlNode<E> tree = null;
    private int size_ = 0;

    public AvlTree()
    {
        super();
        comparator_ = null;
    }

    @SuppressWarnings("unchecked")
    public AvlTree(Collection<? extends E> collection)
    {
        super();
        comparator_ = collection instanceof AvlTree<? extends E> avlTree
                      ? (Comparator<? super E>)avlTree.comparator()
                      : collection instanceof SortedSet<? extends E> sortedSet
                        ? (Comparator<? super E>)sortedSet.comparator()
                        : collection instanceof PriorityQueue<? extends E> priorityQueue
                          ? (Comparator<? super E>)priorityQueue.comparator()
                          : null;
        addAll(collection);
    }

    public AvlTree(Comparator<? super E> comparator)
    {
        super();
        comparator_ = comparator;
    }

    @Override
    public boolean isEmpty()
    {
        return tree == null;
    }

    private void setRoot(AvlNode<E> node)
    {
        tree = node;

        if(tree != null)
            tree.setParent(null);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof AvlTree<?> other))
            return false;

        return size_ == other.size_ && containsAll(other);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(toArray());
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        Iterator<E> it = iterator();

        builder.append("{|");

        while(it.hasNext())
        {
            builder.append(it.next());

            if(it.hasNext())
                builder.append(", ");
        }

        builder.append("|}");
        return builder.toString();
    }

    /**
     * Gets the comparator.
     * @return the comparator
     */
    public Comparator<? super E> comparator()
    {
        return comparator_;
    }

    @Override
    public int size()
    {
        return size_;
    }

    @Override
    public void clear()
    {
        setRoot(null);
        size_ = 0;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new AvlIterator(tree != null ? tree.minimum() : null);
    }

    public Iterator<E> descendingIterator()
    {
        return new AvlDescendingIterator(tree != null ? tree.maximum() : null);
    }

    @Override
    public boolean contains(Object object)
    {
        return !isEmpty() && findNode(object, (node, obj) -> Objects.equals(node.getElement(), obj))
                                     != null;
    }

    @Override
    public boolean add(E e)
    {
        AvlNode<E> nodeParent = findNode(e, (node, obj) -> {
            AvlNode<E> child = search(node, obj);

            return child == null || Objects.equals(obj, child.getElement());
        });

        if(nodeParent == null)
        {
            AvlNode<E> newNode = new AvlNode<>(e);
            setRoot(newNode);
            ++size_;
            return true;
        }

        if(search(nodeParent, e) == null)
        {
            AvlNode<E> newNode = new AvlNode<>(e);

            if(compare(e, nodeParent.getElement()) < 0)
                nodeParent.setLeft(newNode);
            else
                nodeParent.setRight(newNode);

            balance(newNode);
            ++size_;
            return true;
        }

        return false;
    }

    @Override
    public boolean remove(Object object)
    {
        return Optional.ofNullable(
                               findNode(object, (n, obj) -> Objects.equals(n.getElement(), obj)))
                       .stream()
                       .peek(this::deleteNode)
                       .findFirst()
                       .isPresent();
    }

    @Override
    public boolean removeAll(Collection<?> objects)
    {
        return objects.stream().reduce(false, (acc, obj) -> remove(obj) || acc, Boolean::logicalOr);
    }

    private boolean isLeftSon(AvlNode<E> node)
    {
        return node.getParent() != null && node.getParent().getLeft() == node;
    }

    private boolean isRightSon(AvlNode<E> node)
    {
        return node.getParent() != null && node.getParent().getRight() == node;
    }

    // Compares two elements using comparator or natural order.
    @SuppressWarnings("unchecked")
    private int compare(Object obj1, Object obj2)
    {
        if(comparator_ == null)
            return ((Comparable<Object>)obj1).compareTo(obj2);

        return ((Comparator<Object>)comparator_).compare(obj1, obj2);
    }

    // Determines the subtree where given value might be present:
    // - node if element is in it
    // - left child if element is less than node's element
    // - right child if element is greater than node's element
    private AvlNode<E> search(AvlNode<E> node, Object object)
    {
        if(Objects.equals(object, node.getElement()))
            return node;

        int result = compare(object, node.getElement());

        if(result < 0)
            return node.getLeft();

        if(result > 0)
            return node.getRight();

        return node;
    }

    // Searches for node that satisfies given predicate with given value.
    private AvlNode<E> findNode(Object object, BiFunction<AvlNode<E>, Object, Boolean> predicate)
    {
        AvlNode<E> node = tree;

        while(node != null && !predicate.apply(node, object))
            node = search(node, object);

        return node;
    }

    // Removes inner node from the tree.
    private void deleteNode(AvlNode<E> node)
    {
        if(node.getLeft() != null && node.getRight() != null)
        {
            AvlNode<E> succ = node.getRight().minimum();
            E temp = succ.getElement();

            succ.setElement(node.getElement());
            node.setElement(temp);
            deleteNode(succ);
        }
        else
        {
            AvlNode<E> child = node.getLeft() != null ? node.getLeft() : node.getRight();

            if(node.getParent() != null)
            {
                AvlNode<E> nodeParent = node.getParent();

                replaceNode(node, child);
                balance(nodeParent);
            }
            else
            {
                setRoot(child);
            }
            --size_;
        }
    }

    // Replaces the first node as a child of its parent with the second node.
    private void replaceNode(AvlNode<E> node1, AvlNode<E> node2)
    {
        if(isLeftSon(node1))
            node1.getParent().setLeft(node2);
        else if(isRightSon(node1))
            node1.getParent().setRight(node2);
        else
            setRoot(node2);

        node1.setParent(null);
    }

    // Rotates the node along the edge to its parent.
    private void rotate(AvlNode<E> node)
    {
        if(isRightSon(node))
        {
            AvlNode<E> upperNode = node.getParent();

            upperNode.setRight(node.getLeft());
            replaceNode(upperNode, node);
            node.setLeft(upperNode);
        }
        else if(isLeftSon(node))
        {
            AvlNode<E> upperNode = node.getParent();

            upperNode.setLeft(node.getRight());
            replaceNode(upperNode, node);
            node.setRight(upperNode);
        }
    }

    // Restores balancing on the path from given node to the root.
    private void balance(AvlNode<E> node)
    {
        while(node != null)
        {
            node.countHeight();

            if(countBalance(node) > 1)
            {
                if(countBalance(node.getLeft()) > 0)
                    rotate(node.getLeft());
                else if(countBalance(node.getLeft()) < 0)
                {
                    rotate(node.getLeft().getRight());
                    rotate(node.getLeft());
                }
            }
            else if(countBalance(node) < -1)
                if(countBalance(node.getRight()) < 0)
                    rotate(node.getRight());
                else if(countBalance(node.getRight()) > 0)
                {
                    rotate(node.getRight().getLeft());
                    rotate(node.getRight());
                }

            node = node.getParent();
        }
    }

    private int countBalance(AvlNode<E> node)
    {
        int leftHeight = Optional.ofNullable(node.getLeft()).map(AvlNode::getHeight).orElse(0);
        int rightHeight = Optional.ofNullable(node.getRight()).map(AvlNode::getHeight).orElse(0);

        return leftHeight - rightHeight;
    }

    private static class AvlNode<T>
    {
        private T element;
        private int height = 1;
        private AvlNode<T> left = null;
        private AvlNode<T> right = null;
        private AvlNode<T> parent = null;

        AvlNode(T element)
        {
            this.element = element;
        }

        T getElement()
        {
            return element;
        }

        void setElement(T element)
        {
            this.element = element;
        }

        int getHeight()
        {
            return height;
        }

        AvlNode<T> getLeft()
        {
            return left;
        }

        void setLeft(AvlNode<T> node)
        {
            left = node;

            if(left != null)
                left.setParent(this);

            countHeight();
        }

        AvlNode<T> getRight()
        {
            return right;
        }

        void setRight(AvlNode<T> node)
        {
            right = node;

            if(right != null)
                right.setParent(this);

            countHeight();
        }

        AvlNode<T> getParent()
        {
            return parent;
        }

        void setParent(AvlNode<T> parent)
        {
            this.parent = parent;
        }

        void countHeight()
        {
            int leftHeight = Optional.ofNullable(left).map(AvlNode::getHeight).orElse(0);
            int rightHeight = Optional.ofNullable(right).map(AvlNode::getHeight).orElse(0);

            height = Math.max(leftHeight, rightHeight) + 1;
        }

        AvlNode<T> minimum()
        {
            return Optional.ofNullable(left).map(AvlNode::minimum).orElse(this);
        }

        AvlNode<T> maximum()
        {
            return Optional.ofNullable(right).map(AvlNode::maximum).orElse(this);
        }
    }

    private final class AvlIterator
            implements Iterator<E>
    {
        private AvlNode<E> currentNode;

        AvlIterator(AvlNode<E> node)
        {
            currentNode = node;
        }

        @Override
        public boolean hasNext()
        {
            return currentNode != null;
        }

        @Override
        public E next()
        {
            if(!hasNext())
                throw new NoSuchElementException("No more elements in iterator");

            E returnValue = currentNode.getElement();

            if(currentNode.getRight() != null)
                currentNode = currentNode.getRight().minimum();
            else
            {
                while(currentNode.getParent() != null
                              && currentNode.getParent().getLeft() != currentNode)
                    currentNode = currentNode.getParent();

                currentNode = currentNode.getParent();
            }

            return returnValue;
        }
    }

    private final class AvlDescendingIterator
            implements Iterator<E>
    {
        private AvlNode<E> currentNode;

        AvlDescendingIterator(AvlNode<E> node)
        {
            currentNode = node;
        }

        @Override
        public boolean hasNext()
        {
            return currentNode != null;
        }

        @Override
        public E next()
        {
            if(!hasNext())
                throw new NoSuchElementException("No more elements in iterator");

            E returnValue = currentNode.getElement();

            if(currentNode.getLeft() != null)
                currentNode = currentNode.getLeft().maximum();
            else
            {
                while(currentNode.getParent() != null
                              && currentNode.getParent().getRight() != currentNode)
                    currentNode = currentNode.getParent();

                currentNode = currentNode.getParent();
            }

            return returnValue;
        }
    }
}
