// DRZEWO AVL
package ref_humbold.algolib.structures;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class AVLTree<E> extends AbstractSet<E> {
    /**
     * Korzeń drzewa.
     */
    private AVLNode<E> tree = new AVLRootNode<>();

    /**
     * Liczba elementów drzewa.
     */
    private int elems = 0;

    public AVLTree() {
        super();
    }

    public AVLTree(Iterable<E> iterable) {
        super();

        for (E e : iterable)
            add(e);
    }

    /**
     * @return wewnętrzny korzeń drzewa
     */
    private AVLNode<E> getInnerRoot() {
        return tree.getParent();
    }

    /**
     * @param node: węzeł, który zostanie wewnętrznym korzeniem
     */
    private void setInnerRoot(AVLNode<E> node) {
        tree.setParent(node);
    }

    @Override
    public AVLIterator iterator() {
        return new AVLSuccIterator(getInnerRoot().minimum());
    }

    /**
     * @return obiekt odwróconego iteratora
     */
    public AVLIterator descendingIterator() {
        return new AVLPredIterator(getInnerRoot().maximum());
    }

    @Override
    public int size() {
        return elems;
    }

    @Override
    public boolean contains(Object object) {
        if (isEmpty())
            return false;

        AVLNode<E> nodeParent = findNodeParent(object);

        return nodeParent == null || getSubtree(nodeParent, object) != null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean add(E element) {
        AVLNode<E> nodeParent = findNodeParent(element);
        AVLNode<E> theNode = nodeParent == null ? getInnerRoot() : getSubtree(nodeParent, element);

        if (theNode != null)
            return false;

        AVLNode<E> newNode = new AVLInnerNode<>(element);

        if (nodeParent != null) {
            Comparable<E> comparable = (Comparable<E>) element;

            if (comparable.compareTo(nodeParent.getElement()) > 0)
                nodeParent.setRight(newNode);
            else
                nodeParent.setLeft(newNode);

            balance(nodeParent);
        } else
            setInnerRoot(newNode);

        ++elems;

        return true;
    }

    @Override
    public boolean remove(Object object) {
        AVLNode<E> nodeParent = findNodeParent(object);
        AVLNode<E> theNode = nodeParent == null ? getInnerRoot() : getSubtree(nodeParent, object);

        if (theNode == null)
            return false;

        if (nodeParent == null)
            deleteRoot(theNode);
        else
            deleteNode(theNode);

        return true;
    }

    @Override
    public void clear() {
        setInnerRoot(null);
        elems = 0;
    }

    @Override
    public String toString() {
        StringBuilder returnBuilder = new StringBuilder("{|");

        for (E elem : this) {
            returnBuilder.append(elem);
            returnBuilder.append(", ");
        }

        String returnString = returnBuilder.toString();

        return returnString.substring(0, returnString.length() - 2) + "|}";
    }

    /**
     * Sprawdzanie, czy węzeł jest wewnętrznym korzeniem.
     * @param node węzeł
     * @return czy węzeł to korzeń
     */
    private boolean isInnerRoot(AVLNode<E> node) {
        return node.getParent().getHeight() < 0;
    }

    /**
     * Wyznaczanie korzenia drzewa, w którym mógłby znaleźć się element.
     * @param node węzeł
     * @param object element
     * @return korzeń poddrzewa, w którym znalazłby się element
     */
    @SuppressWarnings("unchecked")
    private AVLNode<E> getSubtree(AVLNode<E> node, Object object) {
        int result = ((Comparable<E>) object).compareTo(node.getElement());

        if (result < 0)
            return node.getLeft();

        if (result > 0)
            return node.getRight();

        return node;
    }

    /**
     * Wyszukiwanie ojca węzła z daną wartością.
     * @param object wartość do znalezienia
     * @return ojciec węzła z wartością
     */
    private AVLNode<E> findNodeParent(Object object) {
        AVLNode<E> treeIter = getInnerRoot();
        AVLNode<E> iterParent = null;

        while (treeIter != null)
            if (Objects.equals(treeIter.getElement(), object))
                return iterParent;
            else {
                iterParent = treeIter;
                treeIter = getSubtree(treeIter, object);
            }

        return iterParent;
    }

    /**
     * Usuwanie elementu z korzenia drzewa.
     * @param root korzeń drzewa
     */
    private void deleteRoot(AVLNode<E> root) {
        if (root.getLeft() != null && root.getRight() != null)
            deleteNode(root);
        else {
            AVLNode<E> new_root = root.getLeft() != null ? root.getLeft() : root.getRight();

            setInnerRoot(new_root);
            --elems;
        }
    }

    /**
     * Usuwanie elementu z węzła wewnętrznego drzewa.
     * @param node węzeł do usunięcia
     */
    private void deleteNode(AVLNode<E> node) {
        if (node.getLeft() != null && node.getRight() != null) {
            AVLNode<E> succ = node.getRight().minimum();
            E temp = succ.getElement();

            succ.setElement(node.getElement());
            node.setElement(temp);
            deleteNode(succ);
        } else {
            AVLNode<E> son = node.getLeft() != null ? node.getLeft() : node.getRight();
            AVLNode<E> nodeParent = node.getParent();

            replaceSubtree(node, son);
            balance(nodeParent);
            --elems;
        }
    }

    /**
     * Zamiana poddrzewa ukorzenionego w danym węźle.
     * @param node węzeł do zamiany
     * @param node2 korzeń nowego poddrzewa
     */
    private void replaceSubtree(AVLNode<E> node, AVLNode<E> node2) {
        if (isInnerRoot(node))
            setInnerRoot(node2);
        else if (node.isLeftSon())
            node.getParent().setLeft(node2);
        else if (node.isRightSon())
            node.getParent().setRight(node2);

        node.setParent(null);
    }

    /**
     * Rotowanie węzła wzdłuż krawędzi z jego ojcem.
     * @param node węzeł do rotacji
     */
    private void rotate(AVLNode<E> node) {
        if (isInnerRoot(node))
            return;

        AVLNode<E> upperNode = node.getParent();

        if (node.isRightSon()) {
            upperNode.setRight(node.getLeft());
            replaceSubtree(upperNode, node);
            node.setLeft(upperNode);
        } else if (node.isLeftSon()) {
            upperNode.setLeft(node.getRight());
            replaceSubtree(upperNode, node);
            node.setRight(upperNode);
        }
    }

    /**
     * Przywracanie balansowania na ścieżce od wierzchołka do korzenia.
     * @param node wierzchołek początkowy
     */
    private void balance(AVLNode<E> node) {
        while (node.getHeight() > 0) {
            node.countHeight();

            int newBalance = node.countBalance();

            if (newBalance >= 2) {
                if (node.getLeft().countBalance() > 0)
                    rotate(node.getLeft());
                else if (node.getLeft().countBalance() < 0) {
                    rotate(node.getLeft().getRight());
                    rotate(node.getLeft());
                }
            } else if (newBalance <= -2)
                if (node.getRight().countBalance() < 0)
                    rotate(node.getRight());
                else if (node.getRight().countBalance() > 0) {
                    rotate(node.getRight().getLeft());
                    rotate(node.getRight());
                }

            node = node.getParent();
        }
    }

    private interface AVLNode<T> {
        T getElement();

        void setElement(T element);

        int getHeight();

        AVLNode<T> getLeft();

        void setLeft(AVLNode<T> node);

        AVLNode<T> getRight();

        void setRight(AVLNode<T> node);

        AVLNode<T> getParent();

        void setParent(AVLNode<T> node);

        /**
         * Sprawdzanie, czy węzeł jest lewym synem.
         * @return czy węzeł to lewy syn
         */
        boolean isLeftSon();

        /**
         * Sprawdzanie, czy węzeł jest prawym synem.
         * @return czy węzeł to prawy syn
         */
        boolean isRightSon();

        /**
         * Wyliczanie balansu wierzchołka.
         * @return wartość balansu
         */
        int countBalance();

        /**
         * Wyliczanie wysokość wierzchołka.
         */
        void countHeight();

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

    private static class AVLInnerNode<T> implements AVLNode<T> {
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

        public AVLInnerNode(T element) {
            this.element = element;
        }

        @Override
        public T getElement() {
            return this.element;
        }

        @Override
        public void setElement(T element) {
            this.element = element;
        }

        @Override
        public int getHeight() {
            return this.height;
        }

        @Override
        public AVLNode<T> getLeft() {
            return this.left;
        }

        @Override
        public void setLeft(AVLNode<T> node) {
            this.left = node;

            if (this.left != null)
                this.left.setParent(this);

            countHeight();
        }

        @Override
        public AVLNode<T> getRight() {
            return this.right;
        }

        @Override
        public void setRight(AVLNode<T> node) {
            this.right = node;

            if (this.right != null)
                this.right.setParent(this);

            countHeight();
        }

        @Override
        public AVLNode<T> getParent() {
            return this.parent;
        }

        @Override
        public void setParent(AVLNode<T> node) {
            this.parent = node;
        }

        @Override
        public boolean isLeftSon() {
            return this.getParent() != null && this.getParent().getLeft() == this;
        }

        @Override
        public boolean isRightSon() {
            return this.getParent() != null && this.getParent().getRight() == this;
        }

        @Override
        public int countBalance() {
            int leftHeight = left == null ? 0 : left.getHeight();
            int rightHeight = right == null ? 0 : right.getHeight();

            return leftHeight - rightHeight;
        }

        @Override
        public void countHeight() {
            int leftHeight = left == null ? 0 : left.getHeight();
            int rightHeight = right == null ? 0 : right.getHeight();

            height = Math.max(leftHeight, rightHeight) + 1;
        }

        @Override
        public AVLNode<T> minimum() {
            return left == null ? this : left.minimum();
        }

        @Override
        public AVLNode<T> maximum() {
            return right == null ? this : right.maximum();
        }
    }

    private static class AVLRootNode<T> implements AVLNode<T> {
        /**
         * Wewnętrzne wierzchołki.
         */
        private AVLNode<T> inner = null;

        public AVLRootNode() {
        }

        @Override
        public T getElement() {
            return null;
        }

        @Override
        public void setElement(T element) {
        }

        @Override
        public int getHeight() {
            return -1;
        }

        @Override
        public AVLNode<T> getLeft() {
            return null;
        }

        @Override
        public void setLeft(AVLNode<T> node) {
        }

        @Override
        public AVLNode<T> getRight() {
            return null;
        }

        @Override
        public void setRight(AVLNode<T> node) {
        }

        @Override
        public AVLNode<T> getParent() {
            return this.inner;
        }

        @Override
        public void setParent(AVLNode<T> node) {
            this.inner = node;

            if (this.inner != null)
                this.inner.setParent(this);
        }

        @Override
        public boolean isLeftSon() {
            return false;
        }

        @Override
        public boolean isRightSon() {
            return false;
        }

        @Override
        public int countBalance() {
            return 0;
        }

        @Override
        public void countHeight() {
        }

        @Override
        public AVLNode<T> minimum() {
            return this;
        }

        @Override
        public AVLNode<T> maximum() {
            return this;
        }
    }

    private abstract class AVLIterator implements Iterator<E> {
        /**
         * Aktualny węzeł.
         */
        protected AVLNode<E> currentNode;

        public AVLIterator(AVLNode<E> node) {
            currentNode = node;
        }

        @Override
        public boolean hasNext() {
            return currentNode.getHeight() > 0;
        }

        @Override
        public abstract E next() throws NoSuchElementException;

        /**
         * Wyznaczanie następnika węzła w drzewie.
         * @param node węzeł
         * @return węzeł z następną wartością
         */
        protected AVLNode<E> successor(AVLNode<E> node) {
            AVLNode<E> succ = node;

            if (node.getRight() != null)
                return node.getRight().minimum();

            while (succ.getHeight() > 0 && !succ.isLeftSon())
                succ = succ.getParent();

            return succ.getHeight() <= 0 ? succ : succ.getParent();
        }

        /**
         * Wyznaczanie poprzednika węzła w drzewie.
         * @param node węzeł
         * @return węzeł z poprzednią wartością
         */
        protected AVLNode<E> predecessor(AVLNode<E> node) {
            AVLNode<E> pred = node;

            if (node.getLeft() != null)
                return node.getLeft().maximum();

            while (pred.getHeight() > 0 && !pred.isRightSon())
                pred = pred.getParent();

            return pred.getHeight() <= 0 ? pred : pred.getParent();
        }
    }

    private class AVLSuccIterator extends AVLIterator {

        public AVLSuccIterator(AVLNode<E> node) {
            super(node);
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext())
                throw new NoSuchElementException("No more elements in iterator.");

            E returnValue = currentNode.getElement();

            currentNode = successor(currentNode);

            return returnValue;
        }
    }

    private class AVLPredIterator extends AVLIterator {
        public AVLPredIterator(AVLNode<E> node) {
            super(node);
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext())
                throw new NoSuchElementException("No more elements in iterator.");

            E returnValue = currentNode.getElement();

            currentNode = predecessor(currentNode);

            return returnValue;
        }
    }
}
