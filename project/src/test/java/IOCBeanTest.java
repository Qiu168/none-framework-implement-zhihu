import com.huangTaiQi.www.service.UserService;
import com.huangTaiQi.www.service.impl.UserServiceImpl;
import com.my_framework.www.core.context.Impl.GenericApplicationContext;
import org.junit.Test;

public class IOCBeanTest {
    public GenericApplicationContext applicationContext=new GenericApplicationContext("com.huangTaiQi.www");
    @Test
    public void test(){
        UserServiceImpl userService =(UserServiceImpl) applicationContext.getBean("userServiceImpl");
        UserService userService1=(UserService)applicationContext.getBean("userService");
        System.out.println(userService);
        System.out.println(userService1);
    }
}
