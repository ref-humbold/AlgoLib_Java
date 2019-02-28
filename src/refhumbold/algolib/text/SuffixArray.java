package refhumbold.algolib.text;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class SuffixArray
{
    private int length;
    private String text;
    private List<Integer> suffixArray = new ArrayList<>();
    private List<Integer> inverseArray = new ArrayList<>();
    private List<Integer> lcpArray = new ArrayList<>();

    public SuffixArray(String text)
    {
        this.text = text;
        this.length = text.length();
        initSuffixArray();
        initInverseArray();
        initLcpArray();
    }

    public String getText()
    {
        return text;
    }

    public int size()
    {
        return length;
    }

    public String get(int i)
    {
        if(i < 0 || i >= length)
            throw new IndexOutOfBoundsException("Suffix array index out of range");

        return text.substring(suffixArray.get(i));
    }

    /**
     * @param i index in suffix array
     * @return index in text where suffix begins
     */
    public int indexAt(int i)
    {
        if(i < 0 || i >= length)
            throw new IndexOutOfBoundsException("Suffix array index out of range");

        return suffixArray.get(i);
    }


    /**
     * @param suf index in text where suffix begins
     * @return index of suffix in suffix array
     */
    public int indexOf(int suf)
    {
        if(suf < 0 || suf >= length)
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
        if(suf1 < 0 || suf1 >= length || suf2 < 0 || suf2 >= length)
            throw new IndexOutOfBoundsException("Text index out of range");

        if(suf1 == suf2)
            return length - suf1;

        int i1 = Math.min(inverseArray.get(suf1), inverseArray.get(suf2));
        int i2 = Math.max(inverseArray.get(suf1), inverseArray.get(suf2));
        int res = lcpArray.get(i1 + 1);

        for(int i = i1 + 2; i <= i2; ++i)
            res = Math.min(res, lcpArray.get(i));

        return res;
    }

    private void initSuffixArray()
    {
        List<Integer> t = text.chars().boxed().collect(Collectors.toList());

        suffixArray = createArray(t, 128);
    }

    private void initInverseArray()
    {
        inverseArray.addAll(Collections.nCopies(length, 0));

        for(int i = 0; i < length; ++i)
            inverseArray.set(suffixArray.get(i), i);
    }

    private void initLcpArray()
    {
        lcpArray.addAll(Collections.nCopies(length, 0));

        for(int i = 0, len = 0; i < length; ++i)
        {
            if(inverseArray.get(i) >= 1)
            {
                int j = suffixArray.get(inverseArray.get(i) - 1);

                while(i + len < length && j + len < length && text.charAt(i + len) == text.charAt(
                    j + len))
                    ++len;

                lcpArray.set(inverseArray.get(i), len);
            }

            if(len > 0)
                --len;
        }
    }

    private List<Integer> createArray(List<Integer> t, int k)
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

        sortByKeys(t12, t, 2, k);
        sortByKeys(t12, t, 1, k);
        sortByKeys(t12, t, 0, k);

        int ix = 0;
        int last0 = k;
        int last1 = k;
        int last2 = k;
        List<Integer> tn12 = new ArrayList<>(Collections.nCopies(n02, 0));

        for(int i : t12)
        {
            if(getElem(t, i) != last0 || getElem(t, i + 1) != last1 || getElem(t, i + 2) != last2)
            {
                ++ix;
                last0 = getElem(t, i);
                last1 = getElem(t, i + 1);
                last2 = getElem(t, i + 2);
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
            sa12 = createArray(tn12, ix + 1);

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

        sortByKeys(sa0, t, 0, k);

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

            if(sa12.get(i12) < n2 ? lessOrEqual(getElem(t0, pos12), getElem(t0, pos0),
                                                getElem(t12, sa12.get(i12) + n2),
                                                getElem(t12, pos0 / 3))
                                  : lessOrEqual(getElem(t0, pos12), getElem(t0, pos0),
                                                getElem(t0, pos12 + 1), getElem(t0, pos0 + 1),
                                                getElem(t12, sa12.get(i12) - n2 + 1),
                                                getElem(t12, pos0 / 3 + n2)))
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

    private void sortByKeys(List<Integer> v, List<Integer> keys, int shift, int k)
    {
        List<Queue<Integer>> buckets = new ArrayList<>();
        int j = 0;

        for(int i = 0; i < k; ++i)
            buckets.add(new ArrayDeque<>());

        for(int i : v)
            buckets.get(getElem(keys, i + shift)).add(i);

        for(Queue<Integer> q : buckets)
            while(!q.isEmpty())
            {
                v.set(j, q.remove());
                ++j;
            }
    }

    private int getElem(List<Integer> v, int i)
    {
        return i < v.size() ? v.get(i) : 0;
    }


    private boolean lessOrEqual(int... elems)
    {
        for(int i = 0; i < elems.length; i += 2)
            if(elems[i] < elems[i + 1])
                return true;
            else if(elems[i] > elems[i + 1])
                return false;

        return true;
    }
}
