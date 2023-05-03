import com.huangTaiQi.www.utils.*;
import com.my_framework.www.redis.JedisUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.awt.image.BufferedImage;

import static com.huangTaiQi.www.constant.JedisConstants.LOGIN_CODE_KEY;
import static com.huangTaiQi.www.constant.MailConstants.MAIL_CONTENT;
import static com.huangTaiQi.www.constant.MailConstants.MAIL_TITLE;

public class MailTest {
    //3122004526@mail2.gdut.edu.cn
    @Test
    public void testMail(){
        MailUtils.sendMail("3122004526@mail2.gdut.edu.cn","test","test");
    }
    @Test
    public void sendEmail(){
        //使用验证码类，生成验证码类对象
        ImgVerifyCode ivc = new ImgVerifyCode();
        //获取验证码
        BufferedImage image = ivc.getImage();
        String code = ivc.getText();
        //发送邮件
        MailUtils.sendMail("3122004526@mail2.gdut.edu.cn",MAIL_CONTENT+"<h1><i>"+code+"</i></h1>",MAIL_TITLE);
        //存到redis
        Jedis jedis = JedisUtils.getJedis();
        jedis.set(LOGIN_CODE_KEY+"", code);

    }
    @Test
    public void check(){
        System.out.println(RegexUtils.check("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*","3122004526@mail2.gdut.edu.cn"));

    }
}
