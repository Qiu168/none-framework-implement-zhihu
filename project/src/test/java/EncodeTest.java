import com.huangTaiQi.www.utils.code.Base64Utils;
import com.huangTaiQi.www.utils.code.Md5Utils;
import org.junit.Test;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;

public class EncodeTest {
    @Test
    public void testMd5() throws NoSuchAlgorithmException {
        System.out.println(Md5Utils.encode("123456789"));
    }
    @Test
    public void testBase64() throws IOException {
        System.out.println(getImgFileToBase64("C:\\Users\\_qqiu\\Desktop\\微信图片\\身份证\\xsz.jpg"));;
    }
    /**
     * imgFile 图片本地存储路径
     */
    public static String getImgFileToBase64(String imgFile) {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream inputStream = null;
        byte[] buffer = null;
        //读取图片字节数组
        try {
            File file=new File(imgFile);
            inputStream = Files.newInputStream(file.toPath());
            int count = 0;
            while (count == 0) {
                count = inputStream.available();
            }
            buffer = new byte[count];
            inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    // 关闭inputStream流
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对字节数组Base64编码
        return new BASE64Encoder().encode(buffer);
    }
}
