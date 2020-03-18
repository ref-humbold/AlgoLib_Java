// AVL tree structure
package algolib.structures;

import java.util.*;
import java.util.function.BiFunction;

public class AVLTree<E>
        extends AbstractSet<E>
{
    private final Comparator<? super E> comparator;
    private AVLNode<E> tree = null;
    private int count = 0;

    public AVLTree()
    {
        super();
        comparator = null;
    }

    public AVLTree(Collection<E> collection)
    {
        this();
        addAll(collection);
    }

    public AVLTree(Comparator<? super E> comparator)
    {
        super();
        this.comparator = comparator;
    }

    private AVLNode<E> getRoot()
    {
        return tree;
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

        return count == other.count && containsAll(other);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(toArray());
    }

    @Override
    public Iterator<E> iterator()
    {
        return new AVLIterator(getRoot().minimum());
    }

    public Iterator<E> descendingIterator()
    {
        return new AVLDescendingIterator(getRoot().maximum());
    }

    @Override
    public int size()
    {
        return count;
    }

    @Override
    public boolean contains(Object object)
    {
        return !isEmpty()
                && findNode(object, (node, obj) -> Objects.equals(node.getElement(), obj)) != null;
    }

    @Override
    public boolean add(E element)
    {
        AVLNode<E> nodeParent = findNode(element, (node, obj) -> {
            AVLNode<E> child = search(node, obj);

            return child == null || Objects.equals(obj, child.getElement());
        });

        if(nodeParent == null)
        {
            AVLNode<E> newNode = new AVLNode<>(element);
            setRoot(newNode);
            ++count;
            return true;
        }

        AVLNode<E> theNode = search(nodeParent, element);

        if(theNode == null)
        {
            AVLNode<E> newNode = new AVLNode<>(element);

            if(compare(element, nodeParent.getElement()) < 0)
                nodeParent.setLeft(newNode);
            else
                nodeParent.setRight(newNode);

            balance(newNode);
            ++count;
            return true;
        }

        return false;
    }

    @Override
    public boolean remove(Object object)
    {
        AVLNode<E> node = findNode(object, (n, obj) -> Objects.equals(n.getElement(), obj));

        if(node == null)
            return false;

        deleteNode(node);
        return true;
    }

    @Override
    public void clear()
    {
        setRoot(null);
        count = 0;
    }

    @Override
    public String toString()
    {
        StringBuilder returnBuilder = new StringBuilder("{|");

        for(E elem : this)
        {
            returnBuilder.append(elem);
            returnBuilder.append(", ");
        }

        String returnString = returnBuilder.toString();

        return returnString.substring(0, returnString.length() - 2) + "|}";
    }

    Comparator<? super E> comparator()
    {
        return comparator;
    }

    /**
     * Compares two elements using a comparator or a natural order.
     * @param obj1 first object
     * @param obj2 second object
     * @return zero if objects are equal, negative value if first is less than second,
     * positive value if first is greater than second
     */
    private int compare(Object obj1, Object obj2)
    {
        return comparator == null ? ((Comparable<Object>)obj1).compareTo(obj2)
                                  : ((Comparator<Object>)comparator).compare(obj1, obj2);
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

    private AVLNode<E> findNode(Object object, BiFunction<AVLNode<E>, Object, Boolean> predicate)
    {
        AVLNode<E> node = getRoot();

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
            --count;
        }
    }

    // Replaces the first node as a child of its parent with the second node.
    private void replaceNode(AVLNode<E> node1, AVLNode<E> node2)
    {
        if(node1.isLeftSon())
            node1.getParent().setLeft(node2);
        else if(node1.isRightSon())
            node1.getParent().setRight(node2);
        else
            setRoot(node2);

        node1.setParent(null);
    }

    // Rotates the node along the edge to its parent.
    private void rotate(AVLNode<E> node)
    {
        if(node.isRightSon())
        {
            AVLNode<E> upperNode = node.getParent();

            upperNode.setRight(node.getLeft());
            replaceNode(upperNode, node);
            node.setLeft(upperNode);
        }
        else if(node.isLeftSon())
        {
            AVLNode<E> upperNode = node.getParent();

            upperNode.setLeft(node.getRight());
            replaceNode(upperNode, node);
            node.setRight(upperNode);
        }
    }

    // Restores balancing on a path from specified node to the root.
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
        int leftHeight = node.getLeft() == null ? 0 : node.getLeft().getHeight();
        int rightHeight = node.getRight() == null ? 0 : node.getRight().getHeight();

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

        boolean isLeftSon()
        {
            return getParent() != null && getParent().getLeft() == this;
        }

        boolean isRightSon()
        {
            return getParent() != null && getParent().getRight() == this;
        }

        void countHeight()
        {
            int leftHeight = left == null ? 0 : left.getHeight();
            int rightHeight = right == null ? 0 : right.getHeight();

            height = Math.max(leftHeight, rightHeight) + 1;
        }

        AVLNode<T> minimum()
        {
            return left == null ? this : left.minimum();
        }

        AVLNode<T> maximum()
        {
            return right == null ? this : right.maximum();
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
                throws NoSuchElementException
        {
            if(!hasNext())
                throw new NoSuchElementException("No more elements in iterator.");

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
                throws NoSuchElementException
        {
            if(!hasNext())
                throw new NoSuchElementException("No more elements in iterator.");

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
