package algolib.text;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/** Structure of trie tree. */
public class Trie
{
    private TrieNode tree = new TrieNode();
    private int size_ = 0;

    public Trie()
    {
    }

    public Trie(Collection<String> texts)
    {
        addAll(texts);
    }

    /**
     * Checks whether this tree is empty.
     * @return {@code true} if the tree is empty, otherwise {@code false}
     */
    public boolean isEmpty()
    {
        return tree.isEmpty();
    }

    /**
     * Gets the number of texts of this tree.
     * @return the number of texts
     */
    public int size()
    {
        return size_;
    }

    /**
     * Checks whether given text belongs to this tree.
     * @param text the text
     * @return {@code true} if the text is present, otherwise {@code false}
     */
    public boolean contains(String text)
    {
        TrieNode node = tree;

        for(char character : text.toCharArray())
        {
            node = node.get(character);

            if(node == null)
                return false;
        }

        return node.terminus;
    }

    /**
     * Checks whether given texts belong to this tree.
     * @param texts the texts
     * @return {@code true} if all texts are present, otherwise {@code false}
     */
    public boolean containsAll(Collection<String> texts)
    {
        return texts.stream().allMatch(this::contains);
    }

    /**
     * Adds new text to this tree.
     * @param text the new text
     */
    public void add(String text)
    {
        TrieNode node = tree;

        for(char character : text.toCharArray())
        {
            node.put(character, new TrieNode());
            node = node.get(character);
        }

        if(!node.terminus)
        {
            ++size_;
            node.terminus = true;
        }
    }

    /**
     * Adds new texts to this tree.
     * @param texts the new texts
     */
    public void addAll(Collection<String> texts)
    {
        for(String text : texts)
            add(text);
    }

    /**
     * Removes given text from this tree.
     * @param text the text
     */
    public void remove(String text)
    {
        if(!text.isEmpty())
            removeNode(text, tree, 0);
    }

    /**
     * Removes given texts from this tree.
     * @param texts the texts
     */
    public void removeAll(Collection<String> texts)
    {
        for(String text : texts)
            remove(text);
    }

    /**
     * Removes all texts from this tree.
     */
    public void clear()
    {
        tree = new TrieNode();
        size_ = 0;
    }

    // Removes node for character in text at specified index.
    private boolean removeNode(String text, TrieNode node, int i)
    {
        if(i == text.length() && node.terminus)
        {
            --size_;
            node.terminus = false;
        }
        else if(i < text.length())
        {
            TrieNode nextNode = node.get(text.charAt(i));

            if(nextNode != null && removeNode(text, nextNode, i + 1))
                node.remove(text.charAt(i));
        }

        return !node.terminus && node.isEmpty();
    }

    private static class TrieNode
    {
        boolean terminus = false;
        private final Map<Character, TrieNode> children = new HashMap<>();

        boolean isEmpty()
        {
            return children.isEmpty();
        }

        TrieNode get(char c)
        {
            return children.get(c);
        }

        void put(char c, TrieNode node)
        {
            children.putIfAbsent(c, node);
        }

        void remove(char c)
        {
            children.remove(c);
        }
    }
}
