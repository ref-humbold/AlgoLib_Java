package algolib.text;

import java.util.*;
import java.util.stream.Collectors;

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
     * @param i index in suffix array
     * @return suffix from i-th index
     */
    public String get(int i)
    {
        if(i < 0 || i >= size_)
            throw new IndexOutOfBoundsException("Suffix array index out of range");

        return text.substring(suffixArray.get(i));
    }

    /**
     * @param i index in suffix array
     * @return index in text where suffix begins
     */
    public int indexAt(int i)
    {
        if(i < 0 || i >= size_)
            throw new IndexOutOfBoundsException("Suffix array index out of range");

        return suffixArray.get(i);
    }

    /**
     * @param suf index in text where suffix begins
     * @return index of suffix in suffix array
     */
    public int indexOf(int suf)
    {
        if(suf < 0 || suf >= size_)
            throw new IndexOutOfBoundsException("Text index out of range");

        return inverseArray.get(suf);
    }

    /**
     * @param suf1 index in text where first suffix begins
     * @param suf2 index in text where second suffix begins
     * @return longest common prefix of both suffices
     */
    public int countLCP(int suf1, int suf2)
    {
        if(suf1 < 0 || suf1 >= size_ || suf2 < 0 || suf2 >= size_)
            throw new IndexOutOfBoundsException("Text index out of range");

        if(suf1 == suf2)
            return size_ - suf1;

        int i1 = Math.min(inverseArray.get(suf1), inverseArray.get(suf2));
        int i2 = Math.max(inverseArray.get(suf1), inverseArray.get(suf2));
        int res = lcpArray.get(i1 + 1);

        for(int i = i1 + 2; i <= i2; ++i)
            res = Math.min(res, lcpArray.get(i));

        return res;
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

            if(len > 0)
                --len;
        }
    }

    private List<Integer> createArray(List<Integer> t)
    {
        if(t.size() < 2)
            return Collections.singletonList(0);

        int n2 = (t.size() + 2) / 3;
        int n1 = (t.size() + 1) / 3;
        int n0 = t.size() / 3;
        int n02 = n0 + n2;
        List<Integer> t12 = new ArrayList<>();

        for(int i = 0; i < t.size() + n2 - n1; ++i)
            if(i % 3 != 0)
                t12.add(i);

        sortByKeys(t12, t, 2);
        sortByKeys(t12, t, 1);
        sortByKeys(t12, t, 0);

        int ix = 0;
        int last0 = Integer.MAX_VALUE;
        int last1 = Integer.MAX_VALUE;
        int last2 = Integer.MAX_VALUE;
        List<Integer> tn12 = new ArrayList<>(Collections.nCopies(n02, 0));

        for(int i : t12)
        {
            if(getElement(t, i) != last0 || getElement(t, i + 1) != last1
                    || getElement(t, i + 2) != last2)
            {
                ++ix;
                last0 = getElement(t, i);
                last1 = getElement(t, i + 1);
                last2 = getElement(t, i + 2);
            }

            if(i % 3 == 1)
                tn12.set(i / 3, ix);
            else
                tn12.set(i / 3 + n2, ix);
        }

        List<Integer> sa0 = new ArrayList<>();
        List<Integer> sa12;

        if(ix < n02)
        {
            sa12 = createArray(tn12);

            for(int i = 0; i < sa12.size(); ++i)
                tn12.set(sa12.get(i), i + 1);
        }
        else
        {
            sa12 = new ArrayList<>(Collections.nCopies(n02, 0));

            for(int i = 0; i < tn12.size(); ++i)
                sa12.set(tn12.get(i) - 1, i);
        }

        for(int i : sa12)
            if(i < n2)
                sa0.add(3 * i);

        sortByKeys(sa0, t, 0);

        return merge(t, sa0, tn12, sa12);
    }

    private List<Integer> merge(List<Integer> t0, List<Integer> sa0, List<Integer> t12,
                                List<Integer> sa12)
    {
        List<Integer> sa = new ArrayList<>();
        int n2 = (t0.size() + 2) / 3;
        int n1 = (t0.size() + 1) / 3;
        int i0 = 0;
        int i12 = n2 - n1;

        while(i0 < sa0.size() && i12 < sa12.size())
        {
            int pos12 = sa12.get(i12) < n2 ? sa12.get(i12) * 3 + 1 : (sa12.get(i12) - n2) * 3 + 2;
            int pos0 = sa0.get(i0);

            if(sa12.get(i12) < n2 ? lessOrEqual(getElement(t0, pos12), getElement(t0, pos0),
                                                getElement(t12, sa12.get(i12) + n2),
                                                getElement(t12, pos0 / 3))
                                  : lessOrEqual(getElement(t0, pos12), getElement(t0, pos0),
                                                getElement(t0, pos12 + 1), getElement(t0, pos0 + 1),
                                                getElement(t12, sa12.get(i12) - n2 + 1),
                                                getElement(t12, pos0 / 3 + n2)))
            {
                sa.add(pos12);
                ++i12;
            }
            else
            {
                sa.add(pos0);
                ++i0;
            }
        }

        while(i12 < sa12.size())
        {
            sa.add(sa12.get(i12) < n2 ? sa12.get(i12) * 3 + 1 : (sa12.get(i12) - n2) * 3 + 2);
            ++i12;
        }

        while(i0 < sa0.size())
        {
            sa.add(sa0.get(i0));
            ++i0;
        }

        return sa;
    }

    private void sortByKeys(List<Integer> v, List<Integer> keys, int shift)
    {
        SortedMap<Integer, Queue<Integer>> buckets = new TreeMap<>();
        int j = 0;

        for(int i : v)
        {
            int k = getElement(keys, i + shift);

            buckets.putIfAbsent(k, new ArrayDeque<>());
            buckets.get(k).add(i);
        }

        for(Queue<Integer> q : buckets.values())
            while(!q.isEmpty())
            {
                v.set(j, q.remove());
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
            if(elements[i] < elements[i + 1])
                return true;
            else if(elements[i] > elements[i + 1])
                return false;

        return true;
    }
}
