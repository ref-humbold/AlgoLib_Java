package algolib.text;

import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests: Structure of trie tree.
public class TrieTest
{
    private final List<String> texts = List.of("abcd", "ab", "xyz");
    private Trie testObject;

    @BeforeEach
    public void setUp()
    {
        testObject = new Trie(texts);
    }

    @Test
    public void isEmpty_WhenEmpty_ThenTrue()
    {
        // given
        testObject = new Trie();

        // when
        boolean result = testObject.isEmpty();

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void isEmpty_WhenNotEmpty_ThenFalse()
    {
        // when
        boolean result = testObject.isEmpty();

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void size_WhenEmpty_ThenZero()
    {
        // given
        testObject = new Trie();

        // when
        int result = testObject.size();

        // then
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    public void size_WhenNotEmpty_ThenNumberOfTexts()
    {
        // when
        int result = testObject.size();

        // then
        Assertions.assertThat(result).isEqualTo(texts.size());
    }

    @Test
    public void contains_WhenPresent_ThenTrue()
    {
        // when
        boolean result = testObject.contains("abcd");

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void contains_WhenAbsent_ThenFalse()
    {
        // when
        boolean result = testObject.contains("abxx");

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void contains_WhenAbsentPrefix_ThenFalse()
    {
        // when
        boolean result = testObject.contains("xy");

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void containsAll_WhenPresentAndAbsent_ThenFalse()
    {
        // given
        List<String> textsToCheck = List.of("abxx", "x", "abcdef", "xyz");

        // when
        boolean result = testObject.containsAll(textsToCheck);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void containsAll_WhenAllPresent_ThenTrue()
    {
        // when
        boolean result = testObject.containsAll(texts);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void add_WhenPresent_ThenNothingChanged()
    {
        // given
        String text = "abcd";

        // when
        testObject.add(text);

        // then
        Assertions.assertThat(testObject.contains(text)).isTrue();
        Assertions.assertThat(testObject.size()).isEqualTo(texts.size());
    }

    @Test
    public void add_WhenAbsent_ThenAdded()
    {
        // given
        String text = "abxx";

        // when
        testObject.add(text);

        // then
        Assertions.assertThat(testObject.contains(text)).isTrue();
        Assertions.assertThat(testObject.size()).isEqualTo(texts.size() + 1);
    }

    @Test
    public void add_WhenAbsentPrefix_ThenAdded()
    {
        // given
        String text = "xy";

        // when
        testObject.add(text);

        // then
        Assertions.assertThat(testObject.contains(text)).isTrue();
        Assertions.assertThat(testObject.size()).isEqualTo(texts.size() + 1);
    }

    @Test
    public void addAll_WhenPresentAndAbsent_ThenAbsentAdded()
    {
        // given
        List<String> textsToAdd = List.of("abxx", "x", "abcdef", "xyz");

        // when
        testObject.addAll(textsToAdd);

        // then
        for(String text : textsToAdd)
            Assertions.assertThat(testObject.contains(text)).isTrue();

        Assertions.assertThat(testObject.size())
                  .isEqualTo(Stream.concat(texts.stream(), textsToAdd.stream()).distinct().count());
    }

    @Test
    public void remove_WhenPresent_ThenRemoved()
    {
        // given
        String text = "abcd";

        // when
        testObject.remove(text);

        // then
        Assertions.assertThat(testObject.contains(text)).isFalse();
        Assertions.assertThat(testObject.size()).isEqualTo(texts.size() - 1);
    }

    @Test
    public void remove_WhenAbsent_ThenNothingChanged()
    {
        // given
        String text = "abxx";

        // when
        testObject.remove(text);

        // then
        Assertions.assertThat(testObject.contains(text)).isFalse();
        Assertions.assertThat(testObject.size()).isEqualTo(texts.size());
    }

    @Test
    public void remove_WhenAbsentPrefix_ThenNothingChanged()
    {
        // given
        String text = "xy";

        // when
        testObject.remove(text);

        // then
        Assertions.assertThat(testObject.contains("xyz")).isTrue();
        Assertions.assertThat(testObject.contains(text)).isFalse();
        Assertions.assertThat(testObject.size()).isEqualTo(texts.size());
    }

    @Test
    public void removeAll_WhenPresentAndAbsent_ThenPresentRemoved()
    {
        // given
        List<String> textsToRemove = List.of("abxx", "x", "abcdef", "xyz");

        // when
        testObject.removeAll(textsToRemove);

        // then
        for(String text : textsToRemove)
            Assertions.assertThat(testObject.contains(text)).isFalse();

        Assertions.assertThat(testObject.size())
                  .isEqualTo(texts.stream().filter(t -> !textsToRemove.contains(t)).count());
    }

    @Test
    public void clear_WhenNotEmpty_ThenEmpty()
    {
        // when
        testObject.clear();

        // then
        Assertions.assertThat(testObject.isEmpty()).isTrue();
        Assertions.assertThat(testObject.size()).isEqualTo(0);
    }
}
