import com.huangTaiQi.www.service.impl.UserServiceImpl;
import com.my_framework.www.annotation.Autowired;
import com.my_framework.www.context.Impl.ApplicationContextImpl;
import org.junit.Test;

public class IOCBeanTest {
    public ApplicationContextImpl applicationContext=new ApplicationContextImpl("application.properties");


    @Test
    public void test(){
        UserServiceImpl userService =(UserServiceImpl) applicationContext.getBean("userServiceImpl");
        System.out.println(userService);
        System.out.println(userService.userDao);
        System.out.println(userService.getUserDao());
        System.out.println(userService.str);
    }
}
