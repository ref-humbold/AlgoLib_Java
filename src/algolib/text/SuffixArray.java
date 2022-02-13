package algolib.text;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Structure of suffix array (with longest common prefix) */
public class SuffixArray
{
    private final int size_;
    private final String text;
    private final List<Integer> suffixArray;
    private final List<Integer> inverseArray = new ArrayList<>();
    private final List<Integer> lcpArray = new ArrayList<>();

    public SuffixArray(String text)
    {
        this.text = text;
        size_ = text.length();
        suffixArray = createArray(this.text.chars().boxed().collect(Collectors.toList()));
        initInverseArray();
        initLcpArray();
    }

    public String getText()
    {
        return text;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;

        if(!(obj instanceof SuffixArray))
            return false;

        SuffixArray other = (SuffixArray)obj;

        return size_ == other.size_ && Objects.equals(text, other.text) && Objects.equals(
                suffixArray, other.suffixArray);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(size_, text, suffixArray);
    }

    public int size()
    {
        return size_;
    }

    /**
     * @param index index in suffix array
     * @return text suffix at the index
     */
    public String get(int index)
    {
        if(index < 0 || index >= size_)
            throw new IndexOutOfBoundsException("Suffix array index out of range");

        return text.substring(suffixArray.get(index));
    }

    /**
     * Finds suffix in text for given index in this suffix array.
     * @param index index in suffix array
     * @return index in text where suffix begins
     */
    public int indexAt(int index)
    {
        if(index < 0 || index >= size_)
            throw new IndexOutOfBoundsException("Suffix array index out of range");

        return suffixArray.get(index);
    }

    /**
     * Finds index in this suffix array for given text suffix.
     * @param index index in text where suffix begins
     * @return index of suffix in suffix array
     */
    public int indexOf(int index)
    {
        if(index < 0 || index >= size_)
            throw new IndexOutOfBoundsException("Text index out of range");

        return inverseArray.get(index);
    }

    /**
     * Calculates length of the longest common prefix of given suffixes.
     * @param index1 index in text where first suffix begins
     * @param index2 index in text where second suffix begins
     * @return length of the longest common prefix
     */
    public int countLCP(int index1, int index2)
    {
        if(index1 < 0 || index1 >= size_ || index2 < 0 || index2 >= size_)
            throw new IndexOutOfBoundsException("Text index out of range");

        if(index1 == index2)
            return size_ - index1;

        int j1 = Math.min(inverseArray.get(index1), inverseArray.get(index2));
        int j2 = Math.max(inverseArray.get(index1), inverseArray.get(index2));

        return IntStream.range(j1 + 1, j2 + 1)
                        .mapToObj(lcpArray::get)
                        .min(Integer::compareTo)
                        .orElseGet(() -> lcpArray.get(j1 + 1));
    }

    private void initInverseArray()
    {
        inverseArray.addAll(Collections.nCopies(size_, 0));

        for(int i = 0; i < size_; ++i)
            inverseArray.set(suffixArray.get(i), i);
    }

    private void initLcpArray()
    {
        lcpArray.addAll(Collections.nCopies(size_, 0));

        for(int i = 0, len = 0; i < size_; ++i)
        {
            if(inverseArray.get(i) >= 1)
            {
                int j = suffixArray.get(inverseArray.get(i) - 1);

                while(i + len < size_ && j + len < size_ && text.charAt(i + len) == text.charAt(
                        j + len))
                    ++len;

                lcpArray.set(inverseArray.get(i), len);
            }

            len = Math.max(0, len - 1);
        }
    }

    private List<Integer> createArray(List<Integer> txt)
    {
        if(txt.size() < 2)
            return Collections.singletonList(0);

        int length2 = (txt.size() + 2) / 3;
        int length1 = (txt.size() + 1) / 3;
        int length0 = txt.size() / 3;
        int length02 = length0 + length2;
        List<Integer> indices12 = IntStream.range(0, txt.size() + length2 - length1)
                                           .filter(i -> i % 3 != 0)
                                           .boxed()
                                           .collect(Collectors.toList());

        sortIndices(indices12, txt, 2);
        sortIndices(indices12, txt, 1);
        sortIndices(indices12, txt, 0);

        int code = 0;
        int last0 = Integer.MAX_VALUE;
        int last1 = Integer.MAX_VALUE;
        int last2 = Integer.MAX_VALUE;
        List<Integer> t12 = new ArrayList<>(Collections.nCopies(length02, 0));

        for(int i : indices12)
        {
            if(getElement(txt, i) != last0 || getElement(txt, i + 1) != last1
                       || getElement(txt, i + 2) != last2)
            {
                ++code;
                last0 = getElement(txt, i);
                last1 = getElement(txt, i + 1);
                last2 = getElement(txt, i + 2);
            }

            if(i % 3 == 1)
                t12.set(i / 3, code);
            else
                t12.set(i / 3 + length2, code);
        }

        List<Integer> sa0 = new ArrayList<>();
        List<Integer> sa12;

        if(code < length02)
        {
            sa12 = createArray(t12);

            for(int i = 0; i < sa12.size(); ++i)
                t12.set(sa12.get(i), i + 1);
        }
        else
        {
            sa12 = new ArrayList<>(Collections.nCopies(length02, 0));

            for(int i = 0; i < t12.size(); ++i)
                sa12.set(t12.get(i) - 1, i);
        }

        for(int i : sa12)
            if(i < length2)
                sa0.add(3 * i);

        sortIndices(sa0, txt, 0);
        return merge(txt, sa0, t12, sa12);
    }

    private List<Integer> merge(List<Integer> t0, List<Integer> sa0, List<Integer> t12,
                                List<Integer> sa12)
    {
        List<Integer> saMerged = new ArrayList<>();
        int length2 = (t0.size() + 2) / 3;
        int length1 = (t0.size() + 1) / 3;
        int index0 = 0;
        int index12 = length2 - length1;

        while(index0 < sa0.size() && index12 < sa12.size())
        {
            int pos12 = sa12.get(index12) < length2
                        ? sa12.get(index12) * 3 + 1
                        : (sa12.get(index12) - length2) * 3 + 2;
            int pos0 = sa0.get(index0);
            boolean cond;

            if(sa12.get(index12) < length2)
                cond = lessOrEqual(getElement(t0, pos12), getElement(t0, pos0),
                                   getElement(t12, sa12.get(index12) + length2),
                                   getElement(t12, pos0 / 3));
            else
                cond = lessOrEqual(getElement(t0, pos12), getElement(t0, pos0),
                                   getElement(t0, pos12 + 1), getElement(t0, pos0 + 1),
                                   getElement(t12, sa12.get(index12) - length2 + 1),
                                   getElement(t12, pos0 / 3 + length2));

            if(cond)
            {
                saMerged.add(pos12);
                ++index12;
            }
            else
            {
                saMerged.add(pos0);
                ++index0;
            }
        }

        while(index12 < sa12.size())
        {
            saMerged.add(sa12.get(index12) < length2
                         ? sa12.get(index12) * 3 + 1
                         : (sa12.get(index12) - length2) * 3 + 2);
            ++index12;
        }

        while(index0 < sa0.size())
        {
            saMerged.add(sa0.get(index0));
            ++index0;
        }

        return saMerged;
    }

    private void sortIndices(List<Integer> indices, List<Integer> values, int shift)
    {
        SortedMap<Integer, Queue<Integer>> buckets = new TreeMap<>();
        int j = 0;

        for(int i : indices)
        {
            int v = getElement(values, i + shift);

            buckets.putIfAbsent(v, new ArrayDeque<>());
            buckets.get(v).add(i);
        }

        for(Queue<Integer> q : buckets.values())
            while(!q.isEmpty())
            {
                indices.set(j, q.remove());
                ++j;
            }
    }

    private int getElement(List<Integer> v, int i)
    {
        return i < v.size() ? v.get(i) : 0;
    }

    private boolean lessOrEqual(int... elements)
    {
        for(int i = 0; i < elements.length; i += 2)
        {
            if(elements[i] < elements[i + 1])
                return true;

            if(elements[i] > elements[i + 1])
                return false;
        }

        return true;
    }
}
