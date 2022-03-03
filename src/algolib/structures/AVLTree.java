// Structure of AVL tree
package algolib.structures;

import java.util.*;
import java.util.function.BiFunction;

public class AVLTree<E>
        extends AbstractSet<E>
{
    private final Comparator<? super E> comparator_;
    private AVLNode<E> tree = null;
    private int size_ = 0;

    public AVLTree()
    {
        super();
        comparator_ = null;
    }

    @SuppressWarnings("unchecked")
    public AVLTree(Collection<? extends E> collection)
    {
        super();

        if(collection instanceof AVLTree)
        {
            AVLTree<? extends E> avlTree = (AVLTree<? extends E>)collection;
            comparator_ = (Comparator<? super E>)avlTree.comparator();
        }
        else if(collection instanceof SortedSet)
        {
            SortedSet<? extends E> sortedSet = (SortedSet<? extends E>)collection;
            comparator_ = (Comparator<? super E>)sortedSet.comparator();
        }
        else if(collection instanceof PriorityQueue)
        {
            PriorityQueue<? extends E> priorityQueue = (PriorityQueue<? extends E>)collection;
            comparator_ = (Comparator<? super E>)priorityQueue.comparator();
        }
        else
            comparator_ = null;

        addAll(collection);
    }

    public AVLTree(Comparator<? super E> comparator)
    {
        super();
        comparator_ = comparator;
    }

    @Override
    public boolean isEmpty()
    {
        return tree == null;
    }

    private void setRoot(AVLNode<E> node)
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

        if(!(obj instanceof AVLTree))
            return false;

        AVLTree<?> other = (AVLTree<?>)obj;

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
        return new AVLIterator(tree != null ? tree.minimum() : null);
    }

    public Iterator<E> descendingIterator()
    {
        return new AVLDescendingIterator(tree != null ? tree.maximum() : null);
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
        AVLNode<E> nodeParent = findNode(e, (node, obj) -> {
            AVLNode<E> child = search(node, obj);

            return child == null || Objects.equals(obj, child.getElement());
        });

        if(nodeParent == null)
        {
            AVLNode<E> newNode = new AVLNode<>(e);
            setRoot(newNode);
            ++size_;
            return true;
        }

        if(search(nodeParent, e) == null)
        {
            AVLNode<E> newNode = new AVLNode<>(e);

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

    private boolean isLeftSon(AVLNode<E> node)
    {
        return node.getParent() != null && node.getParent().getLeft() == node;
    }

    private boolean isRightSon(AVLNode<E> node)
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
    private AVLNode<E> search(AVLNode<E> node, Object object)
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
    private AVLNode<E> findNode(Object object, BiFunction<AVLNode<E>, Object, Boolean> predicate)
    {
        AVLNode<E> node = tree;

        while(node != null && !predicate.apply(node, object))
            node = search(node, object);

        return node;
    }

    // Removes inner node from the tree.
    private void deleteNode(AVLNode<E> node)
    {
        if(node.getLeft() != null && node.getRight() != null)
        {
            AVLNode<E> succ = node.getRight().minimum();
            E temp = succ.getElement();

            succ.setElement(node.getElement());
            node.setElement(temp);
            deleteNode(succ);
        }
        else
        {
            AVLNode<E> child = node.getLeft() != null ? node.getLeft() : node.getRight();

            if(node.getParent() != null)
            {
                AVLNode<E> nodeParent = node.getParent();

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
    private void replaceNode(AVLNode<E> node1, AVLNode<E> node2)
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
    private void rotate(AVLNode<E> node)
    {
        if(isRightSon(node))
        {
            AVLNode<E> upperNode = node.getParent();

            upperNode.setRight(node.getLeft());
            replaceNode(upperNode, node);
            node.setLeft(upperNode);
        }
        else if(isLeftSon(node))
        {
            AVLNode<E> upperNode = node.getParent();

            upperNode.setLeft(node.getRight());
            replaceNode(upperNode, node);
            node.setRight(upperNode);
        }
    }

    // Restores balancing on a path from given node to the root.
    private void balance(AVLNode<E> node)
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

    private int countBalance(AVLNode<E> node)
    {
        int leftHeight = Optional.ofNullable(node.getLeft()).map(AVLNode::getHeight).orElse(0);
        int rightHeight = Optional.ofNullable(node.getRight()).map(AVLNode::getHeight).orElse(0);

        return leftHeight - rightHeight;
    }

    private static class AVLNode<T>
    {
        /** Value in the node */
        private T element;

        private int height = 1;
        private AVLNode<T> left = null;
        private AVLNode<T> right = null;
        private AVLNode<T> parent = null;

        AVLNode(T element)
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

        AVLNode<T> getLeft()
        {
            return left;
        }

        void setLeft(AVLNode<T> node)
        {
            left = node;

            if(left != null)
                left.setParent(this);

            countHeight();
        }

        AVLNode<T> getRight()
        {
            return right;
        }

        void setRight(AVLNode<T> node)
        {
            right = node;

            if(right != null)
                right.setParent(this);

            countHeight();
        }

        AVLNode<T> getParent()
        {
            return parent;
        }

        void setParent(AVLNode<T> parent)
        {
            this.parent = parent;
        }

        void countHeight()
        {
            int leftHeight = Optional.ofNullable(left).map(AVLNode::getHeight).orElse(0);
            int rightHeight = Optional.ofNullable(right).map(AVLNode::getHeight).orElse(0);

            height = Math.max(leftHeight, rightHeight) + 1;
        }

        AVLNode<T> minimum()
        {
            return Optional.ofNullable(left).map(AVLNode::minimum).orElse(this);
        }

        AVLNode<T> maximum()
        {
            return Optional.ofNullable(right).map(AVLNode::maximum).orElse(this);
        }
    }

    private class AVLIterator
            implements Iterator<E>
    {
        private AVLNode<E> currentNode;

        AVLIterator(AVLNode<E> node)
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

    private class AVLDescendingIterator
            implements Iterator<E>
    {
        private AVLNode<E> currentNode;

        AVLDescendingIterator(AVLNode<E> node)
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
