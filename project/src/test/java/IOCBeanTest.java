import com.huangTaiQi.www.service.impl.UserServiceImpl;
import com.my_framework.www.context.Impl.ApplicationContextImpl;
import org.junit.Test;

public class IOCBeanTest {
    public ApplicationContextImpl applicationContext=new ApplicationContextImpl("application.properties");


    @Test
    public void test(){
        UserServiceImpl userService =(UserServiceImpl) applicationContext.getBean("userServiceImpl");
    }
}
