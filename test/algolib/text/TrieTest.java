package algolib.text;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void find_WhenPresent_ThenTrue()
    {
        // when
        boolean result = testObject.find("abcd");
        // then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void find_WhenAbsent_ThenFalse()
    {
        // when
        boolean result = testObject.find("abxx");
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void find_WhenAbsentPrefix_ThenFalse()
    {
        // when
        boolean result = testObject.find("xy");
        // then
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void add_WhenPresent_ThenNothingChanged()
    {
        // given
        String text = "abcd";
        // when
        testObject.add(text);
        // then
        Assertions.assertThat(testObject.find(text)).isTrue();
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
        Assertions.assertThat(testObject.find(text)).isTrue();
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
        Assertions.assertThat(testObject.find(text)).isTrue();
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
            Assertions.assertThat(testObject.find(text)).isTrue();

        Assertions.assertThat(testObject.size()).isEqualTo(texts.size() + textsToAdd.size() - 1);
    }

    @Test
    public void remove_WhenPresent_ThenRemoved()
    {
        // given
        String text = "abcd";
        // when
        testObject.remove(text);
        // then
        Assertions.assertThat(testObject.find(text)).isFalse();
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
        Assertions.assertThat(testObject.find(text)).isFalse();
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
        Assertions.assertThat(testObject.find("xyz")).isTrue();
        Assertions.assertThat(testObject.find(text)).isFalse();
        Assertions.assertThat(testObject.size()).isEqualTo(texts.size());
    }
}
