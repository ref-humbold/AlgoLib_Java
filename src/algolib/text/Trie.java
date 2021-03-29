package algolib.text;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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

    public boolean isEmpty()
    {
        return tree.isEmpty();
    }

    public int size()
    {
        return size_;
    }

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
            size_++;
            node.terminus = true;
        }
    }

    public void addAll(Collection<String> texts)
    {
        for(String text : texts)
            add(text);
    }

    public void remove(String text)
    {
        if(text.length() > 0)
            removeNode(text, tree, 0);
    }

    public void clear()
    {
        tree = new TrieNode();
        size_ = 0;
    }

    private boolean removeNode(String text, TrieNode node, int i)
    {
        if(i == text.length() && node.terminus)
        {
            size_--;
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
        private final Map<Character, TrieNode> children = new HashMap<>();
        boolean terminus = false;

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
