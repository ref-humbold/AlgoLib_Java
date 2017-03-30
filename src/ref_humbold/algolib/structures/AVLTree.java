// DRZEWO AVL
package ref_humbold.algolib.structures;

import java.lang.Comparable;
import java.lang.Iterable;
import java.lang.Math;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AVLTree< E extends Comparable<E> >
    implements Iterable<E>
{
    protected class AVLNode
    {
        /** Wartość w węźle. */
        public E element;

        /** Wysokość węzła. */
        private int height;

        /** Lewy syn węzła. */
        private AVLNode left;

        /** Prawy syn węzła. */
        private AVLNode right;

        /** Ojciec węzła. */
        private AVLNode parent;

        public AVLNode(E element)
        {
            this.element = element;
            height = 0;
            left = null;
            right = null;
            parent = null;
        }

        public AVLNode getLeft()
        {
            return left;
        }

        public void setLeft(AVLNode node)
        {
            left = node;

            if(left != null)
                left.parent = this;

            countHeight();
        }

        public AVLNode getRight()
        {
            return right;
        }

        public void setRight(AVLNode node)
        {
            right = node;

            if(right != null)
                right.parent = this;

            countHeight();
        }

        public AVLNode getParent()
        {
            return parent;
        }

        public void setParent(AVLNode node)
        {
            parent = node;
        }

        /**
        Wyliczanie balansu wierzchołka.
        @return wartość balansu
        */
        public int getBalance()
        {
            int leftHeight = left == null ? 0 : left.height;
            int rightHeight = right == null ? 0 : right.height;

            return leftHeight-rightHeight;
        }

        /**
        Sprawdzanie, czy węzeł jest korzeniem
        @return czy węzeł to korzeń
        */
        public boolean isRoot()
        {
            return parent == null;
        }

        /**
        Sprawdzanie, czy węzeł jest lewym synem
        @return czy węzeł to lewy syn
        */
        public boolean isLeftSon()
        {
            return isRoot() ? false : parent.left == this;
        }

        /**
        Sprawdzanie, czy węzeł jest prawym synem
        @return czy węzeł to prawy syn
        */
        public boolean isRightSon()
        {
            return isRoot() ? false : parent.right == this;
        }

        /** Wylicza wysokość wierzchołka. */
        public void countHeight()
        {
            int leftHeight = left == null ? -1 : left.height;
            int rightHeight = right == null ? -1 : right.height;

            height = Math.max(leftHeight, rightHeight)+1;
        }

        /**
        Wyznaczanie korzenia drzewa, w którym mógłby znależć się element.
        @param element element do testowania
        @return korzeń poddrzewa, w którym znalazłby się element
        */
        public AVLNode getSubtree(E element)
        {
            if(element.equals(this.element))
                return this;
            else if(element.compareTo(this.element) < 0)
                return left;
            else
                return right;
        }

        /**
        Wyszukiwanie minimum w poddrzewie.
        @return węzeł z minimalną wartością w poddrzewie
        */
        public AVLNode minimum()
        {
            AVLNode tree_iter = this;

            while(tree_iter != null && tree_iter.left != null)
                tree_iter = tree_iter.left;

            return tree_iter;
        }

        /**
        Wyszukiwanie maksimum w ukorzenionym poddrzewie.
        @return węzeł z maksymalną wartością w poddrzewie
        */
        public AVLNode maximum()
        {
            AVLNode tree_iter = this;

            while(tree_iter != null && tree_iter.right != null)
                tree_iter = tree_iter.right;

            return tree_iter;
        }

        /**
        Wyznaczanie następnika węzła w drzewie.
        @return węzeł z następną wartością
        */
        public AVLNode successor()
        {
            AVLNode succ = this;

            if(right != null)
                return right.minimum();

            while(succ != null && succ.element.compareTo(this.element) <= 0)
                succ = succ.getParent();

            return succ;
        }

        /**
        Wyznaczanie poprzednika węzła w drzewie.
        @return węzeł z poprzednią wartością
        */
        public AVLNode predecessor()
        {
            AVLNode pred = this;

            if(left != null)
                return left.maximum();

            while(pred != null && pred.element.compareTo(pred.element) >= 0)
                pred = pred.getParent();

            return pred;
        }
    }

    protected class TreeIterator
        implements Iterator <E>
    {
        /** Aktualny węzeł. */
        private AVLNode currentNode;

        public TreeIterator(AVLNode node)
        {
            currentNode = node;
        }

        public boolean hasNext()
        {
            return currentNode != null;
        }

        public E next()
            throws NoSuchElementException
        {
            if(!hasNext())
                throw new NoSuchElementException();
            else
            {
                E returnValue = currentNode.element;

                currentNode = currentNode.successor();

                return returnValue;
            }
        }
    }

    /** Korzeń drzewa. */
    private AVLNode tree;

    /** Liczba elementów drzewa. */
    private int elems;

    public AVLTree()
    {
        tree = null;
        elems = 0;
    }

    public String toString()
    {
        String returnValue = "{[";

        for(E elem : this)
            returnValue += elem.toString()+", ";

        int ln = returnValue.length();

        return returnValue.substring(0, ln-2)+"]}";
    }

    /**
    Określanie pustości drzewa.
    @return czy drzewo jest puste
    */
    public boolean isEmpty()
    {
        return tree == null;
    }

    /**
    Określanie liczby elementów drzewa.
    @return liczba elemenów drzewa
    */
    public int size()
    {
        return elems;
    }

    /**
    Sprawdzanie występowania elementu w drzewie.
    @param element wartość do znalezienia
    @return czy wartość w drzewie
    */
    public boolean contains(E element)
    {
        if(tree == null)
            return false;

        AVLNode nodeParent = findNodeParent(element);

        return nodeParent == null ? true : nodeParent.getSubtree(element) != null;
    }

    /**
    Tworzenie iteratora dla drzewa.
    @return obiekt iteratora
    */
    public TreeIterator iterator()
    {
        return new TreeIterator(tree.minimum());
    }

    /**
    Dodawanie elementu do drzewa.
    @param element wartość do dodania
    @return czy dodano nowy element
    */
    public boolean add(E element)
    {
        AVLNode nodeParent = findNodeParent(element);
        AVLNode theNode = nodeParent == null ? tree : nodeParent.getSubtree(element);

        if(theNode != null)
            return false;

        AVLNode newNode = new AVLNode(element);

        if(nodeParent != null)
        {
            if(element.compareTo(nodeParent.element) > 0)
                nodeParent.setRight(newNode);
            else
                nodeParent.setLeft(newNode);

            rebalance(nodeParent);
        }
        else
            tree = newNode;

        ++elems;

        return true;
    }

    /**
    Usuwanie elementu z drzewa.
    @param element wartość do usunięcia
    @return czy element został usunięty
    */
    public boolean remove(E element)
    {
        AVLNode nodeParent = findNodeParent(element);
        AVLNode theNode = nodeParent == null ? tree : nodeParent.getSubtree(element);

        if(theNode == null)
            return false;

        if(nodeParent == null)
            deleteRoot(theNode);
        else
            deleteNode(theNode);

        return true;
    }

    /** Usuwanie wszystkich elementów z drzewa. */
    void clear()
    {
        tree = null;
        elems = 0;
    }

    /*
    Wyszukiwanie ojca węzła z daną wartością.
    @param element wartość do znalezienia
    @return ojciec węzła z wartością
    */
    private AVLNode findNodeParent(E element)
    {
        AVLNode treeIter = tree;
        AVLNode iterParent = null;

        while(treeIter != null)
            if(treeIter.element == element)
                break;
            else
            {
                iterParent = treeIter;
                treeIter = treeIter.getSubtree(element);
            }

        return iterParent;
    }

    /**
    Usuwanie elementu z korzenia drzewa.
    @param root korzeń drzewa
    */
    private void deleteRoot(AVLNode root)
    {
        if(root.getLeft() != null && root.getRight() != null)
            deleteNode(root);
        else if(root.getLeft() != null && root.getRight() == null)
        {
            E temp = root.getLeft().element;

            root.getLeft().element = root.element;
            root.element = temp;
            root.getLeft().setParent(null);
            root.setLeft(null);
            --elems;
        }
        else if(root.getLeft() == null && root.getRight() != null)
        {
            E temp = root.getRight().element;

            root.getRight().element = root.element;
            root.element = temp;
            root.getRight().setParent(null);
            root.setRight(null);
            --elems;
        }
        else
            clear();
    }

    /**
    Usuwanie elementu z węzła wewnętrznego drzewa.
    @param node węzeł do usunięcia
    */
    private void deleteNode(AVLNode node)
    {
        if(node.getLeft() != null && node.getRight() != null)
        {
            AVLNode succ = node.successor();
            E temp = succ.element;

            succ.element = node.element;
            node.element = temp;
            deleteNode(succ);
        }
        else
        {
            AVLNode son = node.getLeft() != null ? node.getLeft() : node.getRight();
            AVLNode nodeParent = node.getParent();

            replaceSubtree(node, son);
            rebalance(nodeParent);
            --elems;
        }
    }

    /**
    Zamiana poddrzewa ukorzenionego w danym węźle.
    @param node węzeł do zamiany
    @param root korzeń nowego poddrzewa
    */
    private void replaceSubtree(AVLNode node, AVLNode root)
    {
        if(node.isRoot())
        {
            tree = root;
            root.setParent(null);
        }
        else if(node.isLeftSon())
            node.getParent().setLeft(root);
        else
            node.getParent().setRight(root);

        node.setParent(null);
    }

    /**
    Rotowanie węzła wzdłuż krawędzi z jego ojcem.
    @param node węzeł do rotacji
    */
    private void rotate(AVLNode node)
    {
        if(node.isRoot())
            return;

        AVLNode upperNode = node.getParent();

        if(node.isLeftSon())
        {
            upperNode.setRight(node.getLeft());
            replaceSubtree(upperNode, node);
            node.setLeft(upperNode);
        }
        else if(node.isRightSon())
        {
            upperNode.setLeft(node.getRight());
            replaceSubtree(upperNode, node);
            node.setRight(upperNode);
        }
    }

    /**
    Przywracanie balansowania na ścieżce od wierzchołka do korzenia.
    @param node wierzchołek początkowy
    */
    private void rebalance(AVLNode node)
    {
        while(node != null)
        {
            node.countHeight();

            int newBalance = node.getBalance();

            if(newBalance >= 2)
            {
                if(node.getLeft().getBalance() > 0)
                    rotate(node.getLeft());
                else if(node.getLeft().getBalance() < 0)
                {
                    rotate(node.getLeft().getRight());
                    rotate(node.getLeft());
                }
            }
            else if(newBalance <= -2)
            {
                if(node.getRight().getBalance() < 0)
                    rotate(node.getRight());
                else if(node.getRight().getBalance() > 0)
                {
                    rotate(node.getRight().getLeft());
                    rotate(node.getRight());
                }
            }

            node = node.getParent();
        }
    }
}
