// DRZEWO AVL
package ref_humbold.algolib.structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AVLTree<E extends Comparable<E>>
    implements Iterable<E>
{
    private interface AVLNode<T extends Comparable<T>>
    {
        T getElement();

        void setElement(T element);

        int getHeight();

        AVLNode<T> getLeft();

        void setLeft(AVLNode<T> node);

        AVLNode<T> getRight();

        void setRight(AVLNode<T> node);

        AVLNode<T> getParent();

        void setParent(AVLNode<T> node);

        @Override
        String toString();

        /**
         * Wyliczanie balansu wierzchołka.
         * @return wartość balansu
         */
        int countBalance();

        /**
         * Wylicza wysokość wierzchołka.
         */
        void recountHeight();

        /**
         * Wyszukiwanie minimum w poddrzewie.
         * @return węzeł z minimalną wartością w poddrzewie
         */
        AVLNode<T> minimum();

        /**
         * Wyszukiwanie maksimum w ukorzenionym poddrzewie.
         * @return węzeł z maksymalną wartością w poddrzewie
         */
        AVLNode<T> maximum();
    }

    private static class AVLInnerNode<T extends Comparable<T>>
        implements AVLNode<T>
    {
        /**
         * Wartość w węźle.
         */
        private T element;

        /**
         * Wysokość węzła.
         */
        private int height = 1;

        /**
         * Lewy syn węzła.
         */
        private AVLNode<T> left = null;

        /**
         * Prawy syn węzła.
         */
        private AVLNode<T> right = null;

        /**
         * Ojciec węzła.
         */
        private AVLNode<T> parent = null;

        public AVLInnerNode(T element)
        {
            this.element = element;
        }

        @Override
        public T getElement()
        {
            return element;
        }

        @Override
        public void setElement(T element)
        {
            this.element = element;
        }

        @Override
        public int getHeight()
        {
            return height;
        }

        @Override
        public AVLNode<T> getLeft()
        {
            return left;
        }

        @Override
        public void setLeft(AVLNode<T> node)
        {
            left = node;

            if(left != null)
                left.setParent(this);

            recountHeight();
        }

        @Override
        public AVLNode<T> getRight()
        {
            return right;
        }

        @Override
        public void setRight(AVLNode<T> node)
        {
            right = node;

            if(right != null)
                right.setParent(this);

            recountHeight();
        }

        @Override
        public AVLNode<T> getParent()
        {
            return parent;
        }

        @Override
        public void setParent(AVLNode<T> node)
        {
            parent = node;
        }

        @Override
        public String toString()
        {
            String leftString = left == null ? "" : left.toString();
            String rightString = right == null ? "" : right.toString();

            return "[" + leftString + " " + element.toString() + " " + rightString + "]";
        }

        @Override
        public int countBalance()
        {
            int leftHeight = left == null ? 0 : left.getHeight();
            int rightHeight = right == null ? 0 : right.getHeight();

            return leftHeight - rightHeight;
        }

        @Override
        public void recountHeight()
        {
            int leftHeight = left == null ? 0 : left.getHeight();
            int rightHeight = right == null ? 0 : right.getHeight();

            height = Math.max(leftHeight, rightHeight) + 1;
        }

        @Override
        public AVLNode<T> minimum()
        {
            return left == null ? this : left.minimum();
        }

        @Override
        public AVLNode<T> maximum()
        {
            return right == null ? this : right.maximum();
        }
    }

    private static class AVLRootNode<T extends Comparable<T>>
        implements AVLNode<T>
    {
        /**
         * Wewnętrzne wierzchołki.
         */
        private AVLNode<T> inner = null;

        public AVLRootNode()
        {
        }

        @Override
        public T getElement()
        {
            return null;
        }

        @Override
        public void setElement(T element)
        {
        }

        @Override
        public int getHeight()
        {
            return -1;
        }

        @Override
        public AVLNode<T> getLeft()
        {
            return inner;
        }

        @Override
        public void setLeft(AVLNode<T> node)
        {
            inner = node;
        }

        @Override
        public AVLNode<T> getRight()
        {
            return inner;
        }

        @Override
        public void setRight(AVLNode<T> node)
        {
            inner = node;
        }

        @Override
        public AVLNode<T> getParent()
        {
            return null;
        }

        @Override
        public void setParent(AVLNode<T> node)
        {
        }

        @Override
        public String toString()
        {
            return "[Null]";
        }

        @Override
        public int countBalance()
        {
            return 0;
        }

        @Override
        public void recountHeight()
        {
        }

        @Override
        public AVLNode<T> minimum()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public AVLNode<T> maximum()
        {
            throw new UnsupportedOperationException();
        }
    }

    private abstract class AVLIterator
        implements Iterator<E>
    {
        /**
         * Aktualny węzeł.
         */
        protected AVLNode<E> currentNode;

        public AVLIterator(AVLNode<E> node)
        {
            currentNode = node;
        }

        @Override
        public boolean hasNext()
        {
            return currentNode.getHeight() >= 0;
        }

        @Override
        public abstract E next()
            throws NoSuchElementException;

        /**
         * Wyznaczanie następnika węzła w drzewie.
         * @param node węzeł
         * @return węzeł z następną wartością
         */
        protected AVLNode<E> successor(AVLNode<E> node)
        {
            AVLNode<E> succ = node;

            if(node.getRight() != null)
                return node.getRight().minimum();

            while(succ.getHeight() >= 0 && succ.getElement().compareTo(node.getElement()) <= 0)
                succ = succ.getParent();

            return succ;
        }

        /**
         * Wyznaczanie poprzednika węzła w drzewie.
         * @param node węzeł
         * @return węzeł z poprzednią wartością
         */
        protected AVLNode<E> predecessor(AVLNode<E> node)
        {
            AVLNode<E> pred = node;

            if(node.getLeft() != null)
                return node.getLeft().maximum();

            while(pred.getHeight() >= 0 && pred.getElement().compareTo(node.getElement()) >= 0)
                pred = pred.getParent();

            return pred;
        }
    }

    private class AVLFwdIterator
        extends AVLIterator
    {

        public AVLFwdIterator(AVLNode<E> node)
        {
            super(node);
        }

        @Override
        public E next()
            throws NoSuchElementException
        {
            if(!hasNext())
                throw new NoSuchElementException();

            E returnValue = currentNode.getElement();

            currentNode = successor(currentNode);

            return returnValue;
        }
    }

    private class AVLRevIterator
        extends AVLIterator
    {
        public AVLRevIterator(AVLNode<E> node)
        {
            super(node);
        }

        @Override
        public E next()
            throws NoSuchElementException
        {
            if(!hasNext())
                throw new NoSuchElementException();

            E returnValue = currentNode.getElement();

            currentNode = predecessor(currentNode);

            return returnValue;
        }
    }

    /**
     * Korzeń drzewa.
     */
    private AVLNode<E> tree = new AVLRootNode<>();

    /**
     * Liczba elementów drzewa.
     */
    private int elems = 0;

    public AVLTree()
    {
    }

    public AVLTree(Iterable<E> iterable)
    {
        this();

        for(E e : iterable)
            add(e);
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

    /**
     * Tworzenie iteratora dla drzewa.
     * @return obiekt iteratora
     */
    @Override
    public AVLIterator iterator()
    {
        return new AVLFwdIterator(getInnerRoot().minimum());
    }

    /**
     * Tworzenie odwróconego iteratora dla drzewa.
     * @return obiekt odwróconego iteratora
     */
    public AVLIterator descendingIterator()
    {
        return new AVLRevIterator(getInnerRoot().maximum());
    }

    /**
     * Określanie pustości drzewa.
     * @return czy drzewo jest puste
     */
    public boolean isEmpty()
    {
        return getInnerRoot() == null;
    }

    /**
     * Określanie liczby elementów drzewa.
     * @return liczba elemenów drzewa
     */
    public int size()
    {
        return elems;
    }

    /**
     * Sprawdzanie występowania elementu w drzewie.
     * @param element wartość do znalezienia
     * @return czy wartość w drzewie
     */
    public boolean contains(E element)
    {
        if(tree == null)
            return false;

        AVLNode<E> nodeParent = findNodeParent(element);

        return nodeParent == null || getSubtree(nodeParent, element) != null;
    }

    /**
     * Dodawanie elementu do drzewa.
     * @param element wartość do dodania
     * @return czy dodano nowy element
     */
    public boolean add(E element)
    {
        AVLNode<E> nodeParent = findNodeParent(element);
        AVLNode<E> theNode = nodeParent == null ? getInnerRoot() : getSubtree(nodeParent, element);

        if(theNode != null)
            return false;

        AVLNode<E> newNode = new AVLInnerNode<>(element);

        if(nodeParent != null)
        {
            if(element.compareTo(nodeParent.getElement()) > 0)
                nodeParent.setRight(newNode);
            else
                nodeParent.setLeft(newNode);

            rebalance(nodeParent);
        }
        else
            setInnerRoot(newNode);

        ++elems;

        return true;
    }

    /**
     * Usuwanie elementu z drzewa.
     * @param element wartość do usunięcia
     * @return czy element został usunięty
     */
    public boolean remove(E element)
    {
        AVLNode<E> nodeParent = findNodeParent(element);
        AVLNode<E> theNode = nodeParent == null ? getInnerRoot() : getSubtree(nodeParent, element);

        if(theNode == null)
            return false;

        if(nodeParent == null)
            deleteRoot(theNode);
        else
            deleteNode(theNode);

        return true;
    }

    /**
     * Usuwanie wszystkich elementów z drzewa.
     */
    public void clear()
    {
        setInnerRoot(null);
        elems = 0;
    }

    /**
     * @return wewnętrzny korzeń drzewa
     */
    private AVLNode<E> getInnerRoot()
    {
        return tree.getRight();
    }

    /**
     * @param node: węzeł, który zostanie wewnętrznym korzeniem
     */
    private void setInnerRoot(AVLNode<E> node)
    {
        if(node != null)
            node.setParent(tree);

        tree.setLeft(node);
    }

    /**
     * Sprawdzanie, czy węzeł jest wewnętrznym korzeniem.
     * @param node węzeł
     * @return czy węzeł to korzeń
     */
    private boolean isInnerRoot(AVLNode<E> node)
    {
        return node.getParent().getHeight() < 0;
    }

    /**
     * Sprawdzanie, czy węzeł jest lewym synem.
     * @param node węzeł
     * @return czy węzeł to lewy syn
     */
    private boolean isLeftSon(AVLNode<E> node)
    {
        return node.getParent().getLeft() == node;
    }

    /**
     * Sprawdzanie, czy węzeł jest prawym synem.
     * @param node węzeł
     * @return czy węzeł to prawy syn
     */
    private boolean isRightSon(AVLNode<E> node)
    {
        return node.getParent().getRight() == node;
    }

    /**
     * Wyznaczanie korzenia drzewa, w którym mógłby znależć się element.
     * @param node węzeł
     * @param element element
     * @return korzeń poddrzewa, w którym znalazłby się element
     */
    private AVLNode<E> getSubtree(AVLNode<E> node, E element)
    {
        if(element.equals(node.getElement()))
            return node;
        else if(element.compareTo(node.getElement()) < 0)
            return node.getLeft();
        else
            return node.getRight();
    }

    /*
     * Wyszukiwanie ojca węzła z daną wartością.
     * @param element wartość do znalezienia
     * @return ojciec węzła z wartością
     */
    private AVLNode<E> findNodeParent(E element)
    {
        AVLNode<E> treeIter = getInnerRoot();
        AVLNode<E> iterParent = null;

        while(treeIter != null)
            if(treeIter.getElement() == element)
                break;
            else
            {
                iterParent = treeIter;
                treeIter = getSubtree(treeIter, element);
            }

        return iterParent;
    }

    /**
     * Usuwanie elementu z korzenia drzewa.
     * @param root korzeń drzewa
     */
    private void deleteRoot(AVLNode<E> root)
    {
        if(root.getLeft() != null && root.getRight() != null)
            deleteNode(root);
        else if(root.getLeft() != null && root.getRight() == null)
        {
            E temp = root.getLeft().getElement();

            root.getLeft().setElement(root.getElement());
            root.setElement(temp);
            setInnerRoot(root.getLeft());
            root.setLeft(null);
            --elems;
        }
        else if(root.getLeft() == null && root.getRight() != null)
        {
            E temp = root.getRight().getElement();

            root.getRight().setElement(root.getElement());
            root.setElement(temp);
            setInnerRoot(root.getRight());
            root.setRight(null);
            --elems;
        }
        else
            clear();
    }

    /**
     * Usuwanie elementu z węzła wewnętrznego drzewa.
     * @param node węzeł do usunięcia
     */
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
            AVLNode<E> son = node.getLeft() != null ? node.getLeft() : node.getRight();
            AVLNode<E> nodeParent = node.getParent();

            replaceSubtree(node, son);
            rebalance(nodeParent);
            --elems;
        }
    }

    /**
     * Zamiana poddrzewa ukorzenionego w danym węźle.
     * @param node węzeł do zamiany
     * @param node2 korzeń nowego poddrzewa
     */
    private void replaceSubtree(AVLNode<E> node, AVLNode<E> node2)
    {
        if(isInnerRoot(node))
            setInnerRoot(node2);
        else if(isLeftSon(node))
            node.getParent().setLeft(node2);
        else if(isRightSon(node))
            node.getParent().setRight(node2);

        node.setParent(null);
    }

    /**
     * Rotowanie węzła wzdłuż krawędzi z jego ojcem.
     * @param node węzeł do rotacji
     */
    private void rotate(AVLNode<E> node)
    {
        if(isInnerRoot(node))
            return;

        AVLNode<E> upperNode = node.getParent();

        if(isRightSon(node))
        {
            upperNode.setRight(node.getLeft());
            replaceSubtree(upperNode, node);
            node.setLeft(upperNode);
        }
        else if(isLeftSon(node))
        {
            upperNode.setLeft(node.getRight());
            replaceSubtree(upperNode, node);
            node.setRight(upperNode);
        }
    }

    /**
     * Przywracanie balansowania na ścieżce od wierzchołka do korzenia.
     * @param node wierzchołek początkowy
     */
    private void rebalance(AVLNode<E> node)
    {
        while(node.getHeight() >= 0)
        {
            node.recountHeight();

            int newBalance = node.countBalance();

            if(newBalance >= 2)
            {
                if(node.getLeft().countBalance() > 0)
                    rotate(node.getLeft());
                else if(node.getLeft().countBalance() < 0)
                {
                    rotate(node.getLeft().getRight());
                    rotate(node.getLeft());
                }
            }
            else if(newBalance <= -2)
                if(node.getRight().countBalance() < 0)
                    rotate(node.getRight());
                else if(node.getRight().countBalance() > 0)
                {
                    rotate(node.getRight().getLeft());
                    rotate(node.getRight());
                }

            node = node.getParent();
        }
    }
}
