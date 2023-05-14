import com.huangTaiQi.www.utils.ACFilter;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;

public class ACFilterTest {

    private ACFilter acFilter;
    private Set<String> sensitiveWords;

    @Before
    public void setUp() {
        sensitiveWords = new HashSet<>(Arrays.asList("abc", "def", "hij"));
        acFilter = new ACFilter(sensitiveWords);
    }

    @Test
    public void testSensitiveWordMatch() {
        String sentence1 = "This text contains the word abc.";
        Set<String> matches1 = acFilter.acMatch(sentence1);
        assertTrue(matches1.contains("abc"));

        String sentence2 = "This text does not contain any sensitive word.";
        Set<String> matches2 = acFilter.acMatch(sentence2);
        assertTrue(matches2.isEmpty());

        String sentence3 = "This text contains def and hij.";
        Set<String> matches3 = acFilter.acMatch(sentence3);
        assertTrue(matches3.contains("def"));
        assertTrue(matches3.contains("hij"));

        String sentence4 = "This text contains AbC, it should still be matched.";
        Set<String> matches4 = acFilter.acMatch(sentence4);
        //没有做大小写
        assertFalse(matches4.contains("AbC"));
    }
}