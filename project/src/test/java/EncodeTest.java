import com.huangTaiQi.www.utils.code.Md5Utils;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class EncodeTest {
    @Test
    public void testMd5() throws NoSuchAlgorithmException {
        System.out.println(Md5Utils.encode("123456789"));
    }
}
